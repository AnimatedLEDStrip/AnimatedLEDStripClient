//
// Created by mnmax on 11/9/2019.
//

#ifndef ANIMATEDLEDSTRIPCLIENT_STRIPINFO_H
#define ANIMATEDLEDSTRIPCLIENT_STRIPINFO_H

#include "AnimationSender.h"
#include "gtest/gtest.h"

namespace {

    TEST(StripInfo, DefaultConstructor) {
        auto s = StripInfo(10);
        EXPECT_EQ(s.numLEDs, 10);
    }

}

#endif //ANIMATEDLEDSTRIPCLIENT_STRIPINFO_H
