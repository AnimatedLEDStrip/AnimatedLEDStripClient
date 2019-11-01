cmake_minimum_required(VERSION 3.5)
project(AnimatedLEDStripClient LANGUAGES CXX)
set(AnimatedLEDStripClient_VERSION_MAJOR 0)
set(AnimatedLEDStripClient_VERSION_MINOR 6)

set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

set(THREADS_PREFER_PTHREAD_FLAG_ON)
find_package(Threads REQUIRED)

if ((NOT JSON_INCLUDE_DIR) OR (NOT EXISTS ${JSON_INCLUDE_DIR}))
    execute_process(COMMAND git submodule update --init -- external/json
            WORKING_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR})
    set(JSON_INCLUDE_DIR ${CMAKE_CURRENT_SOURCE_DIR}/external/json
            CACHE PATH "json include dir")
else ()
    add_subdirectory(external/json)
endif ()

add_library(animation_data AnimationData.cpp)
add_library(animation_sender AnimationSender.cpp)
add_executable(testexec test.cpp)
target_link_libraries(testexec animation_sender)
target_link_libraries(testexec animation_data)
target_link_libraries(testexec Threads::Threads)
target_link_libraries(testexec nlohmann_json::nlohmann_json)
