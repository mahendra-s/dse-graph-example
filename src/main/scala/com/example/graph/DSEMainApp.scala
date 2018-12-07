package com.example.graph

import com.example.graph.util.GremlinConnection
import gremlin.scala._

object DSEMainApp extends App with GremlinConnection {
  val Person = "Student"
  val Knows = "knows"

  val mahendra = g.addV(Person).property(Name, "Mahendra").property(Age, 29).head()
  val vikas = g.addV(Person).property(Name, "Vikas").property(Age, 27).head()

  val a = StepLabel[Vertex]()
  val b = StepLabel[Vertex]()
  // Find vertices by id and then connect them by 'knows' edge
  g.V(mahendra.id()).as(a).V(vikas.id()).as(b).addE(Knows).from(a).to(b).property(StartTime, 2010).iterate()

  shutdown
}


object Name extends Key[String]("name")

object Age extends Key[Int]("age")

object StartTime extends Key[Int]("startTime")


