package animatedledstrip.client

/*
 *  Copyright (c) 2019 AnimatedLEDStrip
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */


import animatedledstrip.animationutils.Animation
import animatedledstrip.animationutils.AnimationData
import animatedledstrip.leds.StripInfo
import animatedledstrip.utils.*
import kotlinx.coroutines.*
import org.pmw.tinylog.Logger
import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketException
import java.nio.charset.Charset

object AnimationSenderFactory {

    /**
     * The default sender if none is specified
     */
    lateinit var defaultSender: AnimationSender


    /**
     * Create a new AnimationSender
     *
     * @param ipAddress The IP of the server
     * @param port The port of the server
     * @param connectAttemptLimit The number of times to attempt a connection before giving up
     * @return The new AnimationSender
     */
    fun create(ipAddress: String = "10.0.0.254", port: Int = 5, connectAttemptLimit: Int = 5) =
        AnimationSender(ipAddress, port, connectAttemptLimit)


    class AnimationSender(var ipAddress: String, val port: Int, val connectAttemptLimit: Int) {
        private var socket: Socket = Socket()
        private var out: OutputStream? = null
        private var connectionTries = 0

        private var stopSocket = false
        var started = false
            private set
        var connected: Boolean = false
            private set

        private var loopThread: Job? = null
        @Suppress("EXPERIMENTAL_API_USAGE")
        private val senderCoroutineScope = newSingleThreadContext("Animation Sender port $port")

        private var receiveAction: ((AnimationData) -> Any?)? = null
        private var newAnimationAction: ((AnimationData) -> Any?)? = null
        private var endAnimationAction: ((AnimationData) -> Any?)? = null
        private var connectAction: ((String) -> Unit)? = null
        private var disconnectAction: ((String) -> Unit)? = null

        val runningAnimations = mutableMapOf<String, AnimationData>()

        var stripInfo: StripInfo? = null

        /**
         * Start this connection
         *
         * @return this
         */
        fun start(): AnimationSender {
            if (!started) {
                stopSocket = false
                loopThread = GlobalScope.launch(senderCoroutineScope) {
                    loop()
                }
                started = true
            } else Logger.warn("Sender started already")
            return this
        }

        /**
         * Stop this connection
         */
        fun end() {
            loopThread?.cancel()
            started = false
            stopSocket = true
            socket.close()
            socket = Socket()
            assert(!connected)
        }

        /**
         * Attempt to create a connection to the server
         */
        private suspend fun connect(): String {
            return withContext(Dispatchers.IO) {
                try {
                    socket.connect(InetSocketAddress(ipAddress, port), 5000)
                    Logger.info("Connected to server at $ipAddress:$port")
                    connected = true
                    connectAction?.invoke(ipAddress)
                    connectionTries = 0
                    ipAddress
                } catch (e: SocketException) {
                    connectionTries++
                    Logger.warn("Connection attempt $connectionTries: Error connecting to server at $ipAddress:$port: $e")
                    if (connectionTries < connectAttemptLimit) {
                        delay(10000)
                        socket = Socket()
                        connect()
                    } else {
                        Logger.error("Could not locate server at $ipAddress:$port after $connectionTries tries")
                        connectionTries = 0
                        end()
                    }
                    ""
                }
            }
        }

        private suspend fun loop() {
            while (!stopSocket) {
                val connectedIp = connect()
                withContext(Dispatchers.IO) {
                    try {
                        out = socket.getOutputStream()
                        val socIn = socket.getInputStream()
                        var input: ByteArray
                        read@ while (true) {
                            checkNotNull(out)
                            input = ByteArray(10000)
                            val count = socIn.read(input)       // Wait for message
                            if (count == -1) throw SocketException("Connection closed")
                            parse@for (d in input.toUTF8(count).split(";")) {
                                val data: AnimationData
                                when (d.getDataTypePrefix()) {
                                    "DATA" -> {
                                        data = d.jsonToAnimationData()
                                    }
                                    "INFO" -> {
                                        stripInfo = d.jsonToStripInfo()
                                        continue@parse
                                    }
                                    else -> continue@parse
                                }
                                Logger.debug("Received: $data")
                                receiveAction?.invoke(data) ?: Logger.debug("No receive action defined")
                                when (data.animation) {             // Run new or end animation action
                                    Animation.ENDANIMATION -> {
                                        endAnimationAction?.invoke(data)
                                            ?: Logger.debug("No end animation action defined")
                                        runningAnimations.remove(data.id)
                                    }
                                    else -> {
                                        newAnimationAction?.invoke(data)
                                            ?: Logger.debug("No new animation action defined")
                                        runningAnimations[data.id] = data
                                    }
                                }
                            }
                        }
                    } catch (e: SocketException) {
                        socket = Socket()                           // Reset socket
                        Logger.error("Exception occurred: $ipAddress:$port: $e")
                        connected = false
                        disconnectAction?.invoke(connectedIp)       // Run disconnect action
                        runningAnimations.clear()
                    }
                }
            }
        }

        /**
         * Send an animation via this connection
         *
         * @param args The animation
         */
        fun send(args: AnimationData) {
            GlobalScope.launch {
                withContext(Dispatchers.IO) {
                    out?.write(args.json()) ?: Logger.warn("Output stream null")
                    Logger.debug(args)
                }
            }
        }


        /* Set methods for callbacks and IP */

        /**
         * Specify an action to perform when data is received from the server
         *
         * @param R The return type of your function
         * @param action A function to run
         * @return this
         */
        fun <R> setOnReceiveCallback(action: (AnimationData) -> R): AnimationSender {
            receiveAction = action
            return this
        }

        /**
         * Specify an action to perform when an animation starting message is received from the server.
         * Runs after onReceive callback.
         *
         * @param R The return type of your function
         * @param action A function to run
         * @return this
         */
        fun <R> setOnNewAnimationCallback(action: (AnimationData) -> R): AnimationSender {
            newAnimationAction = action
            return this
        }

        /**
         * Specify an action to perform when an animation ending message is received from the server.
         * Runs after onReceive callback.
         *
         * @param R The return type of your function
         * @param action A function to run
         * @return this
         */
        fun <R> setOnEndAnimationCallback(action: (AnimationData) -> R): AnimationSender {
            endAnimationAction = action
            return this
        }

        /**
         * Specify an action to perform when a connection is established
         *
         * @param action A function to run
         * @return this
         */
        fun setOnConnectCallback(action: (String) -> Unit): AnimationSender {
            connectAction = action
            return this
        }

        /**
         * Specify an action to perform when a connection is lost
         *
         * @param action A function to run
         * @return this
         */
        fun setOnDisconnectCallback(action: (String) -> Unit): AnimationSender {
            disconnectAction = action
            return this
        }

        /**
         * Set this sender as the default sender
         *
         * @return this
         */
        fun setAsDefaultSender(): AnimationSender {
            defaultSender = this
            return this
        }

        /**
         * Set this connection's IP address. Restarts connection if connected.
         *
         * @param address A string representing an IPv4 address
         * @return this
         */
        fun setIPAddress(address: String): AnimationSender {
            end()
            ipAddress = address
            start()
            return this
        }

    }

}