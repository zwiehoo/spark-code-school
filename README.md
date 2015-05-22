spark-code-school
=================

[![Build Status](https://travis-ci.org/zwiehoo/spark-code-school.svg?branch=master)](https://travis-ci.org/zwiehoo/spark-code-school.svg)

Introduction:
-------------

Spark and Spark Streaming code school examples

TextAnalysis example
--------------------

TextAnalysis example is supposed to go through basic concepts of Spark batch processing.
The concept goes through text file parsing, converting, splitting, word counting, computing some basic stats, normalizing.
All that should end up with partial implementation of TF-IDF algorithm (TF part)

WikiStreaming example
---------------------

WikiStreaming example goes through basic concepts of Spark Streaming.
There is a small utility which creates stream of wikipedia change events.

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

KafkaStreaming example
----------------------

This is a short example of Kafka Streaming based on Allegro [Allegro.pl](http://allegro.pl) internal events
