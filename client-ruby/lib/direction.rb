module Direction
  FORWARD = 0
  BACKWARD = 1

  def self.string(direction)
    case direction
    when FORWARD
      'FORWARD'
    when BACKWARD
      'BACKWARD'
    else
      'FORWARD'
    end
  end
end