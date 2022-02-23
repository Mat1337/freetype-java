# freetype-java

freetype-java is a port of the [FreeType](https://github.com/freetype/freetype) library to java

# including the library

To include the library in your project follow the next steps

### maven

1) Add the JitPack repository to your build file
```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

2) Add the dependency
```xml
	<dependency>
	    <groupId>com.github.Mat1337</groupId>
	    <artifactId>freetype-java</artifactId>
	    <version>Tag</version>
	</dependency>
```

### gradle

1) Add it in your root build.gradle at the end of repositories:
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
2) Add the dependency
```gradle
	dependencies {
	        implementation 'com.github.Mat1337:freetype-java:Tag'
	}
```

# compiling the library

This library depends on [FreeType](https://github.com/freetype/freetype) so you will need to precompile that library first

1) git clone https://github.com/Mat1337/freetype-java.git
2) cd freetype-java
3) mkdir lib
4) move the freetype static library and the dynamic library to here
5) move the freetype include directory to here
6) cd ..
7) mvn clean package
8) last move the freetype dynamic library & the generated freetype-native dynamic library into the jar
