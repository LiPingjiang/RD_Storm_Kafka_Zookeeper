package topologies

import bolts.printInputBolt
import org.apache.storm.{Config, LocalCluster}
import org.apache.storm.kafka.{KafkaSpout, SpoutConfig, StringScheme, ZkHosts}
import org.apache.storm.spout.SchemeAsMultiScheme
import org.apache.storm.topology.TopologyBuilder

import scala.concurrent.duration._

/**
  * Created by
  * User: pingjiangli
  * Date: 28/11/2017
  * Time: 2:39 PM
  */
class LocalKafkaPrinterTopology(kafkaZkConnect: String, topic: String, numTopicPartitions: Int = 1,
                                topologyName: String = "kafka-storm-starter", runtime: Duration = 1.hour){
  def runTopologyLocally() {
    val zkHosts = new ZkHosts(kafkaZkConnect)
    val topic = "testing"
    val zkRoot = "/kafka-spout" //zkRoot为写读取topic时的偏移量offset数据的节点 zk node
    // The spout appends this id to zkRoot when composing its ZooKeeper path.  You don't need a leading `/`.
    val zkSpoutId = "kafka-storm-starter"//第四个参数为该节点上的次级节点名
    val kafkaConfig = new SpoutConfig(zkHosts, topic, zkRoot, zkSpoutId)
    kafkaConfig.scheme = new SchemeAsMultiScheme(new StringScheme())
    val kafkaSpout = new KafkaSpout(kafkaConfig)
    val numSpoutExecutors = numTopicPartitions
    val builder = new TopologyBuilder
    val spoutId = "kafka-spout"
    builder.setSpout(spoutId, kafkaSpout, numSpoutExecutors)

    System.out.println("init processerBolt")
    val processerBolt = new printInputBolt()
    val processerBoltName = "Print_Input"
    val processerBoltThreadNum = 2

    builder.setBolt(processerBoltName,processerBolt,processerBoltThreadNum)
      .shuffleGrouping(spoutId)

    // Showcases how to customize the topology configuration
    val topologyConfiguration = {
      val c = new Config
      c.setDebug(false)
      c.setNumWorkers(4)
      c.setMaxSpoutPending(1000)
      c.setMessageTimeoutSecs(60)
      c.setNumAckers(0)
      c.setMaxTaskParallelism(50)
      c.put(Config.TOPOLOGY_EXECUTOR_RECEIVE_BUFFER_SIZE, 16384: Integer)
      c.put(Config.TOPOLOGY_EXECUTOR_SEND_BUFFER_SIZE, 16384: Integer)
      //      c.put(Config.TOPOLOGY_RECEIVER_BUFFER_SIZE, 8: Integer)
      c.put(Config.TOPOLOGY_EXECUTOR_RECEIVE_BUFFER_SIZE, 8: Integer)
      c.put(Config.TOPOLOGY_TRANSFER_BUFFER_SIZE, 32: Integer)
      c.put(Config.TOPOLOGY_STATS_SAMPLE_RATE, 0.05: java.lang.Double)
      c
    }

    // Now run the topology in a local, in-memory Storm cluster
    val cluster = new LocalCluster
    cluster.submitTopology(topologyName, topologyConfiguration, builder.createTopology())
    //    Thread.sleep(runtime.toMillis)
    //    val killOpts = new KillOptions()
    //    killOpts.set_wait_secs(1)
    //    cluster.killTopologyWithOpts(topologyName, killOpts)
    //    cluster.shutdown()
  }
}
