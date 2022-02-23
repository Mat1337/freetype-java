# freetype-java

freetype-java is a port of the [FreeType](https://github.com/freetype/freetype) library to java

## maven

Add the JitPack repository to your build file
```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

Add the dependency
```xml
	<dependency>
	    <groupId>com.github.Mat1337</groupId>
	    <artifactId>freetype-java</artifactId>
	    <version>Tag</version>
	</dependency>
```

## gradle

Add it in your root build.gradle at the end of repositories:
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Add the dependency
```gradle
	dependencies {
	        implementation 'com.github.Mat1337:freetype-java:Tag'
	}
```
