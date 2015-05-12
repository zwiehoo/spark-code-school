package pl.allegro.codeschool.spark

import org.apache.spark.SparkContext
import org.apache.spark.streaming._

object WikiStreaming extends App {
  val sc = new SparkContext("local[8]", "my-spark-streaming-app")
  sc.setCheckpointDir("output/checkpointy")

  val ssc: StreamingContext = new StreamingContext(sc, Seconds(10))

  val stream = ssc.socketTextStream("localhost", 8124);

  stream.print()

  ssc.start()
  ssc.awaitTermination()
}
