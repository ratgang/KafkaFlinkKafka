import java.sql.{Connection, DriverManager, PreparedStatement}
import java.util.Properties

import org.apache.flink.api.common.io.RichOutputFormat
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.util.Collector
import org.apache.hadoop.hbase.HBaseConfiguration

/**
 * Created by wenzhiyu on 2020/5/21 10:43
 */
object ReadFromKafka {
  def main(args: Array[String]): Unit = {
    //读取外部参数

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
    streamDataSource.print()
    streamDataSource
      .flatMap(x=>x.split("\\s"))
      .map(x=>(x,1))
      .keyBy(_._1)
      .timeWindow(Time.seconds(5))
      .sum(1)
        .addSink(new MysqlSinkTest)

    env.execute("Streaming Test ==> From Kafka")


  }

  class MysqlSinkTest extends RichSinkFunction[(String,Int)]{
    var conn : Connection = _
    var pres : PreparedStatement = _
    var userName = "root"
    var passWord = "Cmcc@1234!"
    var dburl = "jdbc:mysql://192.168.10.4:3306/mas_oper?useUnicode=true&characterEncoding=utf-8&useSSL=false"
    var sql = "insert into flink_test(word, count) values(?,?)"
    override def open(parameters: Configuration): Unit = {
      Class.forName("com.mysql.jdbc.Driver")
      conn = DriverManager.getConnection(dburl,userName,passWord)
      pres = conn.prepareStatement(sql)
      super.close()
    }

    override def invoke(value: (String, Int)): Unit = {
      pres.setString(1,value._1)
      pres.setInt(2,value._2)
      pres.executeUpdate()
      println("values: " + value._1 + "--" + value._2 )
    }

    override def close(): Unit = {
      pres.close()
      conn.close()
    }
  }
}
