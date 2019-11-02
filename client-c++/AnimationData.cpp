//
// Created by mnmax on 10/28/2019.
//

#include "AnimationData.h"

const char *animation_string(Animation a) {
    switch (a) {
        case COLOR:
            return "COLOR";
        case CUSTOMANIMATION:
            return "CUSTOMANIMATION";
        case CUSTOMREPETITIVEANIMATION:
            return "CUSTOMREPETITIVEANIMATION";
        case ALTERNATE:
            return "ALTERNATE";
        case BOUNCE:
            return "BOUNCE";
        case BOUNCETOCOLOR:
            return "BOUNCETOCOLOR";
        case CATTOY:
            return "CATTOY";
        case METEOR:
            return "METEOR";
        case MULTIPIXELRUN:
            return "MULTIPIXELRUN";
        case MULTIPIXELRUNTOCOLOR:
            return "MULTIPIXELRUNTOCOLOR";
        case RIPPLE:
            return "RIPPLE";
        case PIXELMARATHON:
            return "PIXELMARATHON";
        case PIXELRUN:
            return "PIXELRUN";
        case SMOOTHCHASE:
            return "SMOOTHCHASE";
        case SMOOTHFADE:
            return "SMOOTHFADE";
        case SPARKLE:
            return "SPARKLE";
        case SPARKLEFADE:
            return "SPARKLEFADE";
        case SPARKLETOCOLOR:
            return "SPARKLETOCOLOR";
        case SPLAT:
            return "SPLAT";
        case STACK:
            return "STACK";
        case STACKOVERFLOW:
            return "STACKOVERFLOW";
        case WIPE:
            return "WIPE";
    }
}

Animation animation_from_string(const char *anim) {
    if (std::strcmp(anim, "COLOR") == 0)
        return COLOR;
    else if (std::strcmp(anim, "CUSTOMANIMATION") == 0)
        return CUSTOMANIMATION;
    else if (std::strcmp(anim, "CUSTOMREPETITIVEANIMATION") == 0)
        return CUSTOMREPETITIVEANIMATION;
    else if (std::strcmp(anim, "ALTERNATE") == 0)
        return ALTERNATE;
    else if (std::strcmp(anim, "BOUNCE") == 0)
        return BOUNCE;
    else if (std::strcmp(anim, "BOUNCETOCOLOR") == 0)
        return BOUNCETOCOLOR;
    else if (std::strcmp(anim, "CATTOY") == 0)
        return CATTOY;
    else if (std::strcmp(anim, "METEOR") == 0)
        return METEOR;
    else if (std::strcmp(anim, "MULTIPIXELRUN") == 0)
        return MULTIPIXELRUN;
    else if (std::strcmp(anim, "MULTIPIXELRUNTOCOLOR") == 0)
        return MULTIPIXELRUNTOCOLOR;
    else if (std::strcmp(anim, "RIPPLE") == 0)
        return RIPPLE;
    else if (std::strcmp(anim, "PIXELMARATHON") == 0)
        return PIXELMARATHON;
    else if (std::strcmp(anim, "PIXELRUN") == 0)
        return PIXELRUN;
    else if (std::strcmp(anim, "SMOOTHCHASE") == 0)
        return SMOOTHCHASE;
    else if (std::strcmp(anim, "SMOOTHFADE") == 0)
        return SMOOTHFADE;
    else if (std::strcmp(anim, "SPARKLE") == 0)
        return SPARKLE;
    else if (std::strcmp(anim, "SPARKLEFADE") == 0)
        return SPARKLEFADE;
    else if (std::strcmp(anim, "SPARKLETOCOLOR") == 0)
        return SPARKLETOCOLOR;
    else if (std::strcmp(anim, "SPLAT") == 0)
        return SPLAT;
    else if (std::strcmp(anim, "STACK") == 0)
        return STACK;
    else if (std::strcmp(anim, "STACKOVERFLOW") == 0)
        return STACKOVERFLOW;
    else if (std::strcmp(anim, "WIPE") == 0)
        return WIPE;
    else return COLOR;
}

const char *continuous_string(enum Continuous c) {
    switch (c) {
        case CONTINUOUS:
            return "true";
        case NONCONTINUOUS:
            return "false";
        case DEFAULT:
            return "null";
    }
}

Continuous continuous_from_string(const char *cont) {
    if (std::strcmp(cont, "true") == 0)
        return CONTINUOUS;
    else if (std::strcmp(cont, "false") == 0)
        return NONCONTINUOUS;
    else return DEFAULT;
}

const char *direction_string(enum Direction d) {
    switch (d) {
        case FORWARD:
            return "FORWARD";
        case BACKWARD:
            return "BACKWARD";
    }
}

Direction direction_from_string(const char * dir) {
    if (std::strcmp(dir, "FORWARD") == 0)
        return FORWARD;
    else if (std::strcmp(dir, "BACKWARD") == 0)
        return BACKWARD;
    else return FORWARD;
}

AnimationData *AnimationData::setAnimation(enum Animation a) {
    animation = a;
    return this;
}

AnimationData *AnimationData::addColor(struct ColorContainer &c) {
    colors.push_back(c);
    return this;
}

AnimationData *AnimationData::setCenter(int c) {
    center = c;
    return this;
}

AnimationData *AnimationData::setContinuous(enum Continuous c) {
    continuous = c;
    return this;
}

AnimationData *AnimationData::setDelay(long d) {
    delay = d;
    return this;
}

AnimationData *AnimationData::setDelayMod(double d) {
    delay_mod = d;
    return this;
}

AnimationData *AnimationData::setDirection(enum Direction d) {
    direction = d;
    return this;
}

AnimationData *AnimationData::setDistance(int d) {
    distance = d;
    return this;
}

AnimationData *AnimationData::setEndPixel(int p) {
    end_pixel = p;
    return this;
}

AnimationData *AnimationData::setId(std::string &i) {
    id.assign(i);
    return this;
}

AnimationData *AnimationData::setId(char *i) {
    id.assign(i);
    return this;
}

AnimationData *AnimationData::setSpacing(int s) {
    spacing = s;
    return this;
}

AnimationData *AnimationData::setStartPixel(int p) {
    start_pixel = p;
    return this;
}

