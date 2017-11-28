package application

import topologies.LocalKafkaPrinterTopology
import utilits.ZookeeperKafkaManager

/**
  * Created by
  * User: pingjiangli
  * Date: 24/11/2017
  * Time: 1:00 PM
  */

object RD_KafkaStorm_Demo_App {

  def main(args: Array[String]) {
    val kafkaTopic = "testing"

    val zooKeeperKafkaManager = new ZookeeperKafkaManager
    zooKeeperKafkaManager.startZooKeeperAndKafka(kafkaTopic)

    for {z <- zooKeeperKafkaManager.getZookeepers} {
      val topology = new LocalKafkaPrinterTopology(z.connectString, kafkaTopic)
      topology.runTopologyLocally()//will sleep for 1h
    }
//    zooKeeperKafkaManager.stopZooKeeperAndKafka()
  }
}