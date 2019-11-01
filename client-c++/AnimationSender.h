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
#include "AnimationData.h"

class AnimationSender {

    std::string host_name;
    int socket_desc;    // socket descriptor
    int port_num;
    struct sockaddr_in sa;

    pthread_t receiver_handle;

public:
    AnimationSender(const std::string &host, int port) {
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


    int sendAnimation(struct AnimationData &);

private:
    int connect();

    static void *receiverLoop(void *);
};

#endif // ANIMATEDLEDSTRIPCLIENT_ANIMATIONSENDER_H
