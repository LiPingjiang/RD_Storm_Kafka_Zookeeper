name := "kStorm_music_up"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2"

// https://mvnrepository.com/artifact/org.apache.curator/curator-test
libraryDependencies += "org.apache.curator" % "curator-test" % "4.0.0" exclude("org.slf4j", "slf4j-log4j12")
// https://mvnrepository.com/artifact/org.apache.kafka/kafka
libraryDependencies += "org.apache.kafka" % "kafka_2.12" % "1.0.0" exclude("org.slf4j", "slf4j-log4j12")
// https://mvnrepository.com/artifact/commons-io/commons-io
libraryDependencies += "commons-io" % "commons-io" % "2.6"
// https://mvnrepository.com/artifact/org.apache.storm/storm-core
libraryDependencies += "org.apache.storm" % "storm-core" % "1.1.1"
// https://mvnrepository.com/artifact/org.apache.storm/storm-kafka
libraryDependencies += "org.apache.storm" % "storm-kafka" % "1.1.1"

resolvers ++= Seq(
  "typesafe-repository" at "http://repo.typesafe.com/typesafe/releases/",
  "clojars-repository" at "https://clojars.org/repo"
)
