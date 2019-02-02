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


import kotlinx.coroutines.*
import org.pmw.tinylog.Logger
import java.io.BufferedInputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.InetSocketAddress
import java.net.Socket

object AnimationSenderFactory {

    var defaultSender: AnimationSender? = null
        get() {
            return field ?: throw NullPointerException()
        }

    fun create(ipAddress: String = "10.0.0.254", port: Int = 5, connectAttemptLimit: Int = 5): AnimationSender {
        return AnimationSender(ipAddress, port, connectAttemptLimit)
    }

    @Suppress("EXPERIMENTAL_API_USAGE")
    class AnimationSender(val ipAddress: String, val port: Int, val connectAttemptLimit: Int) {
        private var socket: Socket = Socket()
        private var out: ObjectOutputStream? = null
        private var socIn: ObjectInputStream? = null
        private var disconnected = true
        private var connectionTries = 0
        private var receiveAction: ((Map<*, *>) -> Any?)? = null
        private var connectAction: (() -> Unit)? = null
        private var disconnectAction: (() -> Unit)? = null
        private var stopSocket = false
        private var started = false
        private var loopThread: Job? = null
        private val senderCoroutineScope = newSingleThreadContext("Animation Sender port $port")

        fun <R> setOnReceiveCallback(action: (Map<*, *>) -> R): AnimationSender {
            this.receiveAction = action
            return this
        }

        fun setOnConnectCallback(action: () -> Unit): AnimationSender {
            this.connectAction = action
            return this
        }

        fun setOnDisconnectCallback(action: () -> Unit): AnimationSender {
            this.disconnectAction = action
            return this
        }

        fun setAsDefaultSender(): AnimationSender {
            defaultSender = this
            return this
        }

        fun start(): AnimationSender {
            if (!started) {
                stopSocket = false
                loopThread = GlobalScope.launch(senderCoroutineScope) {
                    loop()
                }
                started = true
            }
            return this
        }

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
                        out = ObjectOutputStream(socket.getOutputStream())
                        socIn = ObjectInputStream(BufferedInputStream(socket.getInputStream()))
                        out!!.writeObject(mapOf("ClientData" to true, "TextBased" to false))
//                        sendAnimation("""
//                                setStripColor(animation.color1)
//                                Thread.sleep(1000)
//                                setStripColor(animation.color2)
//                                run(AnimationData(mapOf("Animation" to "WIP", "Color1" to animation.color3.hex, "Direction" to 'F')))
//                                """.trimIndent(), "COL2")
                        var input: Map<*, *>
                        while (true) {
                            input = socIn!!.readObject() as Map<*, *>
                            Logger.debug("Received: $input")
                            receiveAction?.invoke(input) ?: println("Null pointer")
                        }
                    } catch (e: Exception) {        // TODO: Limit types of exceptions
                        socket = Socket()
                        disconnected = true
                        disconnectAction?.invoke()
                    }
                }
            }
        }

        private suspend fun connect() {
            withContext(Dispatchers.IO) {
                try {
                    socket.connect(InetSocketAddress(ipAddress, port), 5000)
                    Logger.info("Connected to server at $ipAddress")
                    disconnected = false
                    connectAction?.invoke()
                    connectionTries = 0
                } catch (e: Exception) {
                    connectionTries++
                    Logger.warn("Connection attempt $connectionTries: Server not found at $ipAddress: $e")
                    delay(10000)
                    if (connectionTries <= connectAttemptLimit) {
                        socket = Socket()
                        connect()
                    } else {
                        Logger.error("Could not locate server at $ipAddress after $connectionTries tries")
                        stopSocket = true
                    }
                }
            }
        }

        fun send(args: Map<*, *>) {
            try {
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        out!!.writeObject(args)
                    }
                }
            } catch (e: Exception) {
                Logger.error("Error sending animation: $e")
            }
        }

        fun sendAnimation(code: String, name: String) {
            send(mapOf(
                    "AnimationDefinition" to true,
                    "AnimationCode" to code.trimIndent(),
                    "CustomAnimationID" to name)
            )
        }

        fun isDisconnected() = disconnected

    }

}