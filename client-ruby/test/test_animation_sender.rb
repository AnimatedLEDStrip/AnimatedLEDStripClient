require_relative 'helper'
require 'minitest'
require_relative '../lib/animation_sender'

class AnimationSenderTest < Minitest::Test
  def test_initialization
    sender = AnimationSender.new '10.0.0.254', 5

    assert_equal '10.0.0.254', sender.address
    assert_equal 5, sender.port
  end
end
