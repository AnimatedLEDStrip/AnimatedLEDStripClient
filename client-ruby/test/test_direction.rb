require_relative 'helper'
require 'minitest'
require_relative '../lib/direction'

class DirectionTest < Minitest::Test
  def test_direction_string
    assert_equal 'FORWARD', Direction.string(Direction::FORWARD)
    assert_equal 'BACKWARD', Direction.string(Direction::BACKWARD)
    assert_equal 'FORWARD', Direction.string(-1)
  end
end
