from AnimationSender import AnimationSender
from AnimationData import AnimationData

sender = AnimationSender("10.44.157.2", 5)
sender.start()
sender.send_animation(AnimationData().json())
