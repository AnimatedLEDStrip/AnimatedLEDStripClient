use crate::animation::Animation::*;

#[derive(Debug, Eq, PartialEq)]
pub enum Animation {
    Color,
    CustomAnimation,
    CustomRepetitiveAnimation,
    Alternate,
    Bounce,
    BounceToColor,
    CatToy,
    CatToyToColor,
    FadeToColor,
    Fireworks,
    Meteor,
    MultiPixelRun,
    MultiPixelRunToColor,
    Ripple,
    PixelMarathon,
    PixelRun,
    SmoothChase,
    SmoothFade,
    Sparkle,
    SparkleFade,
    SparkleToColor,
    Splat,
    Stack,
    StackOverflow,
    Wipe,
    EndAnimation,
}

impl Animation {
    pub fn to_string(&self) -> &str {
        match self {
            Color => "COLOR",
            CustomAnimation => "CUSTOMANIMATION",
            CustomRepetitiveAnimation => "CUSTOMREPETITIVEANIMATION",
            Alternate => "ALTERNATE",
            Bounce => "BOUNCE",
            BounceToColor => "BOUNCETOCOLOR",
            CatToy => "CATTOY",
            CatToyToColor => "CATTOYTOCOLOR",
            FadeToColor => "FADETOCOLOR",
            Fireworks => "FIREWORKS",
            Meteor => "METEOR",
            MultiPixelRun => "MULTIPIXELRUN",
            MultiPixelRunToColor => "MULTIPIXELRUNTOCOLOR",
            Ripple => "RIPPLE",
            PixelMarathon => "PIXELMARATHON",
            PixelRun => "PIXELRUN",
            SmoothChase => "SMOOTHCHASE",
            SmoothFade => "SMOOTHFADE",
            Sparkle => "SPARKLE",
            SparkleFade => "SPARKLEFADE",
            SparkleToColor => "SPARKLETOCOLOR",
            Splat => "SPLAT",
            Stack => "STACK",
            StackOverflow => "STACKOVERFLOW",
            Wipe => "WIPE",
            EndAnimation => "ENDANIMATION",
        }
    }
}

pub fn animation_from_string(animation: &str) -> Animation {
    match &animation.to_uppercase()[..] {
        "COLOR" => Color,
        "CUSTOMANIMATION" => CustomAnimation,
        "CUSTOMREPETITIVEANIMATION" => CustomRepetitiveAnimation,
        "ALTERNATE" => Alternate,
        "BOUNCE" => Bounce,
        "BOUNCETOCOLOR" => BounceToColor,
        "CATTOY" => CatToy,
        "CATTOYTOCOLOR" => CatToyToColor,
        "FADETOCOLOR" => FadeToColor,
        "FIREWORKS" => Fireworks,
        "METEOR" => Meteor,
        "MULTIPIXELRUN" => MultiPixelRun,
        "MULTIPIXELRUNTOCOLOR" => MultiPixelRunToColor,
        "RIPPLE" => Ripple,
        "PIXELMARATHON" => PixelMarathon,
        "PIXELRUN" => PixelRun,
        "SMOOTHCHASE" => SmoothChase,
        "SMOOTHFADE" => SmoothFade,
        "SPARKLE" => Sparkle,
        "SPARKLEFADE" => SparkleFade,
        "SPARKLETOCOLOR" => SparkleToColor,
        "SPLAT" => Splat,
        "STACK" => Stack,
        "STACKOVERFLOW" => StackOverflow,
        "WIPE" => Wipe,
        "ENDANIMATION" => EndAnimation,
        _ => Color
    }
}


#[cfg(test)]
#[cfg_attr(tarpaulin, skip)]
mod tests {
    use crate::animation::Animation::*;
    use crate::animation::animation_from_string;

