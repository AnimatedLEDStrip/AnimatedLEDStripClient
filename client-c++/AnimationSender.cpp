//
// Created by mnmax on 11/1/2019.
//

#include "AnimationSender.h"

void *AnimationSender::receiverLoop(void *args) {
    AnimationSender sender = *((AnimationSender *) args);
    int ret;
    char *buff = new char[MAX_LEN];
    if ((ret = recv(sender.socket_desc, buff, MAX_LEN - 1, 0)) < 0)
        printf("error %d", ret);
    printf("%s", buff);
    return nullptr;
}


int AnimationSender::start() {
    if (connect() < 0) {
        perror("Could not connect");
        return -1;
    }
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
    sleep(5);
    return 0;
}
