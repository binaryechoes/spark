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

package org.apache.spark.deploy.worker

import com.codahale.metrics.{Gauge, MetricRegistry}

import org.apache.spark.metrics.source.Source
import org.apache.spark.util.NamingConventions

private[spark] class WorkerSource(val worker: Worker) extends Source {
  val sourceName = "worker"
  val metricRegistry = new MetricRegistry()

  metricRegistry.register(
    MetricRegistry.name(NamingConventions.makeMetricName("executors", "number")), 
    new Gauge[Int] { override def getValue: Int = worker.executors.size })

  // Gauge for cores used of this worker
  metricRegistry.register(
    MetricRegistry.name(NamingConventions.makeMetricName("coresUsed", "number")), 
    new Gauge[Int] { override def getValue: Int = worker.coresUsed })

  // Gauge for memory used of this worker
  metricRegistry.register(
    MetricRegistry.name(NamingConventions.makeMetricName("memUsed", "MBytes")), 
    new Gauge[Int] { override def getValue: Int = worker.memoryUsed })

  // Gauge for cores free of this worker
  metricRegistry.register(
    MetricRegistry.name(NamingConventions.makeMetricName("coresFree", "number")), 
    new Gauge[Int] { override def getValue: Int = worker.coresFree })

  // Gauge for memory free of this worker
  metricRegistry.register(
    MetricRegistry.name(NamingConventions.makeMetricName("memFree", "MBytes")), 
    new Gauge[Int] { override def getValue: Int = worker.memoryFree })
}

