require 'minitest/autorun'
require_relative '../lib/animation'
require_relative 'helper'

class AnimationTest < Minitest::Test
  def test_color_string
    assert_equal 'COLOR', Animation.string(Animation::COLOR)
  end

  def test_custom_animation_string
    assert_equal 'CUSTOMANIMATION', Animation.string(Animation::CUSTOM_ANIMATION)
  end

  def test_custom_rep_anim_string
    assert_equal 'CUSTOMREPETITIVEANIMATION', Animation.string(Animation::CUSTOM_REPETITIVE_ANIMATION)
  end

  def test_alternate_string
    assert_equal 'ALTERNATE', Animation.string(Animation::ALTERNATE)
  end

  def test_bounce_string
    assert_equal 'BOUNCE', Animation.string(Animation::BOUNCE)
  end

  def test_bounce_to_color_string
    assert_equal 'BOUNCETOCOLOR', Animation.string(Animation::BOUNCE_TO_COLOR)
  end

  def test_cat_toy_string
    assert_equal 'CATTOY', Animation.string(Animation::CAT_TOY)
  end

  def test_meteor_string
    assert_equal 'METEOR', Animation.string(Animation::METEOR)
  end

  def test_multipixel_run_string
    assert_equal 'MULTIPIXELRUN', Animation.string(Animation::MULTIPIXEL_RUN)
  end

  def test_multipixel_rtc_string
    assert_equal 'MULTIPIXELRUNTOCOLOR', Animation.string(Animation::MULTIPIXEL_RUN_TO_COLOR)
  end

  def test_ripple_string
    assert_equal 'RIPPLE', Animation.string(Animation::RIPPLE)
  end

  def test_pixel_marathon_string
    assert_equal 'PIXELMARATHON', Animation.string(Animation::PIXEL_MARATHON)
  end

  def test_pixel_run_string
    assert_equal 'PIXELRUN', Animation.string(Animation::PIXEL_RUN)
  end

  def test_smooth_chase_string
    assert_equal 'SMOOTHCHASE', Animation.string(Animation::SMOOTH_CHASE)
  end

  def test_smooth_fade_string
    assert_equal 'SMOOTHFADE', Animation.string(Animation::SMOOTH_FADE)
  end

  def test_sparkle_string
    assert_equal 'SPARKLE', Animation.string(Animation::SPARKLE)
  end

  def test_sparkle_fade_string
    assert_equal 'SPARKLEFADE', Animation.string(Animation::SPARKLE_FADE)
  end

  def test_sparkle_to_color_string
    assert_equal 'SPARKLETOCOLOR', Animation.string(Animation::SPARKLE_TO_COLOR)
  end

  def test_splat_string
    assert_equal 'SPLAT', Animation.string(Animation::SPLAT)
  end

  def test_stack_string
    assert_equal 'STACK', Animation.string(Animation::STACK)
  end

  def test_stack_overflow_string
    assert_equal 'STACKOVERFLOW', Animation.string(Animation::STACK_OVERFLOW)
  end

  def test_wipe_string
    assert_equal 'WIPE', Animation.string(Animation::WIPE)
  end

  def test_end_animation_string
    assert_equal 'ENDANIMATION', Animation.string(Animation::END_ANIMATION)
  end

  def test_default_string
    assert_equal 'COLOR', Animation.string(-1)
  end
end
