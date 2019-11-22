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

#include "RunningAnimationMap.h"

AnimationData *RunningAnimationMap::load(const std::string &id) {
    rwlock->lock_shared();
    if (running_animations->count(id)){
        AnimationData *ret = (*running_animations)[id];
        rwlock->unlock_shared();
        return ret;
    } else {
        rwlock->unlock_shared();
        return nullptr;
    }
}

void RunningAnimationMap::insert(const std::string &id, AnimationData *data) {
    auto data_pair = std::pair<std::string, AnimationData *>(id, data);
    rwlock->lock();
    running_animations->insert(data_pair);
    rwlock->unlock();
}

void RunningAnimationMap::insert(const std::pair<std::string, AnimationData *> &data) {
    rwlock->lock();
    running_animations->insert(data);
    rwlock->unlock();
}

void RunningAnimationMap::erase(const std::string &id) {
    rwlock->lock();
    running_animations->erase(id);
    rwlock->unlock();
}

std::vector<std::string> *RunningAnimationMap::keys() {
    auto *keys = new std::vector<std::string>;

    rwlock->lock_shared();
    for (const auto &k : *running_animations) {
        keys->push_back(k.first);
    }
    rwlock->unlock_shared();
    return keys;
}

int RunningAnimationMap::count(const std::string &id) {
    rwlock->lock_shared();
    int cnt = running_animations->count(id);
    rwlock->unlock_shared();
    return cnt;
}

int RunningAnimationMap::size() {
    rwlock->lock_shared();
    int cnt = running_animations->size();
    rwlock->unlock_shared();
    return cnt;
}