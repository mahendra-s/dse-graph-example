name := "dse-graph"
organization := "com.decooda"
scalaVersion := "2.11.12"

// Note: This is needed to work around an issue with the DataStax driver and the Scala Compiler.
// See: https://datastax-oss.atlassian.net/browse/JAVA-1252 for more details.
scalacOptions += "-Ybreak-cycles"

libraryDependencies ++= Seq(
  "com.michaelpollmeier" %% "gremlin-scala" % "3.3.0.5",
  "com.datastax.dse" % "dse-java-driver-graph" % "1.4.2",
  "com.typesafe" % "config" % "1.3.1",
  "ch.qos.logback" % "logback-classic" % "1.1.9",
  "org.scalatest" %% "scalatest" % "3.0.3" % Test
)

fork in Test := true
resolvers += Resolver.mavenLocal
