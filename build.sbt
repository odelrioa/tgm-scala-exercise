name := """tgm-scala-exercise"""
organization := "com.topgolfmedia"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.1"

libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies ++= Seq(
    guice,
    "com.h2database" % "h2" % "1.4.200",
    "com.typesafe.play" %% "play-slick" % "5.0.0",
    "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0"
    )

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.topgolfmedia.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.topgolfmedia.binders._"
