import java.lang
import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer, FlinkKafkaProducer, KafkaSerializationSchema}
import org.apache.kafka.clients.producer.ProducerRecord


/**
 * Created by wenzhiyu on 2020/7/14 10:34
 */
object Mongo1ToMongo {
  def main(args: Array[String]): Unit = {
    val topic = "mongoTopic1"
    val prop = new Properties()
    prop.setProperty("bootstrap.servers","192.168.7.2:9092")
    prop.setProperty("zookeeper.connect","192.168.7.2:2181,192.168.7.3:2181")
    prop.setProperty("group.id","flinkTest1")
    //获取运行时环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //env.enableCheckpointing(5000L)
    import org.apache.flink.streaming.api.scala._
    val consumer = new FlinkKafkaConsumer[String](topic,new SimpleStringSchema(),prop)
    val streamDataSource = env.addSource(consumer)
    streamDataSource.print()

    //写入到Kafka
    val sinkProp = new Properties()
    sinkProp.put("bootstrap.servers","")
    sinkProp.put("zookeeper.connect","")
    sinkProp.put("group.id","")
    sinkProp.put("key.deserializer","")
    sinkProp.put("bootstrap.servers","")
    //自定义数据类型Sink
//    val stream:DataStream[ResultDt] = streamDataSource.map(x=>(new ResultDt(x,"","","","",1,1)))
//    val kafkaSink = new FlinkKafkaProducer[ResultDt]("flink_producer",new ResultDtSerialization("flink_producer"),
//      sinkProp,FlinkKafkaProducer.Semantic.EXACTLY_ONCE)
//    stream.addSink(kafkaSink)
    val kafkaSink2 = new FlinkKafkaProducer[String]("192.168.7.2:9092","mongoTopic",
      new SimpleStringSchema())
//    val kafkaSink3 = new FlinkKafkaProducer[String]("",new KafkaSerializationSchema[String] {
//      override def serialize(element: String, timestamp: lang.Long): ProducerRecord[Array[Byte], Array[Byte]] = {
//        new ProducerRecord[Array[Byte],Array[Byte]](element, element.getBytes())
//      }
//    },sinkProp,FlinkKafkaProducer.Semantic.EXACTLY_ONCE)
    streamDataSource.addSink(kafkaSink2)
    env.execute("Streaming Test ==> mongoTopic1 -> mongoTopic")


  }
  case class ResultDt(id: String, date_h: String, star: String, end: String, watermark: String, pv: Long, uv: Long)

  class ResultDtSerialization(topic: String) extends KafkaSerializationSchema[ResultDt] {
    override def serialize(t: ResultDt, aLong: lang.Long): ProducerRecord[Array[Byte], Array[Byte]] = {
      new ProducerRecord[Array[Byte], Array[Byte]](topic, t.toString.getBytes())
    }
  }
}
