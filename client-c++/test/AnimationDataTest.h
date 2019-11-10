/*
 *  Copyright (c) 2019 AnimatedLEDStrip
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

#include "AnimationData.h"
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

    TEST(AnimationData, SetContinuous) {
        AnimationData data = AnimationData();
        EXPECT_EQ(data.continuous, DEFAULT);
        data.setContinuous(CONTINUOUS);
        EXPECT_EQ(data.continuous, CONTINUOUS);
        data.setContinuous(NONCONTINUOUS);
        EXPECT_EQ(data.continuous, NONCONTINUOUS);
        data.setContinuous(DEFAULT);
        EXPECT_EQ(data.continuous, DEFAULT);
    }

    TEST(AnimationData, SetDelay) {
        AnimationData data = AnimationData();
        EXPECT_EQ(data.delay, -1);
        data.setDelay(30);
        EXPECT_EQ(data.delay, 30);
    }

    TEST(AnimationData, SetDelayMod) {
        AnimationData data = AnimationData();
        EXPECT_EQ(data.delay_mod, 1.0);
        data.setDelayMod(3.0);
        EXPECT_EQ(data.delay_mod, 3.0);
    }

    TEST(AnimationData, SetDirection) {
        AnimationData data = AnimationData();
        EXPECT_EQ(data.direction, FORWARD);
        data.setDirection(BACKWARD);
        EXPECT_EQ(data.direction, BACKWARD);
        data.setDirection(FORWARD);
        EXPECT_EQ(data.direction, FORWARD);
    }

    TEST(AnimationData, SetDistance) {
        AnimationData data = AnimationData();
        EXPECT_EQ(data.distance, -1);
        data.setDistance(50);
        EXPECT_EQ(data.distance, 50);
    }

    TEST(AnimationData, SetEndPixel) {
        AnimationData data = AnimationData();
        EXPECT_EQ(data.end_pixel, -1);
        data.setEndPixel(75);
        EXPECT_EQ(data.end_pixel, 75);
    }

    TEST(AnimationData, SetId) {
        AnimationData data = AnimationData();
        EXPECT_STREQ(data.id.c_str(), "");
        char test[5];
        strcpy(test, "TEST");
        data.setId(test);
        EXPECT_STREQ(data.id.c_str(), "TEST");
        std::string test2 = "TEST2";
        data.setId(test2);
        EXPECT_STREQ(data.id.c_str(), "TEST2");
    }

    TEST(AnimationData, SetSpacing) {
        AnimationData data = AnimationData();
        EXPECT_EQ(data.spacing, -1);
        data.setSpacing(5);
        EXPECT_EQ(data.spacing, 5);
    }

    TEST(AnimationData, SetStartPixel) {
        AnimationData data = AnimationData();
        EXPECT_EQ(data.start_pixel, 0);
        data.setStartPixel(3);
        EXPECT_EQ(data.start_pixel, 3);
    }
}