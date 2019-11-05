require 'socket'
require_relative './animation_sender.rb'

class AnimationSender
  attr_accessor :address, :port

  def initialize(address, port)
    @address = address
    @port = port
  end

  def start
    @socket = TCPSocket.new @address, @port
  end

  def end
    @socket.close
  end

  def send_animation(animation)
    raise TypeError unless animation.is_a? AnimationData

    @socket.write animation.json
  end

end
