package animatedledstrip.client

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

    class AnimationSender(val ipAddress: String, val port: Int, val connectAttemptLimit: Int) {
        private var socket: Socket = Socket()
        private var out: ObjectOutputStream? = null
        private var socIn: ObjectInputStream? = null
        private var disconnected = true
        private var connectionTries = 0
        private var action: ((Map<*, *>) -> Any?)? = null
        private var stopSocket = false
        private var started = false
        private var loopThread: Job? = null

        fun <R> setOnReceiveCallback(action: (Map<*, *>) -> R): AnimationSender {
            this.action = action
            return this
        }

        fun setAsDefaultSender(): AnimationSender {
            defaultSender = this
            return this
        }

        fun start(): AnimationSender {
            if (!started) {
                loopThread = GlobalScope.launch(newSingleThreadContext("AnimationSender")) {
                    loop()
                }
                started = true
            }
            return this
        }

        fun end() {
            loopThread?.cancel()
        }

        private suspend fun loop() {
            while (!stopSocket) {
                connect()
                try {
                    out = ObjectOutputStream(socket.getOutputStream())
                    socIn = ObjectInputStream(BufferedInputStream(socket.getInputStream()))
                    var input: Map<*, *>
                    while (true) {
                        input = socIn!!.readObject() as Map<*, *>
                        Logger.debug("Received: $input")
                        action?.invoke(input)
                    }
                } catch (e: Exception) {
                    socket = Socket()
                    disconnected = true
                }
            }
        }

        private suspend fun connect() {
            try {
                socket.connect(InetSocketAddress(ipAddress, port), 5000)
                Logger.info("Connected to server at $ipAddress")
                disconnected = false
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
                }
            }
        }

        fun send(args: Map<*, *>) {
            try {
                out!!.writeObject(args)
            } catch (e: Exception) {
                Logger.error("Error sending animation: $e")
            }
        }

        fun isDisconnected() = disconnected

    }

}