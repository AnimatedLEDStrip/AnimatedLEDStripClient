/*
 *  Copyright (c) 2018-2020 AnimatedLEDStrip
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

package animatedledstrip.client

import animatedledstrip.animationutils.Animation
import animatedledstrip.animationutils.AnimationData
import animatedledstrip.leds.StripInfo
import animatedledstrip.utils.*
import kotlinx.coroutines.*
import org.pmw.tinylog.Logger
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketException
import java.net.SocketTimeoutException

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
     * @return The new AnimationSender
     */
    fun create(ipAddress: String = "10.0.0.254", port: Int = 5) =
        AnimationSender(ipAddress, port)


    class AnimationSender(var ipAddress: String, var port: Int) {
        private var socket: Socket = Socket()
        private var out: OutputStream? = null

        var started = false
            private set
        var connected: Boolean = false
            private set

        @Suppress("EXPERIMENTAL_API_USAGE")
        private val senderCoroutineScope = newSingleThreadContext("Animation Sender port $port")

        private var receiveAction: ((AnimationData) -> Any?)? = null
        private var newAnimationAction: ((AnimationData) -> Any?)? = null
        private var endAnimationAction: ((AnimationData) -> Any?)? = null
        private var connectAction: ((String) -> Unit)? = null
        private var disconnectAction: ((String) -> Unit)? = null
        private var unableToConnectAction: ((String) -> Unit)? = null

        val runningAnimations = mutableMapOf<String, AnimationData>()

        var stripInfo: StripInfo? = null

        /**
         * Start this connection
         */
        fun start(): AnimationSender {
            if (!started) {
                GlobalScope.launch(senderCoroutineScope) {
                    openConnection()
                }
                started = true
            } else Logger.warn("Sender started already")
            return this
        }

        /**
         * Stop this connection
         */
        fun end() {
            started = false
            socket.close()
        }

        private suspend fun openConnection() {
            var connectedIp = ""
            socket = Socket()
            socket.soTimeout = 1000

            var socIn: InputStream? = null
            withContext(Dispatchers.IO) {
                try {
                    socket.connect(InetSocketAddress(ipAddress, port), 1000)
                    connectedIp = ipAddress
                    out = socket.getOutputStream()
                    socIn = socket.getInputStream()
                    connected = true
                    connectAction?.invoke(connectedIp)
                } catch (e: SocketException) {
                    unableToConnectAction?.invoke(ipAddress)
                    started = false
                    return@withContext
                } catch (e: SocketTimeoutException) {
                    unableToConnectAction?.invoke(ipAddress)
                    started = false
                    return@withContext
                }
            }

            Logger.info("Connected to server at $connectedIp:$port")

            try {
                var input = ByteArray(10000)
                var count = -1
                while (connected) {
                    while (true)
                        try {
                            withContext(Dispatchers.IO) {
                                count = socIn?.read(input) ?: throw SocketException("Socket null")
                            }
                            break
                        } catch (e: SocketTimeoutException) {
                            yield()
                            continue
                        }

                    if (count == -1) throw SocketException("Connection closed")

                    parse@ for (d in input.toUTF8(count).split(";")) {
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

                        receiveAction?.invoke(data)

                        when (data.animation) {             // Run new or end animation action
                            Animation.ENDANIMATION -> {
                                endAnimationAction?.invoke(data)
                                runningAnimations.remove(data.id)
                            }
                            else -> {
                                newAnimationAction?.invoke(data)
                                runningAnimations[data.id] = data
                            }
                        }

                        input = ByteArray(10000)
                    }
                }
            } catch (e: IOException) {
                Logger.error("Exception occurred: $ipAddress:$port: $e")
                connected = false
                disconnectAction?.invoke(connectedIp)
                runningAnimations.clear()
            }
        }

        /**
         * Send an animation via this connection
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
         */
        fun <R> setOnReceiveCallback(action: (AnimationData) -> R): AnimationSender {
            receiveAction = action
            return this
        }

        /**
         * Specify an action to perform when an animation starting message is received from the server.
         * Runs after onReceive callback.
         */
        fun <R> setOnNewAnimationCallback(action: (AnimationData) -> R): AnimationSender {
            newAnimationAction = action
            return this
        }

        /**
         * Specify an action to perform when an animation ending message is received from the server.
         * Runs after onReceive callback.
         */
        fun <R> setOnEndAnimationCallback(action: (AnimationData) -> R): AnimationSender {
            endAnimationAction = action
            return this
        }

        /**
         * Specify an action to perform when a connection is established
         */
        fun setOnConnectCallback(action: (String) -> Unit): AnimationSender {
            connectAction = action
            return this
        }

        /**
         * Specify an action to perform when a connection is lost
         */
        fun setOnDisconnectCallback(action: (String) -> Unit): AnimationSender {
            disconnectAction = action
            return this
        }

        fun setOnUnableToConnectCallback(action: (String) -> Unit): AnimationSender {
            unableToConnectAction = action
            return this
        }

        /**
         * Set this sender as the default sender
         */
        fun setAsDefaultSender(): AnimationSender {
            defaultSender = this
            return this
        }

        /**
         * Set this connection's IP address.
         * Will start/restart connection if start = true or if start = null
         * and connection is running.
         *
         * @param address A string representing an IPv4 address
         */
        fun setIPAddress(address: String, start: Boolean? = null): AnimationSender {
            GlobalScope.launch {
                if (started) {
                    end()
                    delayBlocking(2000)
                }
                ipAddress = address
                if (start ?: started) start()
            }
            return this
        }

        /**
         * Set this connection's port.
         * Will start/restart connection if start = true or if start = null
         * and connection is running.
         */
        fun setPort(newPort: Int, start: Boolean? = null): AnimationSender {
            GlobalScope.launch {
                if (started) {
                    end()
                    delayBlocking(2000)
                }
                port = newPort
                if (start ?: started) start()
            }
            return this
        }

    }

}