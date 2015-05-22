spark-code-school
=================

[![Build Status](https://travis-ci.org/zwiehoo/spark-code-school.svg?branch=master)](https://travis-ci.org/zwiehoo/spark-code-school.svg)

Introduction:
-------------

Spark and Spark Streaming code school examples

WikiStreaming example
---------------------

To run WikiStreaming example you must have nodejs installed and run:

    $ cd wikichanges
    
On OSX run

    $ brew install icu4c
    
And then...
    
    $ npm install
    $ node wikichanges.js
    server bound
    ...

I's going to listen on port 8124 and stream wikipedia edit events from IRC channel broadcasts.


