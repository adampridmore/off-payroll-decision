/*
 * Copyright 2019 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.decisionservice.config

import javax.inject.Inject
import play.api.Mode
import play.api.{Configuration, Environment}
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig

class AppConfig @Inject()(env: Environment, val runModeConfiguration: Configuration, servicesConfig: ServicesConfig) {

  val mode: Mode = env.mode
  val gatherAnalytics: Boolean = runModeConfiguration.getOptional[Boolean]("analytics.gatherAnalytics").getOrElse(false)
  val reportingPeriod = runModeConfiguration.getOptional[Int]("analytics.reportingPeriod").getOrElse(0)

  val newDecisionVersion = servicesConfig.getString("newDecisionVersion")

}
