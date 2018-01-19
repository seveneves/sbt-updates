package com.timushev.sbt.updates

import sbt.Configurations._
import sbt.librarymanagement.ConfigRef

object ConfigurationsCompat {

  private val configurationMapping: Map[ConfigRef, Option[ConfigRef]] = {
    Map(
      CompileInternal -> Some(Compile),
      TestInternal -> Some(Test),
      IntegrationTestInternal -> Some(IntegrationTest),
      RuntimeInternal -> Some(Runtime)
    ) map {
      case (k, v) => k.toConfigRef -> v.map(_.toConfigRef)
    }
  }

  def mergeConfigurations(cs: Seq[ConfigRef]): String =
    cs.map(configurationMapping.orElse(PartialFunction(Some.apply[ConfigRef])))
      .toSet
      .flatten
      .toVector
      .map(_.name)
      .sorted
      .mkString(",")

}
