# set the minimum cmake version required
cmake_minimum_required(VERSION 3.10)

# set the project name
project(freetype-native)

# set the cmake standards
set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# set output directories for all builds (Debug, Release, etc.)
foreach( OUTPUTCONFIG ${CMAKE_CONFIGURATION_TYPES} )
    string( TOUPPER ${OUTPUTCONFIG} OUTPUTCONFIG )
    set( CMAKE_ARCHIVE_OUTPUT_DIRECTORY_${OUTPUTCONFIG} ${LIBRARY_EXPORT_PATH}/ )
    set( CMAKE_LIBRARY_OUTPUT_DIRECTORY_${OUTPUTCONFIG} ${LIBRARY_EXPORT_PATH}/ )
    set( CMAKE_RUNTIME_OUTPUT_DIRECTORY_${OUTPUTCONFIG} ${NATIVE_EXPORT_PATH}/ )
endforeach( OUTPUTCONFIG CMAKE_CONFIGURATION_TYPES )

# set the free type include directory
set(FREE_TYPE_INCLUDE_DIR "${FREE_TYPE_DIR}/include")

# set the jvm library directories
if (UNIX)
  if (APPLE)
    message(FATAL_ERROR "Un supported OS '${CMAKE_SYSTEM_NAME}'")
  else ()
    set(JAVA_INCLUDE_DIR "${JAVA_PATH}/include")
    set(JAVA_INCLUDE_BRIDGE_DIR "${JAVA_PATH}/include/linux")
    set(JAVA_LIBRARY_DIR "${JAVA_PATH}/lib")
  endif ()
elseif (CMAKE_SYSTEM_NAME MATCHES "Windows")
    set(JAVA_INCLUDE_DIR "${JAVA_PATH}/include")
    set(JAVA_INCLUDE_BRIDGE_DIR "${JAVA_PATH}/include/win32")
    set(JAVA_LIBRARY_DIR "${JAVA_PATH}/lib")
else ()
  message(FATAL_ERROR "Unknown OS '${CMAKE_SYSTEM_NAME}'")
endif ()

# include all the libraries
include_directories("${JAVA_INCLUDE_DIR}" "${JAVA_INCLUDE_BRIDGE_DIR}" "${FREE_TYPE_INCLUDE_DIR}")

# set all the library directories
link_directories("${JAVA_LIBRARY_DIR}" "${FREE_TYPE_DIR}")

# link all the libraries
if (UNIX)
    link_libraries(jawt freetype)
else ()
    link_libraries(jvm freetype)
endif ()

# get all the source files
file(GLOB_RECURSE SOURCE_LIST "src/*.cpp" "src/*.hpp")

# add the library
add_library(freetype-native SHARED "${SOURCE_LIST}")

# set up the export properties
if (UNIX)
    set_target_properties(freetype-native
        PROPERTIES
        ARCHIVE_OUTPUT_DIRECTORY "${NATIVE_EXPORT_PATH}/"
        LIBRARY_OUTPUT_DIRECTORY "${NATIVE_EXPORT_PATH}/"
        RUNTIME_OUTPUT_DIRECTORY "${NATIVE_EXPORT_PATH}/"
    )
endif ()