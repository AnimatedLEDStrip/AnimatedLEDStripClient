#   Copyright (c) 2019-2020 AnimatedLEDStrip
#
#   Permission is hereby granted, free of charge, to any person obtaining a copy
#   of this software and associated documentation files (the "Software"), to deal
#   in the Software without restriction, including without limitation the rights
#   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
#   copies of the Software, and to permit persons to whom the Software is
#   furnished to do so, subject to the following conditions:
#
#   The above copyright notice and this permission notice shall be included in
#   all copies or substantial portions of the Software.
#
#   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
#   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
#   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
#   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
#   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
#   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
#   THE SOFTWARE.

module Animation
  COLOR = 0
  CUSTOM_ANIMATION = 1
  CUSTOM_REPETITIVE_ANIMATION = 2
  ALTERNATE = 3
  BOUNCE = 4
  BOUNCE_TO_COLOR = 5
  CAT_TOY = 6
  CAT_TOY_TO_COLOR = 7
  FADE_TO_COLOR = 8
  FIREWORKS = 9
  METEOR = 10
  MULTIPIXEL_RUN = 11
  MULTIPIXEL_RUN_TO_COLOR = 12
  RIPPLE = 13
  PIXEL_MARATHON = 14
  PIXEL_RUN = 15
  SMOOTH_CHASE = 16
  SMOOTH_FADE = 17
  SPARKLE = 18
  SPARKLE_FADE = 19
  SPARKLE_TO_COLOR = 20
  SPLAT = 21
  STACK = 22
  STACK_OVERFLOW = 23
  WIPE = 24
  END_ANIMATION = 25

  # @return [String]
  def self.string(animation)
    case animation
    when COLOR
      'COLOR'
    when CUSTOM_ANIMATION
      'CUSTOMANIMATION'
    when CUSTOM_REPETITIVE_ANIMATION
      'CUSTOMREPETITIVEANIMATION'
    when ALTERNATE
      'ALTERNATE'
    when BOUNCE
      'BOUNCE'
    when BOUNCE_TO_COLOR
      'BOUNCETOCOLOR'
    when CAT_TOY
      'CATTOY'
    when CAT_TOY_TO_COLOR
      'CATTOYTOCOLOR'
    when FADE_TO_COLOR
      'FADETOCOLOR'
    when FIREWORKS
      'FIREWORKS'
    when METEOR
      'METEOR'
    when MULTIPIXEL_RUN
      'MULTIPIXELRUN'
    when MULTIPIXEL_RUN_TO_COLOR
      'MULTIPIXELRUNTOCOLOR'
    when RIPPLE
      'RIPPLE'
    when PIXEL_MARATHON
      'PIXELMARATHON'
    when PIXEL_RUN
      'PIXELRUN'
    when SMOOTH_CHASE
      'SMOOTHCHASE'
    when SMOOTH_FADE
      'SMOOTHFADE'
    when SPARKLE
      'SPARKLE'
    when SPARKLE_FADE
      'SPARKLEFADE'
    when SPARKLE_TO_COLOR
      'SPARKLETOCOLOR'
    when SPLAT
      'SPLAT'
    when STACK
      'STACK'
    when STACK_OVERFLOW
      'STACKOVERFLOW'
    when WIPE
      'WIPE'
    when END_ANIMATION
      'ENDANIMATION'
    else
      'COLOR'
    end
  end

end
