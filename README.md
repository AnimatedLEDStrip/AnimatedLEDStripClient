[![Build Status](https://travis-ci.com/AnimatedLEDStrip/AnimatedLEDStripClient.svg?branch=master)](https://travis-ci.com/AnimatedLEDStrip/AnimatedLEDStripClient)

|Language(s)|Documentation|Coverage|
|-|:-:|:-:|
|Kotlin/Java|[![KDoc](https://img.shields.io/badge/KDoc-read-green.svg)](https://animatedledstrip.github.io/AnimatedLEDStripClient/animatedledstrip-client/index.html)|[![codecov](https://codecov.io/gh/AnimatedLEDStrip/AnimatedLEDStripClient/branch/master/graph/badge.svg?flag=kotlinjava)](https://codecov.io/gh/AnimatedLEDStrip/AnimatedLEDStripClient)|
|C++|||
|Python 3||[![codecov](https://codecov.io/gh/AnimatedLEDStrip/AnimatedLEDStripClient/branch/master/graph/badge.svg?flag=python3)](https://codecov.io/gh/AnimatedLEDStrip/AnimatedLEDStripClient)|
|Go||[![codecov](https://codecov.io/gh/AnimatedLEDStrip/AnimatedLEDStripClient/branch/master/graph/badge.svg?flag=go)](https://codecov.io/gh/AnimatedLEDStrip/AnimatedLEDStripClient)|
|Ruby||[![codecov](https://codecov.io/gh/AnimatedLEDStrip/AnimatedLEDStripClient/branch/master/graph/badge.svg?flag=ruby)](https://codecov.io/gh/AnimatedLEDStrip/AnimatedLEDStripClient)|

# AnimatedLEDStripClient
This is a set of helper libraries for connecting to an [AnimatedLEDStripServer](https://github.com/AnimatedLEDStrip/AnimatedLEDStripServer) from clients, allowing the client to send animations to the server and (if supported) receive currently running animations from the server.
Supports clients written Kotlin, Java, C++, Python, Go and Ruby.

## Supported Functionality by Library

|Language(s)|Send Data|Receive Data|Event Callbacks|
|-|:-:|:-:|:-:|
|Kotlin/Java|Yes|Yes|Yes|
|C++|Yes|Yes*|No|
|Python 3|Yes|No|No|
|Go|Yes|No|No|
|Ruby|Yes|No|No|

*\*In Development*

Even if a library does not currently support a feature, it will likely be added in the future (if supported by the language).

See the individual wiki pages for each language for usage instructions.
