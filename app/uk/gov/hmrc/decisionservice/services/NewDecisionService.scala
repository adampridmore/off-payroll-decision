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

package uk.gov.hmrc.decisionservice.services

import com.google.inject.Inject
import uk.gov.hmrc.decisionservice.models.{DecisionRequest, DecisionResponse}
import uk.gov.hmrc.decisionservice.models.enums.SetupEnum

import scala.concurrent.{ExecutionContext, Future}
import uk.gov.hmrc.decisionservice.models.{DecisionResponse, Score}
import uk.gov.hmrc.decisionservice.ruleEngines._

class NewDecisionService @Inject()(controlRuleEngine: ControlRuleEngine,
                                   earlyExitRuleEngine: ExitRuleEngine,
                                   financialRiskRuleEngine: FinancialRiskRuleEngine,
                                   personalServiceRuleEngine: PersonalServiceRuleEngine,
                                   partAndParcelRuleEngine: PartAndParcelRuleEngine,
                                   resultRuleEngine: ResultRuleEngine) {

  def calculateResult(request: DecisionRequest)(implicit ec: ExecutionContext): Future[DecisionResponse] = {

    val interview = request.interview
    val setup = if(interview.setup.isDefined) Some(SetupEnum.CONTINUE) else None

    for {
      exit <- earlyExitRuleEngine.decide(interview.exit)
      personalService <- personalServiceRuleEngine.decide(interview.personalService)
      control <- controlRuleEngine.decide(interview.control)
      financialRisk <- financialRiskRuleEngine.decide(interview.financialRisk)
      partAndParcel <- partAndParcelRuleEngine.decide(interview.partAndParcel)
      score = Score(setup, exit, personalService, control, financialRisk, partAndParcel)
      result <- resultRuleEngine.decide(score)

    } yield DecisionResponse(request.version, request.correlationID, score, result)
  }
}
