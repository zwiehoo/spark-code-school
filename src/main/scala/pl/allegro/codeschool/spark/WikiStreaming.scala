package pl.allegro.codeschool.spark

import com.lambdaworks.jacks.JacksMapper
import org.apache.spark.SparkContext
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._ // don't auto-optimize me!

/**
 * To run this example you need wikichanges node app running - see README.md
 */
object WikiStreaming extends App {
    val sc = new SparkContext("local[8]", "wiki-streaming-app")
    sc.setCheckpointDir("output/checkpoints")

    val ssc: StreamingContext = new StreamingContext(sc, Seconds(10))

    val stream = ssc
        .socketTextStream("localhost", 8124)
        .map(JacksMapper.readValue[Map[String, Any]])
        .cache()

    // TODO: display URLs of 10 most frequently edited pages
    // TODO: filter by count greater than 10
    // TODO: (extra) send hot topics to kafka stream

    stream.print()

    ssc.start()
    ssc.awaitTermination()
}
