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
import kotlinx.coroutines.*
import org.pmw.tinylog.Logger
import java.io.BufferedInputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.InetSocketAddress
import java.net.Socket

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
        private var out: ObjectOutputStream? = null
        private var socIn: ObjectInputStream? = null
        private var connectionTries = 0

        private var receiveAction: ((AnimationData) -> Any?)? = null
        private var newAnimationAction: ((AnimationData) -> Any?)? = null
        private var endAnimationAction: ((AnimationData) -> Any?)? = null
        private var connectAction: (() -> Unit)? = null
        private var disconnectAction: (() -> Unit)? = null

        private var stopSocket = false
        private var started = false
        var connected: Boolean = true
            private set

        private var loopThread: Job? = null
        @Suppress("EXPERIMENTAL_API_USAGE")
        private val senderCoroutineScope = newSingleThreadContext("Animation Sender port $port")

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
        fun setOnConnectCallback(action: () -> Unit): AnimationSender {
            connectAction = action
            return this
        }

        /**
         * Specify an action to perform when a connection is lost
         *
         * @param action A function to run
         * @return this
         */
        fun setOnDisconnectCallback(action: () -> Unit): AnimationSender {
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
         * Set this connection's IP address
         *
         * @param address A string representing an IPv4 address
         * @return this
         */
        fun setIPAddress(address: String): AnimationSender {
            ipAddress = address
            return this
        }

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
            } else throw Exception("Sender started already")
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
        }

        private suspend fun loop() {
            while (!stopSocket) {
                connect()
                withContext(Dispatchers.IO) {
                    try {
                        // Create streams
                        out = ObjectOutputStream(socket.getOutputStream())
                        socIn = ObjectInputStream(BufferedInputStream(socket.getInputStream()))
                        var input: AnimationData

                        while (true) {
                            // Wait for input
                            input = socIn!!.readObject() as AnimationData
                            Logger.debug("Received: $input")
                            // Run receive action
                            receiveAction?.invoke(input) ?: Logger.debug("No receive action defined")
                            // Run new or end animation action
                            when (input.animation) {
                                Animation.ENDANIMATION -> endAnimationAction?.invoke(input)
                                        ?: Logger.debug("No end animation action defined")
                                else -> newAnimationAction?.invoke(input)
                                        ?: Logger.debug("No new animation action defined")
                            }
                        }

                    } catch (e: Exception) {            // TODO: Limit types of exceptions
                        socket = Socket()               // Reset socket
                        Logger.error("Exception $e occurred: $ipAddress:$port")
                        connected = false
                        disconnectAction?.invoke()      // Run disconnect action
                    }
                }
            }
        }

        /**
         * Attempt to create a connection to the server
         */
        private suspend fun connect() {
            withContext(Dispatchers.IO) {
                try {
                    socket.connect(InetSocketAddress(ipAddress, port), 5000)
                    Logger.info("Connected to server at $ipAddress port $port")
                    connected = true
                    connectAction?.invoke()
                    connectionTries = 0
                } catch (e: Exception) {
                    connectionTries++
                    Logger.warn("Connection attempt $connectionTries: Server not found at $ipAddress: $e")
                    delay(10000)
                    if (connectionTries < connectAttemptLimit) {
                        socket = Socket()
                        connect()
                    } else {
                        Logger.error("Could not locate server at $ipAddress after $connectionTries tries")
                        end()
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
            try {
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        out?.writeObject(args) ?: Logger.error("Output stream not defined")
                        Logger.debug(args)
                    }
                }
            } catch (e: Exception) {
                Logger.error("Error sending animation: $e")
            }
        }

    }

}