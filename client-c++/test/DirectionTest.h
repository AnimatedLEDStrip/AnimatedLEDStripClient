//
// Created by mnmax on 11/9/2019.
//

#ifndef ANIMATEDLEDSTRIPCLIENT_DIRECTIONTEST_H
#define ANIMATEDLEDSTRIPCLIENT_DIRECTIONTEST_H

#include "AnimationData.h"
#include "gtest/gtest.h"

namespace {

    TEST(Direction, DirectionString) {
        Direction d = FORWARD;
        EXPECT_STREQ(direction_string(d), "FORWARD");

        d = BACKWARD;
        EXPECT_STREQ(direction_string(d), "BACKWARD");
    }

    TEST(Direction, DirectionFromString) {
        std::string str_f = "FORWARD";
        std::string str_b = "BACKWARD";
        std::string str = "FORWARD";

        EXPECT_EQ(direction_from_string(str_f.c_str()), FORWARD);
        EXPECT_EQ(direction_from_string(str_b.c_str()), BACKWARD);
        EXPECT_EQ(direction_from_string(str.c_str()), FORWARD);
    }
}

#endif //ANIMATEDLEDSTRIPCLIENT_DIRECTIONTEST_H
