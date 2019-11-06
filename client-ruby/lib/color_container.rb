class ColorContainer
  attr_accessor :colors

  def initialize
    @colors = []
  end

  # @return [String]
  def json
    @colors.each { |c| raise TypeError unless c.is_a? Integer }
    str = '{"colors":['
    @colors.each do |c|
      str.concat(c.to_s, ',')
    end
    str.delete_suffix! ','
    str + ']}'
  end

  def add_color(color)
    raise TypeError unless color.is_a? Integer

    @colors.push(color)
  end

end
