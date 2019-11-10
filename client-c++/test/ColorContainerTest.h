//
// Created by mnmax on 11/9/2019.
//

#ifndef ANIMATEDLEDSTRIPCLIENT_COLORCONTAINERTEST_H
#define ANIMATEDLEDSTRIPCLIENT_COLORCONTAINERTEST_H

#include "ColorContainer.h"
#include "gtest/gtest.h"

namespace {

    TEST(ColorContainer, DefaultConstructor) {
        const ColorContainer cc = ColorContainer();

        EXPECT_TRUE(cc.colors.empty());
    }

    TEST(ColorContainer, AddColor) {
        ColorContainer cc = ColorContainer();

        cc.addColor(0xFF);

        EXPECT_FALSE(cc.colors.empty());
        EXPECT_TRUE(cc.colors.size() == 1);
        EXPECT_EQ(cc.colors[0], 0xFF);

        cc.addColor(0xFF00);

        EXPECT_FALSE(cc.colors.empty());
        EXPECT_TRUE(cc.colors.size() == 2);
        EXPECT_EQ(cc.colors[0], 0xFF);
        EXPECT_EQ(cc.colors[1], 0xFF00);
    }
}

#endif //ANIMATEDLEDSTRIPCLIENT_COLORCONTAINERTEST_H
