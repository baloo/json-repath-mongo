import sbt._
import sbt.Keys._

object Build extends Build {
  lazy val root = Project(
    "json-repath-mongo",
    file("."),
    settings = Defaults.defaultSettings ++ Seq(
      organization := "net.superbaloo",
      version := "0.0.1",
      scalaVersion := "2.10.1",
      libraryDependencies ++= Seq(
        "play" %% "play" % "2.1.1",
        "org.specs2" % "specs2" % "1.14" % "test" cross CrossVersion.binary
      ),
      resolvers ++= Seq("Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/")
    )
  )
}

