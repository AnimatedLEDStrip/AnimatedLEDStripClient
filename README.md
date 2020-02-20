[![Build Status](https://travis-ci.com/AnimatedLEDStrip/AnimatedLEDStripClient.svg?branch=master)](https://travis-ci.com/AnimatedLEDStrip/AnimatedLEDStripClient)

|Language(s)|Documentation|Coverage||
|-|:-:|:-:|:-:|
|Kotlin/Java|[![KDoc](https://img.shields.io/badge/KDoc-read-green.svg)](https://animatedledstrip.github.io/AnimatedLEDStripClient/animatedledstrip-client/index.html)|[![codecov](https://codecov.io/gh/AnimatedLEDStrip/AnimatedLEDStripClient/branch/master/graph/badge.svg?flag=kotlinjava)](https://codecov.io/gh/AnimatedLEDStrip/AnimatedLEDStripClient)|[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.animatedledstrip/animatedledstrip-client/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.animatedledstrip/animatedledstrip-client)|
|C++||[![codecov](https://codecov.io/gh/AnimatedLEDStrip/AnimatedLEDStripClient/branch/master/graph/badge.svg?flag=cpp)](https://codecov.io/gh/AnimatedLEDStrip/AnimatedLEDStripClient)||
|Dart||[![codecov](https://codecov.io/gh/AnimatedLEDStrip/AnimatedLEDStripClient/branch/master/graph/badge.svg?flag=dart)](https://codecov.io/gh/AnimatedLEDStrip/AnimatedLEDStripClient)|[![pub package](https://img.shields.io/pub/v/animatedledstripclient.svg)](https://pub.dev/packages/animatedledstripclient)|
|Go|[![](https://godoc.org/github.com/AnimatedLEDStrip/AnimatedLEDStripClient/client-go?status.svg)](http://godoc.org/github.com/AnimatedLEDStrip/AnimatedLEDStripClient/client-go)|[![codecov](https://codecov.io/gh/AnimatedLEDStrip/AnimatedLEDStripClient/branch/master/graph/badge.svg?flag=go)](https://codecov.io/gh/AnimatedLEDStrip/AnimatedLEDStripClient)||
|Python 3||[![codecov](https://codecov.io/gh/AnimatedLEDStrip/AnimatedLEDStripClient/branch/master/graph/badge.svg?flag=python3)](https://codecov.io/gh/AnimatedLEDStrip/AnimatedLEDStripClient)|[![PyPI](https://img.shields.io/pypi/v/animatedledstrip-client.svg)](https://pypi.python.org/pypi/animatedledstrip-client)|
|Ruby||[![codecov](https://codecov.io/gh/AnimatedLEDStrip/AnimatedLEDStripClient/branch/master/graph/badge.svg?flag=ruby)](https://codecov.io/gh/AnimatedLEDStrip/AnimatedLEDStripClient)|[![Gem Version](https://badge.fury.io/rb/animatedledstrip-client.svg)](https://badge.fury.io/rb/animatedledstrip-client)|

# AnimatedLEDStripClient
This is a set of helper libraries for connecting to an [AnimatedLEDStripServer](https://github.com/AnimatedLEDStrip/AnimatedLEDStripServer) from clients, allowing the client to send animations to the server and (if supported) receive currently running animations from the server.
Supports clients written Kotlin, Java, C++, Python, Go, Ruby and Dart.

## Supported Functionality by Library

|Language(s)|Send Data|Receive Data|Event Callbacks|
|-|:-:|:-:|:-:|
|Kotlin/Java|Yes|Yes|Yes|
|C++|Yes|Yes|No|
|Dart|Yes|Yes|No|
|Go|Yes|Yes|No|
|Python 3|Yes|Yes|No|
|Ruby|Yes|No|No|


Even if a library does not currently support a feature, it will likely be added in the future (if supported by the language).

See the individual wiki pages for each language for usage instructions.
