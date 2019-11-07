add_library(animation_data AnimationData.cpp)
target_include_directories(animation_data PUBLIC ${CMAKE_CURRENT_SOURCE_DIR})
target_compile_options(animation_data PUBLIC -Wall -pedantic)

add_library(animation_sender AnimationSender.cpp)
target_include_directories(animation_sender PUBLIC ${CMAKE_CURRENT_SOURCE_DIR})
target_link_libraries(animation_sender nlohmann_json::nlohmann_json)
target_compile_options(animation_sender PUBLIC -Wall -pedantic)
