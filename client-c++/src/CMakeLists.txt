#  Copyright (c) 2019 AnimatedLEDStrip
#
#  of this software and associated documentation files (the "Software"), to deal
#  in the Software without restriction, including without limitation the rights
#  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
#  copies of the Software, and to permit persons to whom the Software is
#  furnished to do so, subject to the following conditions:
#
#  The above copyright notice and this permission notice shall be included in
#  all copies or substantial portions of the Software.
#
#  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
#  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
#  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
#  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
#  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
#  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
#  THE SOFTWARE.

add_library(animation_data AnimationData.cpp)
target_include_directories(animation_data PUBLIC ${CMAKE_CURRENT_SOURCE_DIR})
target_compile_options(animation_data PUBLIC -Wall -pedantic)

add_library(animation_sender AnimationSender.cpp)
target_include_directories(animation_sender PUBLIC ${CMAKE_CURRENT_SOURCE_DIR})
target_link_libraries(animation_sender nlohmann_json::nlohmann_json)
target_link_libraries(animation_sender Threads::Threads)
target_compile_options(animation_sender PUBLIC -Wall -pedantic)

add_library(running_animation_map RunningAnimationMap.cpp)
target_include_directories(running_animation_map PUBLIC ${CMAKE_CURRENT_SOURCE_DIR})
target_link_libraries(running_animation_map Threads::Threads)
target_compile_options(running_animation_map PUBLIC -Wall -pedantic)


