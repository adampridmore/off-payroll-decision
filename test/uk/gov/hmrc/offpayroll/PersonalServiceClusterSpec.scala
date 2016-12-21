/*
 * Copyright 2016 HM Revenue & Customs
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

package uk.gov.hmrc.offpayroll

import java.util.Properties

import org.scalatest.{FlatSpec, Matchers}
import  uk.gov.hmrc.offpayroll.models.PersonalServiceCluster

/**
  * Created by peter on 11/12/2016.
  */
class PersonalServiceClusterSpec extends FlatSpec with Matchers {

  private val personalServiceCluster = PersonalServiceCluster
  private val partialAnswers = List(("personalService.workerSentActualSubstitiute", "false"))

  private val propsFilteredByCluster = PropertyFileLoader.getMessagesForACluster("personalService")

  private val allAnswers = PropertyFileLoader.convertMapToAListOfAnswers(propsFilteredByCluster)

  private val skipOneAnswer = PropertyFileLoader.convertMapToAListOfAnswers(propsFilteredByCluster - "personalService.engagerArrangeWorker")

  "The Personal Service Cluster " should
  " say ask for decision if a hard exit is identified " in {
    personalServiceCluster.shouldAskForDecision(partialAnswers) should be (false)

  }
  
  it should "say complete when all the questions are present" in {
    personalServiceCluster.shouldAskForDecision(allAnswers) should be (true)
  }

  it should "say continue if a question hgas been skipped" in {
    personalServiceCluster.shouldAskForDecision(skipOneAnswer) should be (false)
  }

}
