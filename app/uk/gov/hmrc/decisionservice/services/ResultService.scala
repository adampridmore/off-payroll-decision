package uk.gov.hmrc.decisionservice.services

import uk.gov.hmrc.decisionservice.models.enums.{ExitEnum, ResultEnum, WeightedAnswerEnum}

import scala.concurrent.Future

trait ResultService {

  def decide(exit: Option[ExitEnum.Value],
             personalService: Option[WeightedAnswerEnum.Value],
             control: Option[WeightedAnswerEnum.Value],
             financialRisk: Option[WeightedAnswerEnum.Value],
             partAndParcel: Option[WeightedAnswerEnum.Value]
            ): Future[ResultEnum.Value]

}
