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
#include "AnimationData.h"

class AnimationSender {

    std::string host_name;
    int socket_desc;    // socket descriptor
    int port_num;
    struct sockaddr_in sa;

public:
    AnimationSender(std::string host, int port) {
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

    int start() {
        if (connect() < 0) {
            perror("Could not connect");
            return -1;
        }
        return 0;
    }

    int end() {
        if (close(socket_desc)<0) {
            perror("Could not close socket");
            return -1;
        }
        return 0;
    }

    int connect() {
        if (::connect(socket_desc, (struct sockaddr *) &sa, sizeof(sa)) < 0) {
            perror("connect()");
            return -1;
        }
        printf("Connected\n");
        return 0;
    }

    int sendAnimation(struct AnimationData &d) {
        char *buff = new char[MAX_LEN];
        int size = d.json(&buff);
        int ret;
        if ((ret = write(socket_desc, buff, size)) < 0)
            printf("error %d", ret);
        sleep(5);
        return 0;
    }


};

#endif // ANIMATEDLEDSTRIPCLIENT_ANIMATIONSENDER_H
