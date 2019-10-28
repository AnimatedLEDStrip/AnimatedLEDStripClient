//
// Created by mnmax on 10/27/2019.
//


#ifndef ANIMATEDLEDSTRIPCLIENT_ANIMATIONDATA_H
#define ANIMATEDLEDSTRIPCLIENT_ANIMATIONDATA_H

#include <string>
#include "ColorContainer.h"

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

const char *animation_string(Animation a);

enum Continuous {
    CONTINUOUS,
    NONCONTINUOUS,
    DEFAULT
};

const char *continuous_string(enum Continuous c);

enum Direction {
    FORWARD,
    BACKWARD
};

const char *direction_string(enum Direction d);

struct AnimationData {

    Animation animation = COLOR;
    std::vector<ColorContainer> colors;
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

    AnimationData *setAnimation(enum Animation a);

    AnimationData *addColor(struct ColorContainer &c);

    AnimationData *setCenter(int c);

    AnimationData *setContinuous(enum Continuous c);

    AnimationData *setDelay(long d);

    AnimationData *setDelayMod(double d);

    AnimationData *setDirection(enum Direction d);

    AnimationData *setDistance(int d);

    AnimationData *setEndPixel(int p);

    AnimationData *setId(std::string &i);

    AnimationData *setId(char *i);

    AnimationData *setSpacing(int s);

    AnimationData *setStartPixel(int p);


    int json(char **buff) {
        std::string data = "DATA:{";

        data.append(R"("animation":")");
        data.append(animation_string(animation));

        data.append(R"(","colors":[)");

        char *cBuff = new char[MAX_LEN];
        for (ColorContainer c : colors) {
            cBuff[0] = 0;
            c.json(&cBuff);
            data.append(cBuff);
            data.append(",");
        }

        data.pop_back();

        data.append(R"(],"center":")");
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

        std::strcpy(*buff, data.c_str());
        return data.size();
    }
};


#endif  // ANIMATEDLEDSTRIPCLIENT_ANIMATIONDATA_H
