package animatedledstrip.client

import kotlinx.coroutines.*
import org.junit.Test
import java.io.BufferedInputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ServerSocket
import java.net.SocketAddress
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class AnimationSenderFactoryTest {

    @Test
    fun testDefaultSender() {
        AnimationSenderFactory
        assertFailsWith(UninitializedPropertyAccessException::class) {
            AnimationSenderFactory.defaultSender
        }

        val testSender = AnimationSenderFactory.create("localhost", 5).setAsDefaultSender()
        assertTrue { AnimationSenderFactory.defaultSender === testSender }
    }

    @Test
    fun testStart() {
        val port = 5

        val job = GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val socket = ServerSocket(port).accept()
                val socIn = ObjectInputStream(BufferedInputStream(socket!!.getInputStream()))
                ObjectOutputStream(socket.getOutputStream())


                val input: Any? = socIn.readObject()
                val inMap = input as Map<*, *>
                assertTrue { inMap["ClientData"] == true }
                println("test passed")
                assertTrue { inMap["TextBased"] == false }
                println("Test2 passed")
            }
        }

        runBlocking { delay(2000) }

        AnimationSenderFactory.create("localhost", port).start()

        runBlocking { job.join() }
    }

    @Test
    fun testConnectCallback() {
        var testBoolean = false
        val port = 6

        val job = GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val socket = ServerSocket(port).accept()
                val socIn = ObjectInputStream(BufferedInputStream(socket!!.getInputStream()))
                ObjectOutputStream(socket.getOutputStream())
            }
        }

        runBlocking { delay(2000) }

        AnimationSenderFactory.create("localhost", port)
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
        val port = 7

        val job = GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val ss = ServerSocket(port)
                val socket = ss.accept()
                val socIn = ObjectInputStream(BufferedInputStream(socket!!.getInputStream()))
                ObjectOutputStream(socket.getOutputStream())

                socIn.readObject()

                socket.shutdownOutput()
            }
        }

        runBlocking { delay(5000) }

        AnimationSenderFactory.create("localhost", port)
                .setAsDefaultSender()
                .setOnDisconnectCallback {
                    testBoolean = true
                    AnimationSenderFactory.defaultSender.end()
                }
                .start()

        runBlocking { job.join() }
        assertTrue { testBoolean }

    }

}