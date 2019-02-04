package animatedledstrip.client

import kotlinx.coroutines.*
import org.junit.Test
import java.io.BufferedInputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ServerSocket
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
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val socket = ServerSocket(5).accept()
                val socIn = ObjectInputStream(BufferedInputStream(socket!!.getInputStream()))

                val input: Any? = socIn.readObject()
                val inMap = input as Map<*, *>
                assertTrue { inMap["ClientData"] == true }
                assertTrue { inMap["TextBased"] == false }
            }
        }

        runBlocking { delay(2000) }

        AnimationSenderFactory.create("localhost", 5).start()
    }

}