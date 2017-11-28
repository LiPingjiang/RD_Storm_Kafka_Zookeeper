# Quick start

## Run Test

    $ ./sbt test

This command launches our test suite.

## Run Application

    $ ./sbt run

This command launches [KafkaStorm](src/main/scala/com/miguno/kafkastorm/storm/topologies/KafkaStormDemo.scala).

This demo starts in-memory instances of ZooKeeper, Kafka, and Storm.  It then runs a demo Storm topology that connectsto and reads from the Kafka instance.

## Test With Kafka Command Line

First download the kafka from the official website: http://kafka.apache.org/downloads.html, unzip it.

    $ sh bin/kafka-topics.sh --list --zookeeper 127.0.0.1:2181
   
This command show the topics with the default demo application, which is "testing".

    $ sh bin/kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic testing

This command connect with the kafka and performs as a productor to send message to "testing" topic

