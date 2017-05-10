package com.orendainx.hortonworks.trucking.storm.bolts

import java.util

import com.orendainx.hortonworks.trucking.commons.models.{EnrichedTruckAndTrafficData, WindowedDriverStats}
import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.OutputFieldsDeclarer
import org.apache.storm.topology.base.BaseRichBolt
import org.apache.storm.tuple.{Fields, Tuple, Values}

/**
  * @author Edgar Orendain <edgar@orendainx.com>
  */
class ObjectToCSVStringBolt extends BaseRichBolt {

  private lazy val log = Logger(this.getClass)
  private var outputCollector: OutputCollector = _

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector): Unit = {
    outputCollector = collector
  }

  override def execute(tuple: Tuple): Unit = {
    val str = tuple.getStringByField("dataType") match {
      case "EnrichedTruckAndTrafficData" => tuple.getValueByField("data").asInstanceOf[EnrichedTruckAndTrafficData].toCSV
      case "WindowedDriverStats" => tuple.getValueByField("data").asInstanceOf[WindowedDriverStats].toCSV
    }

    outputCollector.emit(new Values(str))
    outputCollector.ack(tuple)
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = declarer.declare(new Fields("data"))
}
