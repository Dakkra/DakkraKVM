cmake_minimum_required(VERSION 3.17)
project(LowLevelInput)

set(CMAKE_CXX_STANDARD 14)

find_package(JNI REQUIRED)
include_directories(${JNI_INCLUDE_DIRS})
add_library(LowLevelInput cpp/lowLevelInput.cpp)
