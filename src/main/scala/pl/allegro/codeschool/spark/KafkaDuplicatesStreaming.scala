package pl.allegro.codeschool.spark

import org.apache.spark.SparkContext
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import OfferPublicationStarted.{Price, Name, SellerId, CountryCode}

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
    .filter {
    case (_, _, CountryCode(countryCode), _, _) => countryCode == "PL"
  }
    .map {
    case (SellerId(sellerId), Name(name), _, _, Price(price)) => (sellerId, name, price)
  }
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
