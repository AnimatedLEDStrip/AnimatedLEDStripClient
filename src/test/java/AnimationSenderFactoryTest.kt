package animatedledstrip.test

import animatedledstrip.client.AnimationSenderFactory
import kotlinx.coroutines.*
import org.junit.Ignore
import org.junit.Test
import java.io.BufferedInputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.InetAddress
import java.net.ServerSocket
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class AnimationSenderFactoryTest {

    @Test
    @Ignore
    fun testDefaultSender() {
        AnimationSenderFactory
//        assertFailsWith(UninitializedPropertyAccessException::class) {
//            AnimationSenderFactory.defaultSender
//        }

        val testSender = AnimationSenderFactory.create("0.0.0.0", 1104).setAsDefaultSender()
        assertTrue { AnimationSenderFactory.defaultSender === testSender }
    }

    @Test
    @Ignore
    fun testStart() {
        val port = 1105

        val job = GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val socket = ServerSocket(port, 0, InetAddress.getByName("0.0.0.0")).accept()
//                val socIn = ObjectInputStream(BufferedInputStream(socket!!.getInputStream()))
                ObjectOutputStream(socket.getOutputStream())


//                val input: Any? = socIn.readObject()
//                val inMap = input as Map<*, *>
//                assertTrue { inMap["ClientData"] == true }
//                assertTrue { inMap["TextBased"] == false }
            }
        }

        runBlocking { delay(2000) }

        AnimationSenderFactory.create("0.0.0.0", port).start()

        runBlocking { job.join() }
    }

    @Test
    @Ignore
    fun testConnectCallback() {
        var testBoolean = false
        val port = 1106

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
    @Ignore
    fun testDisconnectCallback() {
        var testBoolean = false
        val port = 1107

        val job = GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val ss = ServerSocket(port, 0, InetAddress.getByName("0.0.0.0"))
                val socket = ss.accept()
                val socIn = ObjectInputStream(BufferedInputStream(socket!!.getInputStream()))
                ObjectOutputStream(socket.getOutputStream())

                socIn.readObject()

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
    @Ignore
    fun testReceiveCallback() {
        var testBoolean = false
        val port = 1106

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
    @Ignore
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