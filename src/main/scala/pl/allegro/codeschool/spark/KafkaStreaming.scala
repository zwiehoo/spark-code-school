package pl.allegro.codeschool.spark

import com.lambdaworks.jacks.JacksMapper
import org.apache.spark.SparkContext
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.kafka.KafkaUtils

object Settings {
    val duration = Seconds(10)
    val windowSize = Seconds(60)
}

object OfferPublicationStarted {
    def stream(ssc: StreamingContext): ReceiverInputDStream[(String, String)] = {
        KafkaUtils.createStream(ssc,
            "zookeeper.qxlint:2181/external/kasia/kafka",
            "pl.spark.training.beta",
            Map("kasia.pl.allegro.offercore.api.offerPublicationStarted" -> 4)
        )
    }

    def message(tuple: (String, String)): String = tuple match {
        case (_, message) => message
    }

    def parse(message: String): Map[String, Any] = {
        JacksMapper.readValue[Map[String, Any]](message)
    }
}

object KafkaStreaming extends App {
    val sc = new SparkContext("local[8]", "kafka-duplicates")
    sc.setCheckpointDir("output/checkpoints")

    val ssc: StreamingContext = new StreamingContext(sc, Settings.duration)

    OfferPublicationStarted.stream(ssc)
        .map(OfferPublicationStarted.message)
        .map(OfferPublicationStarted.parse)

    // TODO: - print stream to analyze offer json structure
    // TODO: - filter allegro.pl offers only
    // TODO: - count offers by title
    // TODO: - apply sliding window mechanism to analyse longer period of time

    ssc.start()
    ssc.awaitTermination()
}
