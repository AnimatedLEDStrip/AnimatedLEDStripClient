cmake_minimum_required(VERSION 3.5)
project(AnimatedLEDStripClient LANGUAGES CXX)
set(AnimatedLEDStripClient_VERSION_MAJOR 0)
set(AnimatedLEDStripClient_VERSION_MINOR 6)

set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

set(THREADS_PREFER_PTHREAD_FLAG_ON)

set(CMAKE_CXX_FLAGS="-g -O0 -Wall -fprofile-arcs -ftest-coverage")
set(CMAKE_EXE_LINKER_FLAGS="-fprofile-arcs -ftest-coverage")
find_package(Threads REQUIRED)

include(CTest)

include(DownloadProject.cmake)
download_project(PROJ               googletest
                 GIT_REPOSITORY     https://github.com/google/googletest.git
                 GIT_TAG            master
        )


download_project(PROJ               json
                 GIT_REPOSITORY     https://github.com/nlohmann/json.git
                 GIT_TAG            master
        )

include(CodeCoverage.cmake)

set(JSON_BuildTests OFF CACHE INTERNAL "")

add_subdirectory(${googletest_SOURCE_DIR} ${googletest_BINARY_DIR})
add_subdirectory(${json_SOURCE_DIR} ${json_BINARY_DIR})

add_subdirectory(src)

#add_library(animation_data src/AnimationData.cpp)
#add_library(animation_sender src/AnimationSender.cpp)
#add_executable(testexec test/AnimationDataTest.cpp)
#target_link_libraries(testexec animation_sender)
#target_link_libraries(testexec animation_data)
#target_link_libraries(testexec Threads::Threads)
#target_link_libraries(testexec nlohmann_json::nlohmann_json)
#target_link_libraries(testexec gtest_main)

option (BUILD_TESTING "Build the testing tree." ON)
if (BUILD_TESTING AND (PROJECT_SOURCE_DIR STREQUAL CMAKE_SOURCE_DIR))
    enable_testing()
    APPEND_COVERAGE_COMPILER_FLAGS()
    set(COVERAGE_LCOV_EXCLUDES '/usr/include/c++/*' 'test/*')
    SETUP_TARGET_FOR_COVERAGE_LCOV(
            NAME    test_coverage
            EXECUTABLE test/animation_data_test
    )
    add_subdirectory(test)
endif()
