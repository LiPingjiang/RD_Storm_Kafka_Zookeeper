package bolts

import java.util

import com.typesafe.scalalogging.Logger
import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.OutputFieldsDeclarer
import org.apache.storm.topology.base.BaseRichBolt
import org.apache.storm.tuple.{Fields, Tuple, Values}

/**
  * Created by
  * User: pingjiangli
  * Date: 27/11/2017
  * Time: 12:16 PM
  */
class printInputBolt extends BaseRichBolt {
  private var _collector: OutputCollector = _
  val logger = Logger(getClass.getName)

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector) = {
    _collector = collector
  }

  override def execute(input: Tuple) = {
    _collector.ack(input)
    //Print Input
    logger.info("Print Input Bolt getString: " + input.getString(0))
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer) = {
    declarer.declare(new Fields("data"))
  }
}
