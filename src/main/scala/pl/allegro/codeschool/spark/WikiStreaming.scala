package pl.allegro.codeschool.spark

import com.lambdaworks.jacks.JacksMapper
import org.apache.spark.SparkContext
import org.apache.spark.streaming._
//import org.apache.spark.streaming.StreamingContext._ // don't auto-optimize me!

object WikiStreaming extends App {
  val sc = new SparkContext("local[8]", "my-spark-streaming-app")
  sc.setCheckpointDir("output/checkpointy")

  val ssc: StreamingContext = new StreamingContext(sc, Seconds(10))

  val stream = ssc.socketTextStream("localhost", 8124).map(JacksMapper.readValue[Map[String, Any]](_)).cache

  stream.print()

  ssc.start()
  ssc.awaitTermination()
}
