/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.scheduler

import com.codahale.metrics.{Gauge,MetricRegistry}

import org.apache.spark.metrics.source.Source
import org.apache.spark.SparkContext
import org.apache.spark.util.NamingConventions

private[spark] class DAGSchedulerSource(val dagScheduler: DAGScheduler, sc: SparkContext)
    extends Source {
  val metricRegistry = new MetricRegistry()
  val sourceName = "%s.DAGScheduler".format(sc.appName)

  metricRegistry.register(
    MetricRegistry.name("stage", NamingConventions.makeMetricName("failedStages", "number")), 
    new Gauge[Int] { override def getValue: Int = dagScheduler.failed.size })

  metricRegistry.register(
    MetricRegistry.name("stage", NamingConventions.makeMetricName("runningStages", "number")), 
    new Gauge[Int] { override def getValue: Int = dagScheduler.running.size })

  metricRegistry.register(
    MetricRegistry.name("stage", NamingConventions.makeMetricName("waitingStages", "number")), 
    new Gauge[Int] { override def getValue: Int = dagScheduler.waiting.size })

  metricRegistry.register(
    MetricRegistry.name("job", NamingConventions.makeMetricName("allJobs", "number")), 
    new Gauge[Int] { override def getValue: Int = dagScheduler.nextJobId.get() })

  metricRegistry.register(
    MetricRegistry.name("job", NamingConventions.makeMetricName("activeJobs", "number")), 
    new Gauge[Int] { override def getValue: Int = dagScheduler.activeJobs.size })
}

