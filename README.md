[![KDoc](https://img.shields.io/badge/KDoc-read-green.svg)](https://animatedledstrip.github.io/AnimatedLEDStripClient/animatedledstrip-client/index.html)

# AnimatedLEDStripClient
Helper library for connecting to an [AnimatedLEDStripServer](https://github.com/AnimatedLEDStrip/AnimatedLEDStripServer)

## Uses Java 9
Because we use the dokka plugin to generate our documentation, we must use Java <=9
> https://www.oracle.com/technetwork/java/javase/downloads/java-archive-javase9-3934878.html

## Maven Coordinates/Dependency
Use the following dependency to use this library in your project
> ```
> <dependency>
>   <groupId>io.github.animatedledstrip</groupId>
>   <artifactId>animatedledstrip-client</artifactId>
>   <version>0.2</version>
> </dependency>
> ```


## Snapshots
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
>     <version>0.3-SNAPSHOT</version>
>   </dependency>
> </dependencies>
