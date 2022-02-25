# freetype-java

freetype-java is a port of the [FreeType](https://github.com/freetype/freetype) library to java   
the library uses JNI to create a link between the Java library and the library that was written in C++

> **If you find any bugs make sure to report them in the [issues](https://github.com/Mat1337/freetype-java/issues) page**

# dependencies

**FreeType**: https://github.com/freetype/freetype

> **Note: Compiling with different versions of FreeType**

[FreeType](https://github.com/freetype/freetype) is packaged with the library, if you wish to use a newer version of the library you will have to compile the library yourself and replace the static & dynamic libraries in the */lib* directory

# support

Currently the library only supports the following operating systems:

> **Windows**   
> **Linux**   

Library will be avaiable on Mac later

# compiling the library

1) git clone https://github.com/Mat1337/freetype-java.git
2) cd freetype-java
3) mvn clean package
