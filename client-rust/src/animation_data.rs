use crate::animation::Animation;
use crate::color_container::ColorContainer;
use crate::direction::Direction;
use crate::animation::Animation::Color;
use crate::direction::Direction::Forward;

pub struct AnimationData {
    pub animation: Animation,
    pub colors: Vec<ColorContainer>,
    pub center: i32,
    pub continuous: Option<bool>,
    pub delay: i32,
    pub delay_mod: f32,
    pub direction: Direction,
    pub distance: i32,
    pub end_pixel: i32,
    pub id: String,
    pub spacing: i32,
    pub start_pixel: i32,
}


impl AnimationData {
    pub fn new() -> AnimationData {
        AnimationData {
            animation: Color,
            colors: vec![],
            center: -1,
            continuous: None,
            delay: -1,
            delay_mod: 1.0,
            direction: Forward,
            distance: -1,
            end_pixel: -1,
            id: String::new(),
            spacing: -1,
            start_pixel: 0,
        }
    }

    pub fn add_color(&mut self, color: ColorContainer) -> &AnimationData {
        self.colors.push(color);
        self
    }

    fn color_json(&self) -> String {
        if self.colors.is_empty() {
            return String::new();
        }
        let mut json_str = String::new();
        for color in &self.colors {
            json_str = format!("{}{},", json_str, color.to_string());
        }
        json_str.pop();
        json_str
    }

    pub fn json(&self) -> String {
        let json = format!(
            "\"animation\":\"{}\",\"colors\":[{}],\"center\":{},\"continuous\":{},\
            \"delay\":{},\"delayMod\":{},\"direction\":\"{}\",\"distance\":{},\"endPixel\":{},\
            \"id\":\"{}\",\"spacing\":{},\"startPixel\":{}",
            self.animation.to_string(),
            self.color_json(),
            self.center,
            match self.continuous {
                None => "null",
                Some(ref x) => match x {
                    true => "true",
                    false => "false"
                }
            },
            self.delay,
            self.delay_mod,
            self.direction.to_string(),
            self.distance,
            self.end_pixel,
            self.id,
            self.spacing,
            self.start_pixel);

        let data = String::from("DATA:{");
        data + &json + "}"
    }
}


#[cfg(test)]
#[cfg_attr(tarpaulin, skip)]
mod tests {
    use crate::animation_data::AnimationData;
    use crate::animation::Animation::Meteor;
    use crate::direction::Direction::Backward;
    use crate::color_container::ColorContainer;

    #[test]
    fn test_json() {
        let json = "DATA:{\"animation\":\"METEOR\",\"colors\":[{\"colors\":[255, 65280]},\
        {\"colors\":[16711680]}],\"center\":50,\"continuous\":false,\"delay\":10,\"delayMod\":1.5,\
        \"direction\":\"BACKWARD\",\"distance\":45,\"endPixel\":200,\"id\":\"TEST\",\"spacing\":5,\
        \"startPixel\":15}";

        let mut data = AnimationData {
            animation: Meteor,
            colors: vec![],
            center: 50,
            continuous: Some(false),
            delay: 10,
            delay_mod: 1.5,
            direction: Backward,
            distance: 45,
            end_pixel: 200,
            id: String::from("TEST"),
            spacing: 5,
            start_pixel: 15,
        };

        let cc1 = ColorContainer {
            colors: vec![0xFF, 0xFF00]
        };

        let cc2 = ColorContainer {
            colors: vec![0xFF0000]
        };

        data.add_color(cc1);
        data.add_color(cc2);

        assert_eq!(data.json(), String::from(json))
    }
}
