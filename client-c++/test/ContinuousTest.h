//
// Created by mnmax on 11/9/2019.
//

#ifndef ANIMATEDLEDSTRIPCLIENT_CONTINUOUSTEST_H
#define ANIMATEDLEDSTRIPCLIENT_CONTINUOUSTEST_H

#include "AnimationData.h"
#include "gtest/gtest.h"

namespace {

    TEST(Continuous, ContinuousString) {
        Continuous c = CONTINUOUS;
        Continuous nc = NONCONTINUOUS;
        Continuous d = DEFAULT;

        EXPECT_STREQ(continuous_string(c), "true");
        EXPECT_STREQ(continuous_string(nc), "false");
        EXPECT_STREQ(continuous_string(d), "null");
    }

    TEST(Continuous, ContinuousFromString) {
        std::string c = "true";
        std::string nc = "false";
        std::string d = "null";

        EXPECT_EQ(continuous_from_string(c.c_str()), CONTINUOUS);
        EXPECT_EQ(continuous_from_string(nc.c_str()), NONCONTINUOUS);
        EXPECT_EQ(continuous_from_string(d.c_str()), DEFAULT);
    }

}

#endif //ANIMATEDLEDSTRIPCLIENT_CONTINUOUSTEST_H
