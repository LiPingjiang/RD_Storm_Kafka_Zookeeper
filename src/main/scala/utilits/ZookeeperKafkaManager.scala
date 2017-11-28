package utilits

import java.util.Properties

import kafka.KafkaEmbedded
import kafka.admin.{AdminUtils, RackAwareMode}
import kafka.utils.ZkUtils
import org.I0Itec.zkclient.ZkClient
import zookeeper.ZooKeeperEmbedded

import scala.concurrent.duration._

/**
  * Created by
  * User: pingjiangli
  * Date: 28/11/2017
  * Time: 11:34 AM
  */
class ZookeeperKafkaManager {

  private var zookeeperEmbedded: Option[ZooKeeperEmbedded] = None
  private var zkClient: Option[ZkClient] = None
  private var kafkaEmbedded: Option[KafkaEmbedded] = None

  /**
    * Launches in-memory, embedded instances of ZooKeeper and Kafka so that our demo Storm topology can connect to and
    * read from Kafka.
    */
  def startZooKeeperAndKafka(topic: String, numTopicPartitions: Int = 1, numTopicReplicationFactor: Int = 1,
                                     zookeeperPort: Int = 2181) {

    zookeeperEmbedded = Some(new ZooKeeperEmbedded(zookeeperPort))
    for {z <- zookeeperEmbedded} {
      val brokerConfig = new Properties
      brokerConfig.put("zookeeper.connect", z.connectString)
      kafkaEmbedded = Some(new KafkaEmbedded(brokerConfig))
      for {k <- kafkaEmbedded} {
        k.start()
      }

      val sessionTimeout = 30.seconds
      val connectionTimeout = 30.seconds

      val zkUtils = ZkUtils.apply(z.connectString, sessionTimeout.toMillis.toInt, connectionTimeout.toMillis.toInt, isZkSecurityEnabled = false)
      val topicConfig: Properties = new Properties // add per-topic configurations settings here
      AdminUtils.createTopic(zkUtils, topic, numTopicPartitions, numTopicReplicationFactor, topicConfig, RackAwareMode.Enforced)

      // Old Code for Storm <= 0.8

      //      zkClient = Some(new ZkClient(z.connectString, sessionTimeout.toMillis.toInt, connectionTimeout.toMillis.toInt,
      //        ZKStringSerializer))
      //      for {
      //        zc <- zkUtils
      //      } {
      //        val topicConfig = new Properties
      //        AdminUtils.createTopic(zc, topic, numTopicPartitions, numTopicReplicationFactor, topicConfig)
      //      }
    }
  }

  def stopZooKeeperAndKafka() {
    for {k <- kafkaEmbedded} k.stop()
    for {zc <- zkClient} zc.close()
    for {z <- zookeeperEmbedded} z.stop()
  }

  def getZookeepers() ={
    zookeeperEmbedded
  }

}
