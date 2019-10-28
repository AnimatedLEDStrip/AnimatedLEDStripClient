//
// Created by mnmax on 10/28/2019.
//

#ifndef ANIMATEDLEDSTRIPCLIENT_COLORCONTAINER_H
#define ANIMATEDLEDSTRIPCLIENT_COLORCONTAINER_H

#include <vector>
#include <string>
#include <cstring>
#include <iomanip>
#include <sstream>

struct ColorContainer {
    std::vector<long> colors;

    ColorContainer *addColor(long c) {
        colors.push_back(c);
        return this;
    }

    int json(char **buff) {
        std::string data = R"({"colors":[)";

        for (auto c : colors) {
            data.append(std::to_string(c));
            data.append(",");
        }
        data.pop_back();
        data.append("]}");

        strcpy(*buff, data.c_str());
        return data.size();
    }
};

#endif  // ANIMATEDLEDSTRIPCLIENT_COLORCONTAINER_H
