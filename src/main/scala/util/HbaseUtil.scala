package util

import org.apache
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.Connection
import org.apache.hadoop

/**
 * Created by wenzhiyu on 2020/5/25 17:46
 */

class HbaseUtil(getConnect:()=>Connection) extends Serializable{
  lazy val connect = getConnect()
}
object HbaseUtil {
  private val conf: Configuration = HBaseConfiguration.create()

}
