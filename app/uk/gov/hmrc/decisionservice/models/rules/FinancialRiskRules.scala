package uk.gov.hmrc.decisionservice.models.rules

import uk.gov.hmrc.decisionservice.model.analytics.PersonalService

case class FinancialRiskRules(rule: PersonalService, result: String)
