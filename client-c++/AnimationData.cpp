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

const char *direction_string(enum Direction d) {
    switch (d) {
        case FORWARD:
            return "FORWARD";
        case BACKWARD:
            return "BACKWARD";
    }
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

