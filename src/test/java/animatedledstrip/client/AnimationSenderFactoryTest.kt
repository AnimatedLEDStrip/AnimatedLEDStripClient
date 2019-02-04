package animatedledstrip.client

import org.junit.Test
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

}