    #[test]
    fn test_anim_to_string() {
        assert_eq!(Color.to_string(), "COLOR");
        assert_eq!(CustomAnimation.to_string(), "CUSTOMANIMATION");
        assert_eq!(CustomRepetitiveAnimation.to_string(), "CUSTOMREPETITIVEANIMATION");
        assert_eq!(Alternate.to_string(), "ALTERNATE");
        assert_eq!(Bounce.to_string(), "BOUNCE");
        assert_eq!(BounceToColor.to_string(), "BOUNCETOCOLOR");
        assert_eq!(CatToy.to_string(), "CATTOY");
        assert_eq!(CatToyToColor.to_string(), "CATTOYTOCOLOR");
        assert_eq!(FadeToColor.to_string(), "FADETOCOLOR");
        assert_eq!(Fireworks.to_string(), "FIREWORKS");
        assert_eq!(Meteor.to_string(), "METEOR");
        assert_eq!(MultiPixelRun.to_string(), "MULTIPIXELRUN");
        assert_eq!(MultiPixelRunToColor.to_string(), "MULTIPIXELRUNTOCOLOR");
        assert_eq!(Ripple.to_string(), "RIPPLE");
        assert_eq!(PixelMarathon.to_string(), "PIXELMARATHON");
        assert_eq!(PixelRun.to_string(), "PIXELRUN");
        assert_eq!(SmoothChase.to_string(), "SMOOTHCHASE");
        assert_eq!(SmoothFade.to_string(), "SMOOTHFADE");
        assert_eq!(Sparkle.to_string(), "SPARKLE");
        assert_eq!(SparkleFade.to_string(), "SPARKLEFADE");
        assert_eq!(SparkleToColor.to_string(), "SPARKLETOCOLOR");
        assert_eq!(Splat.to_string(), "SPLAT");
        assert_eq!(Stack.to_string(), "STACK");
        assert_eq!(StackOverflow.to_string(), "STACKOVERFLOW");
        assert_eq!(Wipe.to_string(), "WIPE");
        assert_eq!(EndAnimation.to_string(), "ENDANIMATION");
    }

    #[test]
    fn test_anim_from_string() {
        assert_eq!(animation_from_string("COLOR"), Color);
        assert_eq!(animation_from_string("CUSTOMANIMATION"), CustomAnimation);
        assert_eq!(animation_from_string("CUSTOMREPETITIVEANIMATION"), CustomRepetitiveAnimation);
        assert_eq!(animation_from_string("ALTERNATE"), Alternate);
        assert_eq!(animation_from_string("BOUNCE"), Bounce);
        assert_eq!(animation_from_string("BOUNCETOCOLOR"), BounceToColor);
        assert_eq!(animation_from_string("CATTOY"), CatToy);
        assert_eq!(animation_from_string("CATTOYTOCOLOR"), CatToyToColor);
        assert_eq!(animation_from_string("FADETOCOLOR"), FadeToColor);
        assert_eq!(animation_from_string("FIREWORKS"), Fireworks);
        assert_eq!(animation_from_string("METEOR"), Meteor);
        assert_eq!(animation_from_string("MULTIPIXELRUN"), MultiPixelRun);
        assert_eq!(animation_from_string("MULTIPIXELRUNTOCOLOR"), MultiPixelRunToColor);
        assert_eq!(animation_from_string("RIPPLE"), Ripple);
        assert_eq!(animation_from_string("PIXELMARATHON"), PixelMarathon);
        assert_eq!(animation_from_string("PIXELRUN"), PixelRun);
        assert_eq!(animation_from_string("SMOOTHCHASE"), SmoothChase);
        assert_eq!(animation_from_string("SMOOTHFADE"), SmoothFade);
        assert_eq!(animation_from_string("SPARKLE"), Sparkle);
        assert_eq!(animation_from_string("SPARKLEFADE"), SparkleFade);
        assert_eq!(animation_from_string("SPARKLETOCOLOR"), SparkleToColor);
        assert_eq!(animation_from_string("SPLAT"), Splat);
        assert_eq!(animation_from_string("STACK"), Stack);
        assert_eq!(animation_from_string("STACKOVERFLOW"), StackOverflow);
        assert_eq!(animation_from_string("WIPE"), Wipe);
        assert_eq!(animation_from_string("ENDANIMATION"), EndAnimation);
        assert_eq!(animation_from_string("OTHER"), Color);
    }
}