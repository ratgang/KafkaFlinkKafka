import java.util.Properties

import ReadFromKafka.MysqlSinkTest
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.configuration.Configuration
import org.apache.hadoop.conf.Configuration
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{BufferedMutator, BufferedMutatorParams, Connection, ConnectionFactory, Put, Table}
import org.apache.hadoop.hbase.util.Bytes

/**
 * Created by wenzhiyu on 2020/5/26 9:29
 */
object WriteHbase {
  def main(args: Array[String]): Unit = {
    val topic = "streaming"
    val prop = new Properties()
    prop.setProperty("bootstrap.servers","192.168.10.10:9092")
    prop.setProperty("zookeeper.connect","192.168.10.17:2181,192.168.10.17:2181")
    prop.setProperty("group.id","flinkTest1")
    //获取运行时环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //env.enableCheckpointing(5000L)
    import org.apache.flink.streaming.api.scala._
    val consumer = new FlinkKafkaConsumer[String](topic,new SimpleStringSchema(),prop)
    val streamDataSource = env.addSource(consumer)
    streamDataSource
      .flatMap(x=>x.split("\\s"))
      .map(x=>(x,1))
      .keyBy(_._1)
      .timeWindow(Time.seconds(5))
      .sum(1)
      .map(x=>x._1.toString + "," +  x._2.toString)
      .addSink(new HbaseSinkTest)

    env.execute("Streaming Test ==> From Kafka")

  }

  class HbaseSinkTest extends RichSinkFunction[String]{
    var configuration: org.apache.hadoop.conf.Configuration = null
    var connection: Connection  = null
    var mutator: BufferedMutator = null
    var table: Table = null
    var count = 0

    override def open(parameters: org.apache.flink.configuration.Configuration): Unit = {
      configuration = HBaseConfiguration.create()
      configuration.set("hbase.master","192.168.10.20:60020")
      configuration.set("hbase.zookeeper.quorum","192.168.10.17")
      configuration.set("hbase.zookeeper.property.clientPort","2181")
      try{
        connection = ConnectionFactory.createConnection(configuration)
      } catch {
        case e: Exception => {
          e.printStackTrace()
        }
      }
      table = connection.getTable(TableName.valueOf("flink_test"))
    }

    override def invoke(value: String): Unit = {
      val cf1 = "cf1"
      val rowk = value.split(",")(0)
      val name = value.split(",")(1)
      val age = value.split(",")(2)
      val put = new Put(Bytes.toBytes(rowk))
      put.addColumn(Bytes.toBytes(cf1),Bytes.toBytes("name"),Bytes.toBytes(name))
      put.addColumn(Bytes.toBytes(cf1),Bytes.toBytes("age"),Bytes.toBytes(age))
      mutator.mutate(put)
      if (count > 2000){
        mutator.flush()
        count = 0
      }
      count += 1
    }

    override def close(): Unit = {
      if (mutator != null){
        mutator.flush()
        mutator.close()
      }
      if (table != null){
        table.close()
      }
      if (connection != null){
        connection.close()
      }
    }
  }

}
