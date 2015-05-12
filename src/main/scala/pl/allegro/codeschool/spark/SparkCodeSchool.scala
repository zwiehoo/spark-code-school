package pl.allegro.codeschool.spark

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object SparkCodeSchool extends App {
  val sc = new SparkContext("local[8]", "my-spark-app")

  val input = sc.textFile("input/all-shakespeare.txt")

  val words = input
    .map(_.toLowerCase.trim)
    .flatMap(_.split("""[ ]+"""))
    .cache()

  val result = words
    .filter(!_.isEmpty)
    .keyBy(word => 1).map (_.swap)
    .reduceByKey((a,b) => a + b)
    .sortBy(_._2, ascending = false)

  result take(100) foreach println
}
