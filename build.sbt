name := "poker-akka"

version := "0.1"

scalaVersion := "2.12.8"

mainClass in (Compile, run) := Some("main.WebServer")

libraryDependencies ++= Seq(
  "org.json4s" %% "json4s-native" % "3.6.4",
  "de.heikoseeberger" %% "akka-http-json4s" % "1.25.2",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.5.20",
  "com.typesafe.akka" %% "akka-actor" % "2.5.20",
  "com.typesafe.akka" %% "akka-http" % "10.1.7",
  "com.typesafe.akka" %% "akka-stream" % "2.5.20",
  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)