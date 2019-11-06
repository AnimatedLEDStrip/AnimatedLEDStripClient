require_relative 'helper'
require 'minitest'
require_relative '../lib/animation_data'

class AnimationDataTest < Minitest::Test
  def test_initialization
    anim = AnimationData.new

    assert_equal Animation::COLOR, anim.animation
    assert_equal(-1, anim.center)
    assert_nil anim.continuous
    assert_equal(-1, anim.delay)
    assert_equal 1.0, anim.delay_mod
    assert_equal Direction::FORWARD, anim.direction
    assert_equal(-1, anim.distance)
    assert_equal(-1, anim.end_pixel)
    assert_equal '', anim.id
    assert_equal(-1, anim.spacing)
    assert_equal 0, anim.start_pixel
  end

  def test_add_color
    cc = ColorContainer.new
    cc.add_color(0xFF)

    anim = AnimationData.new
    anim.add_color(cc)

    assert anim.colors.include? cc
  end
end
