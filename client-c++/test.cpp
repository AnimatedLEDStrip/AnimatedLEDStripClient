//
// Created by mnmax on 11/1/2019.
//

#include "AnimationSender.h"

int main() {

    AnimationSender sender("10.44.157.2", 5);

    sender.start();

    sleep(10);

    AnimationData d;
    ColorContainer c;
    c.addColor(0xFF).addColor(0xFF00);
    d.animation = BOUNCE;
    d.addColor(c);
    sender.sendAnimation(d);

    sleep(20);
//    sender.end();

    for (std::pair<std::string, AnimationData *> a : *sender.running_animations) {
        char *b = new char[1000];
        a.second->json(&b);
        printf("%s=%s\n", a.first.c_str(), b);
        sender.endAnimation(a.first);
    }
    sleep(100);

    sender.end();
}