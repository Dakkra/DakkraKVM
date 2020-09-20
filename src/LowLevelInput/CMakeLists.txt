cmake_minimum_required(VERSION 3.17)
project(LowLevelInput C)

set(CMAKE_C_STANDARD 14)

find_package(JNI REQUIRED)
include_directories(${JNI_INCLUDE_DIRS})
add_library(LowLevelInput c/lowLevelInput.c)
