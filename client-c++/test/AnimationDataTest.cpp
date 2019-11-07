//
// Created by mnmax on 11/6/2019.
//

#include "../src/AnimationData.h"
#include "gtest/gtest.h"


namespace {

    TEST(AnimationData, DefaultConstructor) {
        const AnimationData data = AnimationData();

        EXPECT_EQ(data.animation, COLOR);
        EXPECT_EQ(data.center, -1);
        EXPECT_EQ(data.continuous, DEFAULT);
        EXPECT_EQ(data.delay, -1);
        EXPECT_EQ(data.delay_mod, 1.0);
        EXPECT_EQ(data.direction, FORWARD);
        EXPECT_EQ(data.distance, -1);
        EXPECT_EQ(data.end_pixel, -1);
        EXPECT_STREQ(data.id.c_str(), "");
        EXPECT_EQ(data.spacing, -1);
        EXPECT_EQ(data.start_pixel, 0);
    }

    TEST(AnimationData, SetAnimation) {
        AnimationData data = AnimationData();
        EXPECT_EQ(data.animation, COLOR);
        data.setAnimation(METEOR);
        EXPECT_EQ(data.animation, METEOR);
    }

    TEST(AnimationData, SetCenter) {
        AnimationData data = AnimationData();
        EXPECT_EQ(data.center, -1);
        data.setCenter(50);
        EXPECT_EQ(data.center, 50);
    }
}