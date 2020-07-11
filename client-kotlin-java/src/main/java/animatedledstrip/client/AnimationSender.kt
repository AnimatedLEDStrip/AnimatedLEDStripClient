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
import animatedledstrip.animationutils.EndAnimation
import animatedledstrip.leds.AnimatedLEDStrip
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

class AnimationSender(var ipAddress: String, var port: Int) {

    /* Connection */

    private var socket: Socket = Socket()
    private var socIn: InputStream? = null
    private var socOut: OutputStream? = null

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val senderCoroutineScope = newSingleThreadContext("Animation Sender port $port")


    /* Status */

    var started = false
        private set
    var connected: Boolean = false
        private set


    /* Callbacks */

    private var receiveAction: ((String) -> Any?)? = null

    private var newAnimationDataAction: ((AnimationData) -> Any?)? = null
    private var newAnimationInfoAction: ((Animation.AnimationInfo) -> Any?)? = null
    private var newEndAnimationAction: ((EndAnimation) -> Any?)? = null
    private var newSectionAction: ((AnimatedLEDStrip.Section) -> Any?)? = null
    private var newStripInfoAction: ((StripInfo) -> Any?)? = null

    private var connectAction: ((String) -> Unit)? = null
    private var disconnectAction: ((String) -> Unit)? = null
    private var unableToConnectAction: ((String) -> Unit)? = null


    /* Information about the connected server */

    val supportedAnimations = mutableMapOf<String, Animation.AnimationInfo>()
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
        supportedAnimations.clear()
    }


    /* Handle receiving data from server */

    private suspend fun openConnection() {
        var connectedIp = ""
        socket = Socket()
        socket.soTimeout = 1000

        withContext(Dispatchers.IO) {
            try {
                socket.connect(InetSocketAddress(ipAddress, port), 1000)
                connectedIp = ipAddress
                socOut = socket.getOutputStream()
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
            processData(receiveData())
        } catch (e: IOException) {
            Logger.error("Exception occurred: $ipAddress:$port: $e")
            connected = false
            disconnectAction?.invoke(connectedIp)
            runningAnimations.clear()
        }
    }

    private suspend fun receiveData(): String {
        val input = ByteArray(INPUT_SIZE)
        var count: Int = -1

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
        return input.toUTF8(count)
    }

    private fun processData(input: String) {
        while (connected) {
            parse@ for (d in splitData(input)) {
                when (d.getDataTypePrefix()) {
                    AnimationData.prefix -> {
                        val data = d.jsonToAnimationData()
                        newAnimationDataAction?.invoke(data)
                        runningAnimations[data.id] = data
                    }
                    Animation.AnimationInfo.prefix -> {
                        val info = d.jsonToAnimationInfo()
                        supportedAnimations[info.name] = info
                        newAnimationInfoAction?.invoke(info)
                    }
                    EndAnimation.prefix -> {
                        val end = d.jsonToEndAnimation()
                        newEndAnimationAction?.invoke(end)
                        runningAnimations.remove(end.id)
                    }
                    AnimatedLEDStrip.sectionPrefix -> {     // AnimatedLEDStrip.Section
                        newSectionAction?.invoke(d.jsonToSection())
                    }
                    StripInfo.prefix -> {
                        val info = d.jsonToStripInfo()
                        stripInfo = info
                        newStripInfoAction?.invoke(info)
                        continue@parse
                    }
                    else -> continue@parse
                }

                Logger.debug("Received: $d")

                receiveAction?.invoke(d)
            }
        }
    }

    private val partialData = StringBuilder()

    private fun String.withPartialData(): String {
        val newStr = partialData.toString() + this
        partialData.clear()
        return newStr
    }

    private fun handlePartialData(inputData: List<String>): List<String> {
        partialData.append(inputData.last())
        return inputData.dropLast(1)
    }

    private fun splitData(input: String): List<String> {
        val inputData = input.withPartialData().split(DELIMITER)

        return if (!input.endsWith(DELIMITER))
            handlePartialData(inputData)
        else
            inputData
    }


    /**
     * Send data via this connection
     */
    fun send(args: SendableData) {
        GlobalScope.launch(Dispatchers.IO) {
            socOut?.write(args.json()) ?: Logger.warn("Output stream null")
            Logger.debug(args)
        }
    }


    /* Set methods for callbacks and IP */

    /**
     * Specify an action to perform when data is received from the server
     */
    fun <R> setOnReceiveCallback(action: (String) -> R): AnimationSender {
        receiveAction = action
        return this
    }

    /**
     * Specify an action to perform when an animation starting message is received from the server.
     * Runs after onReceive callback.
     */
    fun <R> setOnNewAnimationDataCallback(action: (AnimationData) -> R): AnimationSender {
        newAnimationDataAction = action
        return this
    }

    fun <R> setOnNewAnimationInfoCallback(action: (Animation.AnimationInfo) -> R): AnimationSender {
        newAnimationInfoAction = action
        return this
    }

    /**
     * Specify an action to perform when an animation ending message is received from the server.
     * Runs after onReceive callback.
     */
    fun <R> setOnNewEndAnimationCallback(action: (EndAnimation) -> R): AnimationSender {
        newEndAnimationAction = action
        return this
    }

    fun <R> setOnNewSectionCallback(action: (AnimatedLEDStrip.Section) -> R): AnimationSender {
        newSectionAction = action
        return this
    }

    fun <R> setOnNewStripInfoCallback(action: (StripInfo) -> R): AnimationSender {
        newStripInfoAction = action
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

    companion object {
        /**
         * The default sender if none is specified
         */
        lateinit var defaultSender: AnimationSender
    }

}
