//
// Created by mnmax on 10/27/2019.
//

#ifndef ANIMATEDLEDSTRIPCLIENT_ANIMATIONSENDER_H
#define ANIMATEDLEDSTRIPCLIENT_ANIMATIONSENDER_H

#include <netdb.h>
#include <netinet/in.h>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>
#include <pthread.h>
#include <sstream>
#include <vector>
#include <map>
#include "external/json/single_include/nlohmann/json.hpp"
#include "AnimationData.h"
#include "StripInfo.h"

class AnimationSender {

    std::string host_name;
    int socket_desc;    // socket descriptor
    int port_num;
    struct sockaddr_in sa;
    bool running = false;

    pthread_t receiver_handle;

    StripInfo *info;


public:
    std::map<std::string, AnimationData *> *running_animations;     // TODO: Make thread safe

    AnimationSender(const std::string &host, int port) {
        running_animations = new std::map<std::string, AnimationData *>;
        host_name = host;
        port_num = port;
        if ((socket_desc = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
            perror("socket()");
        }
        struct hostent *hp;
        if ((hp = gethostbyname(host.c_str())) == nullptr) {
            perror("gethostbyname()");
        }
        memset((char *) &sa, '\0', sizeof(sa));
        memcpy((char *) &sa.sin_addr.s_addr, hp->h_addr, hp->h_length);
        sa.sin_family = AF_INET;
        sa.sin_port = htons(port);
    }


    int start();

    int end();


    int sendAnimation(struct AnimationData &d);

    int endAnimation(const std::string& id);

    int endAnimation(struct AnimationData &d);

private:
    int connect();

    static void *receiverLoop(void *);
};

#endif // ANIMATEDLEDSTRIPCLIENT_ANIMATIONSENDER_H
