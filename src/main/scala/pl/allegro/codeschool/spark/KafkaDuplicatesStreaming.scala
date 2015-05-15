package pl.allegro.codeschool.spark

import org.apache.spark.SparkContext
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._

object Settings {
  val duration = Seconds(10)
  val windowSize = Seconds(60)
}

object KafkaDuplicatesStreaming extends App {
  val sc = new SparkContext("local[8]", "my-spark-streaming-app")
  sc.setCheckpointDir("output/checkpointy")

  val ssc: StreamingContext = new StreamingContext(sc, Settings.duration)

  val stream = OfferPublicationStarted.stream(ssc)

  val message = stream.map(OfferPublicationStarted.message)

  val result = message.map(OfferPublicationStarted.parse)
    .filter(_.getOrElse("countryIsoCode", "") == "PL")
    .map(x => ( ??? )) // Get required fields to perform map/reduce operation
    .map(x => (x, 1))
    .reduceByKeyAndWindow(
      reduceFunc = _ + _,
      invReduceFunc = _ - _,
      windowDuration = Settings.windowSize
    )
    .filter {
      case (triple, count) => count > 1
    }
    .transform(_.sortBy(_._2, ascending = false))
    .cache

  result.print()

  ssc.start()
  ssc.awaitTermination()
}
