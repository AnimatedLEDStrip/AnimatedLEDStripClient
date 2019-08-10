package animatedledstrip.test

import animatedledstrip.animationutils.Animation
import animatedledstrip.animationutils.AnimationData
import animatedledstrip.animationutils.Direction
import animatedledstrip.client.AnimationSenderFactory
import animatedledstrip.client.endAnimation
import animatedledstrip.client.send
import animatedledstrip.colors.ColorContainer
import kotlinx.coroutines.*
import org.junit.Test
import org.pmw.tinylog.Configurator
import org.pmw.tinylog.Level
import java.io.BufferedInputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.InetAddress
import java.net.ServerSocket
import kotlin.test.assertTrue

class AnimationDataExtensionTest {

    init {
        Configurator.defaultConfig().level(Level.OFF).activate()
    }

    @Test
    fun testSend() {
        var testBoolean = false
        val port = 1200

        val testAnimation = AnimationData().animation(Animation.STACK)
                .color(ColorContainer(0xFF, 0xFFFF).prepare(5), index = 0)
                .color(0xFF, index = 1)
                .color(0xFF, index = 2)
                .color(0xFF, index = 3)
                .color(0xFF, index = 4)
                .continuous(true)
                .delay(50)
                .direction(Direction.FORWARD)
                .id("TEST")
                .spacing(5)

        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val socket = ServerSocket(port, 0, InetAddress.getByName("0.0.0.0")).accept()
                val inStr = ObjectInputStream(BufferedInputStream(socket!!.getInputStream()))
                ObjectOutputStream(socket.getOutputStream())
                val input = inStr.readObject()
                if (input == testAnimation) testBoolean = true
            }
        }

        runBlocking { delay(2000) }

        val sender = AnimationSenderFactory.create("0.0.0.0", port)
                .start()

        runBlocking { delay(2000) }

        testAnimation.send(sender)

        runBlocking { delay(2000) }

        assertTrue { testBoolean }
    }

    @Test
    fun testEndAnimation() {
        var testBoolean = false
        val port = 1201

        val testAnimation = AnimationData().animation(Animation.STACK)
                .color(ColorContainer(0xFF, 0xFFFF).prepare(5), index = 0)
                .color(0xFF, index = 1)
                .color(0xFF, index = 2)
                .color(0xFF, index = 3)
                .color(0xFF, index = 4)
                .continuous(true)
                .delay(50)
                .direction(Direction.FORWARD)
                .id("TEST")
                .spacing(5)

        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val socket = ServerSocket(port, 0, InetAddress.getByName("0.0.0.0")).accept()
                val inStr = ObjectInputStream(BufferedInputStream(socket!!.getInputStream()))
                ObjectOutputStream(socket.getOutputStream())
                val input = inStr.readObject()
                if (input is AnimationData && input.animation == Animation.ENDANIMATION) testBoolean = true
            }
        }

        val sender = AnimationSenderFactory.create("0.0.0.0", port)
                .start()

        runBlocking { delay(2000) }

        testAnimation.endAnimation(sender)

        runBlocking { delay(2000) }

        assertTrue { testBoolean }
    }

}