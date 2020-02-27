#[derive(Debug, Eq, PartialEq)]
pub enum Direction {
    Forward,
    Backward,
}

impl Direction {
    pub fn to_string(&self) -> &str {
        match self {
            Direction::Forward => "FORWARD",
            Direction::Backward => "BACKWARD",
        }
    }
}

pub fn direction_from_string(direction: &str) -> Direction {
    match &direction.to_uppercase()[..] {
        "FORWARD" => Direction::Forward,
        "BACKWARD" => Direction::Backward,
        _ => Direction::Forward
    }
}

#[cfg(test)]
#[cfg_attr(tarpaulin, skip)]
mod tests {
    use crate::direction::Direction;
    use crate::direction::direction_from_string;

    #[test]
    fn test_dir_to_string() {
        assert_eq!(Direction::Forward.to_string(), "FORWARD");
        assert_eq!(Direction::Backward.to_string(), "BACKWARD");
    }

    #[test]
    fn test_dir_from_string() {
        assert_eq!(direction_from_string("FORWARD"), Direction::Forward);
        assert_eq!(direction_from_string("BACKWARD"), Direction::Backward);
        assert_eq!(direction_from_string("OTHER"), Direction::Forward);
    }
}