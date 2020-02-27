pub struct ColorContainer {
    pub colors: Vec<u32>
}

impl ColorContainer {
    pub fn add_color(&mut self, color: u32) {
        self.colors.push(color)
    }

    pub fn to_string(&self) -> String {
        "{\"colors\":".to_string() + &format!("{:?}", self.colors) + "}"
    }

    pub fn new() -> ColorContainer {
        ColorContainer {
            colors: vec![]
        }
    }
}

#[cfg(test)]
#[cfg_attr(tarpaulin, skip)]
mod tests {
    use crate::color_container::ColorContainer;

    #[test]
    fn test_add_color() {
        let mut c = ColorContainer::new();
        c.add_color(0xFF);

        assert_eq!(c.colors.len(), 1);
        assert_eq!(c.colors[0], 0xFF);
    }
}
