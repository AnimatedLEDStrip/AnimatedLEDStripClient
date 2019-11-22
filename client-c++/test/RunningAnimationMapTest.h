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

#ifndef ANIMATEDLEDSTRIPCLIENT_RUNNINGANIMATIONMAPTEST_H
#define ANIMATEDLEDSTRIPCLIENT_RUNNINGANIMATIONMAPTEST_H

#include "RunningAnimationMap.h"
#include "gtest/gtest.h"

namespace {

    TEST(RunningAnimationMap, Insert) {
        RunningAnimationMap animMap = RunningAnimationMap();
        auto data = AnimationData();

        animMap.insert("TEST", &data);

        EXPECT_EQ(animMap.size(), 1);
        EXPECT_STREQ((*animMap.keys())[0].c_str(), "TEST");
        EXPECT_EQ(animMap.load("TEST"), &data);

        auto data2 = AnimationData();

        animMap.insert(std::pair<std::string, AnimationData *>("TEST2", &data2));
        EXPECT_EQ(animMap.size(), 2);
        EXPECT_STREQ((*animMap.keys())[0].c_str(), "TEST");
        EXPECT_EQ(animMap.load("TEST"), &data);
        EXPECT_STREQ((*animMap.keys())[1].c_str(), "TEST2");
        EXPECT_EQ(animMap.load("TEST2"), &data2);
    }

    TEST(RunningAnimationMap, Load) {
        RunningAnimationMap animMap = RunningAnimationMap();
        auto data = AnimationData();

        animMap.insert("TEST", &data);
        EXPECT_EQ(animMap.load("TEST"), &data);

        EXPECT_EQ(animMap.load("TEST2"), nullptr);
        EXPECT_EQ(animMap.size(), 1);
    }

    TEST(RunningAnimationMap, Erase) {
        RunningAnimationMap animMap = RunningAnimationMap();
        auto data = AnimationData();

        animMap.insert("TEST", &data);
        EXPECT_EQ(animMap.load("TEST"), &data);
        EXPECT_EQ(animMap.size(), 1);
        animMap.erase("TEST");
        EXPECT_EQ(animMap.size(), 0);
        EXPECT_EQ(animMap.load("TEST"), nullptr);
        EXPECT_EQ(animMap.size(), 0);
    }

    TEST(RunningAnimationMap, Keys) {
        RunningAnimationMap animMap = RunningAnimationMap();
        auto data = AnimationData();
        auto data2 = AnimationData();
        auto data3 = AnimationData();

        animMap.insert("TEST1", &data);
        animMap.insert("TEST2", &data2);
        animMap.insert("TEST3", &data3);

        EXPECT_EQ(animMap.keys()->size(), 3);
        EXPECT_STREQ((*animMap.keys())[0].c_str(), "TEST1");
        EXPECT_STREQ((*animMap.keys())[1].c_str(), "TEST2");
        EXPECT_STREQ((*animMap.keys())[2].c_str(), "TEST3");
    }

    TEST(RunningAnimationMap, Count) {
        RunningAnimationMap animMap = RunningAnimationMap();
        auto data = AnimationData();
        animMap.insert("TEST", &data);
        EXPECT_EQ(animMap.count("TEST"), 1);
        EXPECT_EQ(animMap.count("TEST1"), 0);
    }

}

#endif //ANIMATEDLEDSTRIPCLIENT_RUNNINGANIMATIONMAPTEST_H
