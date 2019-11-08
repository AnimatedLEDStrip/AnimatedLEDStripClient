add_executable(run_tests run_tests.cpp)

target_link_libraries(run_tests animation_data)
target_link_libraries(run_tests gtest)
target_link_libraries(run_tests gcov)

add_test(NAME runtests COMMAND run_tests)