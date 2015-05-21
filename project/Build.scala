import sbt._
import sbt.Keys._

object BuildSettings {

  val Name = "spark-kafka-ml"
  val Version = "2.1"
  val ScalaVersion = "2.10.5"

  lazy val buildSettings = Defaults.coreDefaultSettings ++ Seq (
    name          := Name,
    version       := Version,
    scalaVersion  := ScalaVersion,
    organization  := "pl.allegro",
    description   := "Spark Streaming Kafka Machine Learning",
    scalacOptions := Seq("-deprecation", "-unchecked", "-encoding", "utf8", "-Xlint")
  )
}


object Resolvers {
  val typesafe = "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
  val sonatype = "Sonatype Release" at "https://oss.sonatype.org/content/repositories/releases"
  val pacewicz = "Patryk Pacewicz Rulez" at "https://dl.bintray.com/patrykpacewicz/maven"
  val mvnrepository = "MVN Repo" at "http://mvnrepository.com/artifact"

  val allResolvers = Seq(typesafe, sonatype, mvnrepository, pacewicz)

}

object Dependency {
  object Version {
    val Spark        = "1.2.1"
    val ScalaTest    = "2.1.4"
    val ScalaCheck   = "1.11.3"
  }

  val sparkCore      = "org.apache.spark"  %% "spark-core"      % Version.Spark  withSources()
  val sparkStreaming = "org.apache.spark"  %% "spark-streaming" % Version.Spark  withSources()
  val sparkMLlib     = "org.apache.spark"  %% "spark-mllib"     % Version.Spark  withSources()

  val kafkaStream    = "org.apache.spark"  %% "spark-streaming-kafka" % Version.Spark withSources()
  val scalaTest      = "org.scalatest"     %% "scalatest"       % Version.ScalaTest  % "test"
  val scalaCheck     = "org.scalacheck"    %% "scalacheck"      % Version.ScalaCheck % "test"

  val wikimediaStream = "pl.patrykpacewicz" % "wikimedia-stream-library" % "0.1.0"

  val jacks = "com.lambdaworks" % "jacks_2.10" % "2.5.2"

}

object Dependencies {
  import Dependency._

  val sparkWorkshop =
    Seq(sparkCore, sparkStreaming, //sparkMLlib,
      scalaTest, scalaCheck, kafkaStream, jacks, wikimediaStream
    )
}

object KafkaStreamingBuild extends Build {
  import Resolvers._
  import BuildSettings._

  lazy val kafkaStreamingProject = Project(
    id = "Kafka-Streaming",
    base = file("."),
    settings = buildSettings ++ Seq(
      maxErrors          := 5,
      triggeredMessage   := Watched.clearWhenTriggered,
      resolvers := allResolvers,
      libraryDependencies ++= Dependencies.sparkWorkshop,
      unmanagedResourceDirectories in Compile += baseDirectory.value / "conf",
      mainClass := Some("run"),
      fork := true,
      parallelExecution in Test := false))
}



