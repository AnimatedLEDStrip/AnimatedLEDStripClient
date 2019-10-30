[![Build Status](https://travis-ci.com/AnimatedLEDStrip/AnimatedLEDStripClient.svg?branch=master)](https://travis-ci.com/AnimatedLEDStrip/AnimatedLEDStripClient)


|Kotlin/Java|C++|Python|
|:-:|:-:|:-:|
|[![KDoc](https://img.shields.io/badge/KDoc-read-green.svg)](https://animatedledstrip.github.io/AnimatedLEDStripClient/animatedledstrip-client/index.html)|||
|[![codecov](https://codecov.io/gh/AnimatedLEDStrip/AnimatedLEDStripClient/branch/master/graph/badge.svg)](https://codecov.io/gh/AnimatedLEDStrip/AnimatedLEDStripClient)||[![Coverage Status](https://coveralls.io/repos/github/AnimatedLEDStrip/AnimatedLEDStripClient/badge.svg?branch=master)](https://coveralls.io/github/AnimatedLEDStrip/AnimatedLEDStripClient?branch=master)|

# AnimatedLEDStripClient
Helper library for connecting to an [AnimatedLEDStripServer](https://github.com/AnimatedLEDStrip/AnimatedLEDStripServer)
Supports Kotlin, Java and C++ clients.

## Kotlin/Java
Supports sending animations to the server as well as receiving currently running animations from the server.
See the [wiki](https://github.com/AnimatedLEDStrip/AnimatedLEDStripClient/wiki) for details.

### Maven Coordinates/Dependency
Use the following dependency to use this library in your project
> ```
> <dependency>
>   <groupId>io.github.animatedledstrip</groupId>
>   <artifactId>animatedledstrip-client</artifactId>
>   <version>0.5</version>
> </dependency>
> ```


### Snapshots
Development versions of the AnimatedLEDStripClient library are available from the Sonatype snapshot repository:

> ```
> <repositories>
>    <repository>
>        <id>sonatype-snapshots</id>
>        <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
>        <snapshots>
>            <enabled>true</enabled>
>        </snapshots>
>    </repository>
> </repositories>
> 
> <dependencies>
>   <dependency>
>     <groupId>io.github.animatedledstrip</groupId>
>     <artifactId>animatedledstrip-client</artifactId>
>     <version>0.6-SNAPSHOT</version>
>   </dependency>
> </dependencies>

### Note About Building
Because we use the dokka plugin to generate our documentation, we must use Java <=9
> https://www.oracle.com/technetwork/java/javase/downloads/java-archive-javase9-3934878.html

## C++ (WIP)
Currently only supports sending animations to the server.
See the [wiki](https://github.com/AnimatedLEDStrip/AnimatedLEDStripClient/wiki) for details.

## Python 3 (WIP)
Currently only supports sending animations to the server.
See the [wiki](https://github.com/AnimatedLEDStrip/AnimatedLEDStripClient/wiki) for details.
