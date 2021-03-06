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

#include "AnimationSender.h"

using json = nlohmann::json;

#define DEBUG true

AnimationData *get_data_from_json(json data) {
    auto *d = new AnimationData();
    d->setAnimation(animation_from_string(((std::string) data["animation"]).c_str()));
    for (auto c : data["colors"]) {
        ColorContainer cc;
        for (int col : c["colors"])
            cc.addColor(col);
        d->addColor(cc);
    }
    d->setCenter(data["center"]);
    if (data["continuous"] == nullptr)
        d->setContinuous(DEFAULT);
    else
        d->setContinuous(continuous_from_bool(data["continuous"]));
    d->setDelay(data["delay"]);
    d->setDelayMod(data["delayMod"]);
    d->setDirection(direction_from_string(((std::string) data["direction"]).c_str()));
    d->setDistance(data["distance"]);
    d->setEndPixel(data["endPixel"]);
    d->id = data["id"];
    d->setSpacing(data["spacing"]);
    d->setStartPixel(data["startPixel"]);
    return d;
}

void *AnimationSender::receiverLoop(void *args) {
    AnimationSender sender = *((AnimationSender *) args);
    int ret;
    std::vector<std::string> tokens;
    char *buff = new char[MAX_LEN];

    while (sender.running) {
#if DEBUG
        printf("Waiting\n");
#endif
        if ((ret = recv(sender.socket_desc, buff, MAX_LEN - 1, 0)) < 0)
            printf("error %d", ret);
#if DEBUG
        printf("%s\n", buff);
#endif
        std::stringstream ss(buff);
        std::string token;
        tokens.clear();

        while (std::getline(ss, token, ';'))
            tokens.push_back(token);

        for (const auto &s : tokens) {
            if (strcmp(s.substr(0, 4).c_str(), "INFO") == 0) {
                json data = json::parse(s.substr(5));
                int num = data["numLEDs"];
                sender.info = new StripInfo(num);
            } else if (strcmp(s.substr(0, 4).c_str(), "DATA") == 0) {
                json data = json::parse(s.substr(5));
                AnimationData *d = get_data_from_json(data);
                if (d->animation == ENDANIMATION)
                    sender.running_animations->erase(d->id);
                else
                    sender.running_animations->insert(d->id, d);
            }
        }
        for (int i = 0; i < MAX_LEN; i++) buff[i] = 0;
    }
    return nullptr;
}


int AnimationSender::start() {
    if (connect() < 0) {
        perror("Could not connect");
        return -1;
    }
    running = true;
    pthread_create(
            &receiver_handle,
            nullptr,
            receiverLoop,
            this);
    return 0;
}

int AnimationSender::end() {
    if (close(socket_desc) < 0) {
        perror("Could not close socket");
        return -1;
    }
    running = false;
    pthread_cancel(receiver_handle);
    return 0;
}

int AnimationSender::connect() {
    if (::connect(socket_desc, (struct sockaddr *) &sa, sizeof(sa)) < 0) {
        perror("connect()");
        return -1;
    }
    printf("Connected\n");
    return 0;
}

int AnimationSender::sendAnimation(struct AnimationData &d) {
    char *buff = new char[MAX_LEN];
    int size = d.json(&buff);
    int ret;
    if ((ret = write(socket_desc, buff, size)) < 0)
        printf("error %d", ret);
    return 0;
}

int AnimationSender::endAnimation(const std::string &id) {
    if (running_animations->count(id) == 0)
        return 1;
    else {
        AnimationData *d = running_animations->load(id);
        d->setAnimation(ENDANIMATION);
        char *buff = new char[MAX_LEN];

        int size = d->json(&buff);
        int ret;
        if ((ret = write(socket_desc, buff, size)) < 0)
            printf("error %d", ret);
        sleep(1);
        return 0;
    }
}

int AnimationSender::endAnimation(struct AnimationData &d) {
    char *buff = new char[MAX_LEN];
    int size = d.json(&buff);
    int ret;
    if ((ret = write(socket_desc, buff, size)) < 0)
        printf("error %d", ret);
    sleep(5);
    return 0;
}
