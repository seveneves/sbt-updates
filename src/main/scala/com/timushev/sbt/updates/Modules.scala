package com.timushev.sbt.updates

import sbt.{ModuleID, UpdateReport}

import scala.collection.breakOut

object Modules {

  def transitiveDependencies(report: UpdateReport): Vector[ModuleID] = {
    val moduleConfigurations = for {
      configurationReport <- report.configurations
      module <- configurationReport.modules
    } yield module.module -> configurationReport.configuration
    moduleConfigurations
      .groupBy(_._1)
      .map {
        case (m, cs) => m.withConfigurations(Some(ConfigurationsCompat.mergeConfigurations(cs.map(_._2))))
      }(breakOut)
  }

}
