require_relative 'animation'
require_relative 'direction'
require_relative 'color_container'

class AnimationData
  attr_accessor :animation, :colors, :center,
                :continuous, :delay, :delay_mod,
                :direction, :distance, :end_pixel,
                :id, :spacing, :start_pixel

  def initialize
    @animation = Animation::COLOR
    @colors = []
    @center = -1
    @continuous = nil
    @delay = -1
    @delay_mod = 1.0
    @direction = Direction::FORWARD
    @distance = -1
    @end_pixel = -1
    @id = ''
    @spacing = -1
    @start_pixel = 0
  end

  def add_color(color)
    raise TypeError unless color.is_a? ColorContainer

    colors.push(color)
  end

  # @return [String]
  def json
    raise TypeError unless @animation.is_a? Integer
    raise TypeError unless @center.is_a? Integer
    raise TypeError unless @continuous.is_a?(Integer) || @continuous.nil?
    raise TypeError unless @delay.is_a? Integer
    raise TypeError unless @delay_mod.is_a? Float
    raise TypeError unless @direction.is_a? Integer
    raise TypeError unless @distance.is_a? Integer
    raise TypeError unless @end_pixel.is_a? Integer
    raise TypeError unless @id.is_a? String
    raise TypeError unless @spacing.is_a? Integer
    raise TypeError unless @start_pixel.is_a? Integer

    @colors.each { |cc| raise TypeError unless cc.is_a? ColorContainer }

    str = "DATA:{\"animation\":\"#{Animation.string(@animation)}\","\
    '"colors":['
    @colors.each { |cc| str += "#{cc.json}," }
    str.delete_suffix! ','
    str + '],'\
    "\"center\":#{@center},"\
    "\"continuous\":#{@continuous.nil? ? 'null' : @continuous},"\
    "\"delay\":#{@delay},"\
    "\"delayMod\":#{delay_mod},"\
    "\"direction\":\"#{Direction.string(@direction)}\","\
    "\"distance\":#{@distance},"\
    "\"endPixel\":#{@end_pixel},"\
    "\"id\":\"#{@id}\","\
    "\"spacing\":#{@spacing},"\
    "\"startPixel\":#{@start_pixel}}"
  end
end
