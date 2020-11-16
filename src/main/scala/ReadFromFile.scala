import org.apache.flink.api.common.io.statistics.BaseStatistics
import org.apache.flink.api.common.io.{FilePathFilter, RichInputFormat}
import org.apache.flink.api.common.typeinfo.BasicTypeInfo
import org.apache.flink.api.java.io.TextInputFormat
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.configuration.Configuration
import org.apache.flink.core.fs.Path
import org.apache.flink.core.io.InputSplitAssigner
import org.apache.flink.streaming.api.functions.source.FileProcessingMode
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time



/**
 * Created by wenzhiyu on 2020/5/20 15:01
 */
object ReadFromFile {
  def main(args: Array[String]): Unit = {
    var filePath = try {
      ParameterTool.fromArgs(args).get("path")
    }catch {
      case exception: Exception=>{
        System.err.println("未指定文件路径，通过--path <path> 来指定")
        return
      }
    }
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val textformat = new TextInputFormat(new Path(filePath))
    val fileFilter = new FilePathFilter {
      override def filterPath(path: Path): Boolean = {
        if (path.getPath.contains(".swp")){
          return true
        }else{
          return false
        }
      }
    }
    textformat.setFilesFilter(fileFilter)
    textformat.setCharsetName("UTF-8")
    import org.apache.flink.streaming.api.scala._
    val text = env.readFile(textformat,filePath,FileProcessingMode.PROCESS_CONTINUOUSLY,5L)
    //val text = env.readTextFile(filePath)
    text.print()
    text.flatMap{x=>x.split("\\s")}.map(x=>(x,1)).keyBy(_._1).timeWindow(Time.seconds(5)).sum(1).print()
    env.execute("hello hello")

    val a = "aa";


  }
  class CustomInputFormat extends RichInputFormat{
    override def configure(configuration: Configuration): Unit = ???

    override def getStatistics(baseStatistics: BaseStatistics): BaseStatistics = ???

    override def createInputSplits(i: Int): Array[Nothing] = ???

    override def getInputSplitAssigner(ts: Array[Nothing]): InputSplitAssigner = ???

    override def open(t: Nothing): Unit = ???

    override def reachedEnd(): Boolean = ???

    override def nextRecord(ot: Nothing): Nothing = ???

    override def close(): Unit = ???
  }
}
