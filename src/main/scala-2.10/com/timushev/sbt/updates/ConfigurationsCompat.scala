package com.timushev.sbt.updates

import sbt.Configurations._

object ConfigurationsCompat {

  private val configurationMapping: Map[String, Option[String]] = {
    Map(
      CompileInternal -> Some(Compile),
      TestInternal -> Some(Test),
      IntegrationTestInternal -> Some(IntegrationTest),
      RuntimeInternal -> Some(Runtime)
    ) map {
      case (k, v) => k.name -> v.map(_.name)
    }
  }

  def mergeConfigurations(cs: Seq[String]): String =
    cs.map(configurationMapping.orElse(PartialFunction(Some.apply[String])))
      .toSet
      .flatten
      .toVector
      .sorted
      .mkString(",")

}
