//
// Created by mnmax on 10/27/2019.
//


#ifndef ANIMATEDLEDSTRIPCLIENT_ANIMATIONDATA_H
#define ANIMATEDLEDSTRIPCLIENT_ANIMATIONDATA_H

#include <string>

#define MAX_LEN 10000

enum Animation {
    COLOR,
    CUSTOMANIMATION,
    CUSTOMREPETITIVEANIMATION,
    ALTERNATE,
    BOUNCE,
    BOUNCETOCOLOR,
    CATTOY,
    METEOR,
    MULTIPIXELRUN,
    MULTIPIXELRUNTOCOLOR,
    RIPPLE,
    PIXELMARATHON,
    PIXELRUN,
    SMOOTHCHASE,
    SMOOTHFADE,
    SPARKLE,
    SPARKLEFADE,
    SPARKLETOCOLOR,
    SPLAT,
    STACK,
    STACKOVERFLOW,
    WIPE
};

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

enum Continuous {
    CONTINUOUS,
    NONCONTINUOUS,
    DEFAULT
};

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

enum Direction {
    FORWARD,
    BACKWARD
};

const char *direction_string(enum Direction d) {
    switch (d) {
        case FORWARD:
            return "FORWARD";
        case BACKWARD:
            return "BACKWARD";
    }
}

struct AnimationData {

    Animation animation = COLOR;
    // colors
    int center = -1;
    Continuous continuous = DEFAULT;
    long delay = -1;
    double delay_mod = 1.0;
    Direction direction = FORWARD;
    int distance = -1;
    int end_pixel = -1;
    std::string id = "";
    int spacing = -1;
    int start_pixel = 0;


    int json(char **buff) {
        std::string data = "DATA:{";

        data.append(R"("animation":")");
        data.append(animation_string(animation));

        data.append(R"(","center":")");
        data.append(std::to_string(center));

        data.append(R"(","continuous":")");
        data.append(continuous_string(continuous));

        data.append(R"(","delay":")");
        data.append(std::to_string(delay));

        data.append(R"(","delayMod":")");
        data.append(std::to_string(delay_mod));

        data.append(R"(","direction":")");
        data.append(direction_string(direction));

        data.append(R"(","distance":")");
        data.append(std::to_string(distance));

        data.append(R"(","endPixel":")");
        data.append(std::to_string(end_pixel));

        data.append(R"(","id":")");
        data.append(id);

        data.append(R"(","spacing":")");
        data.append(std::to_string(spacing));

        data.append(R"(","startPixel":")");
        data.append(std::to_string(start_pixel));

        data.append(R"("})");

        strcpy(*buff, data.c_str());
        printf("%s", *buff);
        return data.size();
    }
};


#endif  // ANIMATEDLEDSTRIPCLIENT_ANIMATIONDATA_H
