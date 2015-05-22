package pl.allegro.codeschool.spark

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._ // do not auto-optimize me please!

object TextAnalysis extends App {
    val sc = new SparkContext("local[8]", "my-spark-app")

    val input = sc.textFile("input/all-shakespeare.txt")

    // TODO: compute simple TF (Term Frequency), first part of TF-IDF algorithm
    // TODO: (http://en.wikipedia.org/wiki/Tf%E2%80%93idf)
    // TODO: - convert entire text to lower case
    // TODO: - split lines into separate words
    // TODO: - filter out empty words
    // TODO: - compute words count
    // TODO: - cache your results to speed up further computations
    // TODO: - compute max, min count values
    // TODO: - normalize word counts
    // TODO: - filter out stop words (normalized count > 0,8)
    // TODO: - filter out rare words (normalized count < 0,2)
    // TODO: - sort by frequency descending
    // TODO: - print top 100 words
}
