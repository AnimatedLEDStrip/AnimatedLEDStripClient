package animatedledstrip.test

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
import animatedledstrip.animationutils.animation
import animatedledstrip.client.AnimationSenderFactory
import animatedledstrip.client.send
import animatedledstrip.utils.json
import kotlinx.coroutines.*
import org.junit.Test
import org.pmw.tinylog.Configurator
import org.pmw.tinylog.Level
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.net.InetAddress
import java.net.ServerSocket
import kotlin.test.assertTrue

class AnimationSenderFactoryTest {

    init {
        Configurator.defaultConfig()
            .level(Level.OFF)
            .activate()
    }

    @Test
    fun testDefaultSender() {
        val port = 1100
        AnimationSenderFactory

        val testSender = AnimationSenderFactory.create("0.0.0.0", port).setAsDefaultSender()
        assertTrue { AnimationSenderFactory.defaultSender === testSender }
    }

    @Test
    fun testStart() {
        val port = 1101

        val job = GlobalScope.launch {
            withContext(Dispatchers.IO) {
                ServerSocket(port, 0, InetAddress.getByName("0.0.0.0")).accept()
            }
        }

        runBlocking { delay(2000) }

        AnimationSenderFactory.create("0.0.0.0", port).start()

        runBlocking { job.join() }
    }

    @Test
    fun testConnectCallback() {
        var testBoolean = false
        val port = 1102

        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                ServerSocket(port, 0, InetAddress.getByName("0.0.0.0")).accept()
            }
        }

        runBlocking { delay(2000) }

        AnimationSenderFactory.create("0.0.0.0", port)
            .setOnConnectCallback {
                testBoolean = true
            }
            .start()

        runBlocking { delay(2000) }

        assertTrue { testBoolean }
    }

    @Test
    fun testDisconnectCallback() {
        var testBoolean = false
        val port = 1103

        val job = GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val socket = ServerSocket(port, 0, InetAddress.getByName("0.0.0.0")).accept()
                delay(3000)
                socket.close()
            }
        }

        runBlocking { delay(2000) }

        AnimationSenderFactory
            .create("0.0.0.0", port)
            .setAsDefaultSender()
            .setOnDisconnectCallback {
                testBoolean = true
                AnimationSenderFactory.defaultSender.end()
            }
            .start()

        runBlocking { job.join() }
        runBlocking { delay(1000) }
        assertTrue { testBoolean }
    }

    @Test
    fun testReceiveCallback() {
        var testBoolean = false
        val port = 1104

        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val socket = ServerSocket(port, 0, InetAddress.getByName("0.0.0.0")).accept()
                val out = socket.getOutputStream()
                out.write(AnimationData().animation(Animation.COLOR).json())
            }
        }

        runBlocking { delay(2000) }

        AnimationSenderFactory.create("0.0.0.0", port)
            .setOnReceiveCallback {
                testBoolean = true
            }
            .start()

        runBlocking { delay(2000) }

        assertTrue { testBoolean }
    }

    @Test
    fun testNewAnimationCallback() {
        var testBoolean1 = false
        var testBoolean2 = true
        val port = 1105

        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val socket = ServerSocket(port, 0, InetAddress.getByName("0.0.0.0")).accept()
                val out = socket.getOutputStream()
                out.write(AnimationData().animation(Animation.COLOR).json())
            }
        }

        runBlocking { delay(2000) }

        AnimationSenderFactory.create("0.0.0.0", port)
            .setOnNewAnimationCallback {
                testBoolean1 = true
            }
            .setOnEndAnimationCallback {
                testBoolean2 = false
            }
            .start()

        runBlocking { delay(2000) }

        assertTrue { testBoolean1 }
        assertTrue { testBoolean2 }
    }

    @Test
    fun testEndAnimationCallback() {
        var testBoolean1 = false
        var testBoolean2 = true
        val port = 1106

        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val socket = ServerSocket(port, 0, InetAddress.getByName("0.0.0.0")).accept()
                val out = socket.getOutputStream()
                out.write(AnimationData().animation(Animation.ENDANIMATION).json())
            }
        }

        runBlocking { delay(2000) }

        AnimationSenderFactory.create("0.0.0.0", port)
            .setOnNewAnimationCallback {
                testBoolean2 = false
            }
            .setOnEndAnimationCallback {
                testBoolean1 = true
            }
            .start()

        runBlocking { delay(2000) }

        assertTrue { testBoolean1 }
        assertTrue { testBoolean2 }

    }

    @Test
    fun testMultipleStarts() {
        val stderr: PrintStream = System.err
        val tempOut = ByteArrayOutputStream()
        System.setErr(PrintStream(tempOut))

        tempOut.reset()

        Configurator.defaultConfig()
            .formatPattern("{{level}:|min-size=8} {message}")
            .level(Level.WARNING)
            .activate()
        val testSender = AnimationSenderFactory.create("0.0.0.0", 1107).start()

        testSender.start()
        assertTrue {
            tempOut
                .toString("utf-8")
                .replace("\r\n", "\n") ==
                    "WARNING: Sender started already\n"
        }

        System.setErr(stderr)
        Configurator.defaultConfig()
            .level(Level.OFF)
            .activate()
    }

    @Test
    fun testSendToNullOutput() {
        val stderr: PrintStream = System.err
        val tempOut = ByteArrayOutputStream()
        System.setErr(PrintStream(tempOut))

        tempOut.reset()

        Configurator.defaultConfig()
            .formatPattern("{{level}:|min-size=8} {message}")
            .level(Level.WARNING)
            .activate()

        val testAnimation = AnimationData()

        val sender =
            AnimationSenderFactory
                .create("0.0.0.0", 0)

        testAnimation.send(sender)

        runBlocking { delay(2000) }

        assertTrue {
            tempOut
                .toString("utf-8")
                .replace("\r\n", "\n") ==
                    "WARNING: Output stream null\n"
        }

        System.setErr(stderr)
        Configurator.defaultConfig()
            .level(Level.OFF)
            .activate()
    }

}