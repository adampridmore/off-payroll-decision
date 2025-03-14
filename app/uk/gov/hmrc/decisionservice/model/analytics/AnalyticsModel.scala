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

package uk.gov.hmrc.decisionservice.model.analytics

import ai.x.play.json.Jsonx
import org.joda.time.DateTime
import play.api.libs.json._

/**
  * Created by work on 20/06/2017.
  */
case class Interview(version: String, compressedInterview: String, route: String, decision:String, count: Option[String], setup: Setup,
                    exit: Exit, personalService: Option[PersonalService], control: Option[Control], financialRisk: Option[FinancialRisk],
                    partAndParcel: Option[PartAndParcel], completed: DateTime)

case class Setup(endUserRole: String, hasContractStarted: String, provideServices: String)

case class Exit(officeHolder: String)

case class PersonalService(workerSentActualSubstitute: Option[String], workerPayActualSubstitute: Option[String],
                     possibleSubstituteRejection: Option[String], possibleSubstituteWorkerPay: Option[String],
                     wouldWorkerPayHelper: Option[String])

case class Control(engagerMovingWorker: Option[String], workerDecidingHowWorkIsDone: Option[String], workHasToBeDone: Option[String],
                     workerDecideWhere: Option[String])

case class FinancialRisk(workerProvidedMaterials: Option[String], workerProvidedEquipment: Option[String],
                         workerUsedVehicle: Option[String], workerHadOtherExpenses: Option[String],
                         expensesAreNotRelevantForRole: Option[String],
                         workerMainIncome: Option[String], paidForSubstandardWork: Option[String])

case class PartAndParcel(workerReceivesBenefits: Option[String], workerAsLineManager: Option[String],
                         contactWithEngagerCustomer: Option[String],  workerRepresentsEngagerBusiness: Option[String])

case class InterviewSearch(version: String, start: DateTime, end: DateTime)

case class AnalyticsSearch(start: DateTime, end: DateTime, decision: String)

case class InterviewSearchResponse(compressedInterview: String, route: String, decision: String, count: String, setupEndUserRole: String,
                     setupHasContractStarted: String, setupProvideServices: String,  exitOfficeHolder: String,
                     personalServiceWorkerSentActualSubstitute: String, personalServiceWorkerPayActualSubstitute: String,
                     personalServicePossibleSubstituteRejection: String, personalServicePossibleSubstituteWorkerPay: String,
                     personalServiceWouldWorkerPayHelper: String, controlEngagerMovingWorker: String,
                     controlWorkerDecidingHowWorkIsDone: String, controlWhenWorkHasToBeDone: String,
                     controlWorkerDecideWhere: String, financialRiskHaveToPayButCannotClaim: String,
                     financialRiskWorkerMainIncome: String, financialRiskPaidForSubstandardWork: String,
                     partParcelWorkerReceivesBenefits: String, partParcelWorkerAsLineManager: String,
                     partParcelContactWithEngagerCustomer: String,  partParcelWorkerRepresentsEngagerBusiness: String)

case class AnalyticsResponse(interviews: List[InterviewSearchResponse])


object InterviewFormat {
  val dateFormat = "yyyy-MM-dd HH:mm:ss"

  implicit val dateWrites = Writes.jodaDateWrites(dateFormat)
  implicit val dateReads = Reads.jodaDateReads(dateFormat)

  implicit val sFormat = Json.format[Setup]
  implicit val eFormat = Json.format[Exit]
  implicit val psFormat = Json.format[PersonalService]
  implicit val cFormat = Json.format[Control]
  implicit val frFormat = Json.format[FinancialRisk]
  implicit val ppFormat = Json.format[PartAndParcel]
  implicit val iFormat = Json.format[Interview]

  implicit val isFormat = Json.format[InterviewSearch]

  implicit val isrFormat = Jsonx.formatCaseClass[InterviewSearchResponse]
  implicit val arFormat = Jsonx.formatCaseClass[AnalyticsResponse]
}
