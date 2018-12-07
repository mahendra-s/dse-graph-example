package com.example.graph.util

import com.datastax.driver.dse.DseCluster
import com.datastax.driver.dse.graph.{GraphOptions, SimpleGraphStatement}
import com.datastax.dse.graph.internal.DseRemoteConnection
import com.google.common.collect.ImmutableMap
import gremlin.scala._
import org.apache.tinkerpop.gremlin.structure.util.empty.EmptyGraph

trait GremlinConnection extends Logger with GraphConfig {
  val dseCluster = DseCluster.builder()
    .addContactPoint(host)
    .build()

  val graphOptions = new GraphOptions()
    .setGraphName(graphName)

  val session = dseCluster.connect()
  // Create graph
    logger.info("Creating graph {}", graphName)
  session.executeGraph("system.graph(name).ifNotExists().create()", ImmutableMap.of("name", graphName))


  // Clear the schema to drop any existing data and schema
//    logger.info("Dropping any existing schema and data if present")
//  session.executeGraph(new SimpleGraphStatement("schema.clear()").setGraphName(graphName))

  // Note: typically you would not want to use development mode and allow scans, but it is good for convenience
  // and experimentation during development.

  // Enable development mode and allow scans
    logger.info("Enabling development mode")
  session.executeGraph(new SimpleGraphStatement("schema.config().option('graph.schema_mode').set('development')")
    .setGraphName(graphName))
    logger.info("Allowing scans")
  session.executeGraph(new SimpleGraphStatement("schema.config().option('graph.allow_scan').set('true')")
    .setGraphName(graphName))

  val connection = DseRemoteConnection.builder(session)
    .withGraphOptions(graphOptions)
    .build()
  val g = EmptyGraph.instance().asScala
    .configure(_.withRemote(connection))

  //  val session: DseSession = dseCluster.connect()
  //  private val graphOptions: GraphOptions = new GraphOptions().setGraphName(graphName)


  def shutdown: Unit = {
    logger.info("Disconnecting")
    dseCluster.close();
    logger.info("Done")
  }

}
