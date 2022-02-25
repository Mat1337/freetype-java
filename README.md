# freetype-java

freetype-java is a port of the [FreeType](https://github.com/freetype/freetype) library to java   
the library uses JNI to create a link between the Java library and the library that was written in C++

> **If you find any bugs make sure to report them in the [issues](https://github.com/Mat1337/freetype-java/issues) page**

# dependencies

**FreeType**: https://github.com/freetype/freetype

> **Note: Compiling with different versions of FreeType**

FreeType is packaged with the library, if you wish to use a newer version of the library you will have to compile the [FreeType](https://github.com/freetype/freetype) library yourself and replace the static & dynamic libraries in the */lib* directory

# support

Currently the library only supports the following operating systems:

> **Windows**   
> **Linux**   

If you wish to use the library on any other operating systems, you will have to compile the [FreeType](https://github.com/freetype/freetype) for the target operating system and then compile the library with the compiled binaries 

# compiling the library

1) git clone https://github.com/Mat1337/freetype-java.git
2) cd freetype-java
3) mvn clean package
