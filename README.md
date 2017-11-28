# Quick start

## Run Test

    $ ./sbt test

This command launches our test suite.

## Run Application

    $ ./sbt run

This command launches [KafkaStorm](src/main/scala/com/miguno/kafkastorm/storm/topologies/KafkaStormDemo.scala).

This demo starts in-memory instances of ZooKeeper, Kafka, and Storm.  It then runs a demo Storm topology that connectsto and reads from the Kafka instance.