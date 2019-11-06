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

    assert_raises TypeError do
      anim.add_color(0xFF)
    end
  end

  def test_json
    anim = AnimationData.new
    anim.animation = Animation::METEOR
    anim.center = 50
    anim.continuous = false
    anim.delay = 10
    anim.delay_mod = 1.5
    anim.direction = Direction::BACKWARD
    anim.distance = 45
    anim.end_pixel = 200
    anim.id = 'TEST'
    anim.spacing = 5
    anim.start_pixel = 15

    cc = ColorContainer.new
    cc2 = ColorContainer.new
    cc.add_color 0xFF
    cc.add_color 0xFF00
    cc2.add_color 0xFF0000

    anim.add_color cc
    anim.add_color cc2

    assert_equal 'DATA:{"animation":"METEOR","colors":[{'\
    '"colors":[255,65280]},{"colors":[16711680]}],"center":50,'\
    '"continuous":false,"delay":10,"delayMod":1.5,'\
    '"direction":"BACKWARD","distance":45,"endPixel":200,'\
    '"id":"TEST","spacing":5,"startPixel":15}', anim.json
  end

  def test_json_animation_failure
    anim = AnimationData.new

    anim.animation = 'A'
    assert_raises TypeError do
      anim.json
    end
  end

  def test_json_center_failure
    anim = AnimationData.new

    anim.center = 'A'
    assert_raises TypeError do
      anim.json
    end
  end

  def test_json_continuous_failure
    anim = AnimationData.new

    anim.continuous = 'A'
    assert_raises TypeError do
      anim.json
    end
  end

  def test_json_delay_failure
    anim = AnimationData.new

    anim.delay = 'A'
    assert_raises TypeError do
      anim.json
    end
  end

  def test_json_delay_mod_failure
    anim = AnimationData.new

    anim.delay_mod = 'A'
    assert_raises TypeError do
      anim.json
    end
  end

  def test_json_direction_failure
    anim = AnimationData.new

    anim.direction = 'A'
    assert_raises TypeError do
      anim.json
    end
  end

  def test_json_distance_failure
    anim = AnimationData.new

    anim.distance = 'A'
    assert_raises TypeError do
      anim.json
    end
  end

  def test_json_end_pixel_failure
    anim = AnimationData.new

    anim.end_pixel = 'A'
    assert_raises TypeError do
      anim.json
    end
  end

  def test_json_id_failure
    skip
    anim = AnimationData.new

    anim.continuous = nil
    assert_raises TypeError do
      anim.json
    end
  end

  def test_json_spacing_failure
    anim = AnimationData.new

    anim.spacing = 'A'
    assert_raises TypeError do
      anim.json
    end
  end

  def test_json_start_pixel_failure
    anim = AnimationData.new

    anim.start_pixel = 'A'
    assert_raises TypeError do
      anim.json
    end
  end

  def test_json_colors_failure
    anim = AnimationData.new

    anim.colors = anim.colors.append 'A'

    assert_raises TypeError do
      anim.json
    end

    anim.colors = 'A'
    assert_raises TypeError do
      anim.json
    end
  end
end
