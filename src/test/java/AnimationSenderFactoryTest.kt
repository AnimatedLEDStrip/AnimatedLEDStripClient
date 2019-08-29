package animatedledstrip.test

import animatedledstrip.animationutils.Animation
import animatedledstrip.animationutils.AnimationData
import animatedledstrip.animationutils.animation
import animatedledstrip.client.AnimationSenderFactory
import kotlinx.coroutines.*
import org.junit.Ignore
import org.junit.Test
import org.pmw.tinylog.Configurator
import org.pmw.tinylog.Level
import java.io.BufferedInputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.InetAddress
import java.net.ServerSocket
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class AnimationSenderFactoryTest {

    init {
        Configurator.defaultConfig().level(Level.OFF).activate()
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
                val socket = ServerSocket(port, 0, InetAddress.getByName("0.0.0.0")).accept()
                ObjectOutputStream(socket.getOutputStream())
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
                val socket = ServerSocket(port, 0, InetAddress.getByName("0.0.0.0")).accept()
                ObjectInputStream(BufferedInputStream(socket!!.getInputStream()))
                ObjectOutputStream(socket.getOutputStream())
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
                ObjectInputStream(BufferedInputStream(socket!!.getInputStream()))
                ObjectOutputStream(socket.getOutputStream())

//                    socIn.readObject()

                socket.shutdownOutput()
            }
        }

        runBlocking { delay(5000) }

        AnimationSenderFactory.create("0.0.0.0", port)
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
                ObjectInputStream(BufferedInputStream(socket!!.getInputStream()))
                val out = ObjectOutputStream(socket.getOutputStream())
                out.writeObject(AnimationData().animation(Animation.COLOR))
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
                ObjectInputStream(BufferedInputStream(socket!!.getInputStream()))
                val out = ObjectOutputStream(socket.getOutputStream())
                out.writeObject(AnimationData().animation(Animation.COLOR))
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
                ObjectInputStream(BufferedInputStream(socket!!.getInputStream()))
                val out = ObjectOutputStream(socket.getOutputStream())
                out.writeObject(AnimationData().animation(Animation.ENDANIMATION))
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
        val testSender = AnimationSenderFactory.create("0.0.0.0").start()

        assertFailsWith(Exception::class) {
            testSender.start()
        }
    }

    @Test
    @Ignore
    fun testAutoReconnect() {
        AnimationSenderFactory.create("0.0.0.0", 0, 1).start()

        runBlocking { delay(1) }
    }

}