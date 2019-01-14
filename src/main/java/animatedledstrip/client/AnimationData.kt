package animatedledstrip.client

import animatedledstrip.leds.Animations
import animatedledstrip.leds.Direction
import org.pmw.tinylog.Logger
import java.lang.IllegalArgumentException

/**
 * Class for creating and sending a map to the server to start an animation.
 *
 */
class AnimationData {

    private val animationMap = mutableMapOf<String, Any?>()


    /*
     * Parameters and functions to create animation map
     */
    var animation: Animations
        set(value) {
            animationMap["Animation"] = value
        }
        get() = animationMap["Animation"] as Animations? ?: throw UninitializedPropertyAccessException()

    fun animation(animation: Animations): AnimationData {
        this.animation = animation
        return this
    }

    var color1: Long
        set(value) {
            animationMap["Color1"] = value
        }
        get() = animationMap["Color1"] as Long? ?: throw UninitializedPropertyAccessException()

    fun color1(color: Long): AnimationData {
        this.color1 = color
        return this
    }

    fun color1(color: Int): AnimationData {
        this.color1 = color.toLong()
        return this
    }

    fun color1(color: String): AnimationData {
        this.color1 = parseHex(color)
        return this
    }

    fun color(color: Long): AnimationData {
        this.color1 = color
        return this
    }

    fun color(color: Int): AnimationData {
        this.color1 = color.toLong()
        return this
    }

    fun color(color: String): AnimationData {
        this.color1 = parseHex(color)
        return this
    }

    var color2: Long
        set(value) {
            animationMap["Color2"] = value
        }
        get() = animationMap["Color2"] as Long? ?: throw UninitializedPropertyAccessException()

    fun color2(color: Long): AnimationData {
        this.color2 = color
        return this
    }

    fun color2(color: Int): AnimationData {
        this.color2 = color.toLong()
        return this
    }

    fun color2(color: String): AnimationData {
        this.color2 = parseHex(color)
        return this
    }

    var color3: Long
        set(value) {
            animationMap["Color3"] = value
        }
        get() = animationMap["Color3"] as Long? ?: throw UninitializedPropertyAccessException()

    fun color3(color: Long): AnimationData {
        this.color3 = color
        return this
    }

    fun color3(color: Int): AnimationData {
        this.color3 = color.toLong()
        return this
    }

    fun color3(color: String): AnimationData {
        this.color3 = parseHex(color)
        return this
    }

    var color4: Long
        set(value) {
            animationMap["Color4"] = value
        }
        get() = animationMap["Color4"] as Long? ?: throw UninitializedPropertyAccessException()

    fun color4(color: Long): AnimationData {
        this.color4 = color
        return this
    }

    fun color4(color: Int): AnimationData {
        this.color4 = color.toLong()
        return this
    }

    fun color4(color: String): AnimationData {
        this.color4 = parseHex(color)
        return this
    }

    var color5: Long
        set(value) {
            animationMap["Color5"] = value
        }
        get() = animationMap["Color5"] as Long? ?: throw UninitializedPropertyAccessException()

    fun color5(color: Long): AnimationData {
        this.color5 = color
        return this
    }

    fun color5(color: Int): AnimationData {
        this.color5 = color.toLong()
        return this
    }

    fun color5(color: String): AnimationData {
        this.color5 = parseHex(color)
        return this
    }

    var colorList = mutableListOf<Long>()

    fun colorList(colorList: List<Long>): AnimationData {
        this.colorList = colorList.toMutableList()
        return this
    }

    var continuous: Boolean
        set(value) {
            animationMap["Continuous"] = value
        }
        get() = animationMap["Continuous"] as Boolean? ?: throw UninitializedPropertyAccessException()

    fun continuous(continuous: Boolean): AnimationData {
        this.continuous = continuous
        return this
    }

    var delay: Int
        set(value) {
            animationMap["Delay"] = value
        }
        get() = animationMap["Delay"] as Int? ?: throw UninitializedPropertyAccessException()

    fun delay(delay: Int): AnimationData {
        this.delay = delay
        return this
    }

    var direction: Char
        set(value) {
            when (value) {
                'F', 'f', 'B', 'b' -> animationMap["Direction"] = value
                else -> throw IllegalArgumentException("Valid directions are: 'F', 'B'")
            }
        }
        get() = animationMap["Direction"] as Char? ?: throw UninitializedPropertyAccessException()

    fun direction(direction: Char): AnimationData {
        this.direction = direction
        return this
    }

    fun direction(direction: Direction): AnimationData {
        this.direction = when (direction) {
            Direction.FORWARD -> 'F'
            Direction.BACKWARD -> 'B'
        }
        return this
    }

    var id: String
        set(value) {
            animationMap["ID"] = value
        }
        get() = animationMap["ID"] as String? ?: throw UninitializedPropertyAccessException()

    var ID: String
        set(value) {
            animationMap["ID"] = value
        }
        get() = animationMap["ID"] as String? ?: throw UninitializedPropertyAccessException()

    fun id(id: String): AnimationData {
        this.id = id
        return this
    }

    fun ID(id: String): AnimationData {
        this.id = id
        return this
    }

    var spacing: Int
        set(value) {
            animationMap["Spacing"] = value
        }
        get() = animationMap["Spacing"] as Int? ?: throw UninitializedPropertyAccessException()

    fun spacing(spacing: Int): AnimationData {
        this.spacing = spacing
        return this
    }


    /*
     * Functions to send animation and set default server
     */

    fun send(sender: AnimationSenderFactory.AnimationSender = AnimationSenderFactory.defaultSender!!) {
        animationMap["ColorList"] = colorList
        sender.send(animationMap)
        Logger.debug("$animationMap sent")
    }

}