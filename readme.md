# Data Challenge

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

Build a small program that downloads GitHub data and calculates a health score for open source projects hosted on GitHub. The scores shall be outputted to a simple CSV file.

# Technical Decision!
### Dependencies.

  - Jackson (API):  is a high-performance JSON processor for Java
  - AsyncHttpClient (AHC): allows Java applications to easily execute HTTP requests and asynchronously process HTTP responses. The library also supports the WebSocket Protocol. It's built on top of Netty. It's currently compiled on Java 8 but runs on Java 9 too.
  - LZF Compressor: is a Java library for encoding and decoding data in LZF format, written by Tatu Saloranta. Data format and algorithm based on original LZF library by Marc A Lehmann.
  - Commons IO: is a library of utilities to assist with developing IO functionality.
  - Guava: is a set of core libraries that includes new collection types (such as multimap and multiset), immutable collections, a graph library, and utilities for concurrency, I/O, hashing, primitives, strings, and more!
  - Opencsv is an easy-to-use CSV (comma-separated values) parser library for Java. Java 7 is currently the minimum supported version.
  
  ### Performance.
To make the application run faster, I have applied some strategies listed below:

  - Make asynchronous http request call.
  - Decompress zip file while downloading.
  - Analyzed data will be serialized at the collector data step and then de-serialize at the step of calculation health repos metrics.
  - Use multi-thread and parallel stream for downloading, analyzing and computing data.
  
The performance is still to improve if we use Protobuff to serialize and de-serialize instead of Json.

 ### Run.
 Run by execute "gradlew run" command, eg:
  >${ProjectDirectory}\gradlew run --args="2019-08-01T00:00:00Z 2019-09-01T00:00:00Z"

