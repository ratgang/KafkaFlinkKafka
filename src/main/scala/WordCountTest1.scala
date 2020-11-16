
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * Created by wenzhiyu on 2020/5/19 14:57
 */
object WordCountTest1 {
  def main(args: Array[String]): Unit = {
    /*val port = try{
      ParameterTool.fromArgs(args).getInt("port")
    }catch {
      case e: Exception=>{
        System.err.println("未指定连接端口,通过 --port <prot> 指定")
        return
      }
    }*/
    //获取运行时环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //连接此socket获取输入数据
    val text = env.socketTextStream("127.0.0.1",10086,'\n')
    env.setParallelism(1)
    import org.apache.flink.api.scala._
    val windowCounts = text.flatMap{ w => w.split("\\s")}
      .map( w => (w,1))
      .keyBy(_._1)
      .timeWindow(Time.seconds(5),Time.seconds(1))
      .sum(1)
    println(windowCounts)
    windowCounts.print().setParallelism(1)
    env.execute("Socket Word WindowCount")

  }
}
