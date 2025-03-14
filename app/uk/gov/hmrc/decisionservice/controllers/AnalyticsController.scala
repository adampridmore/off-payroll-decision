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

package uk.gov.hmrc.decisionservice.controllers

import javax.inject.Inject
import play.api.Logger
import play.api.libs.json._
import play.api.mvc.{Action, MessagesControllerComponents}
import uk.gov.hmrc.decisionservice.model.analytics.InterviewFormat._
import uk.gov.hmrc.decisionservice.model.analytics.{AnalyticsResponse, Interview, InterviewSearch}
import uk.gov.hmrc.decisionservice.repository.InterviewRepository
import uk.gov.hmrc.decisionservice.transformer.InterviewTransformer._

import scala.concurrent.Future


class AnalyticsController @Inject() (val repository: InterviewRepository,
                                     mcc: MessagesControllerComponents) extends BaseController(mcc) {

  def logInterview: Action[JsValue] = Action.async(parse.json) { implicit request =>
    Logger.debug(s"log request: ${request.body.toString.replaceAll("\"", "")}")
    request.body.validate[Interview].fold(
      error    => Future.successful(BadRequest(JsError.toJson(error))),
      req      => {
        repository().save(req).map {
          case result if result.ok => NoContent
          case result => InternalServerError(result.writeErrors.mkString)
        }
      }
    )
  }

  def searchInterview: Action[JsValue] = Action.async(parse.json) { implicit request =>
    Logger.debug(s"search request: ${request.body.toString.replaceAll("\"", "")}")
    request.body.validate[InterviewSearch].fold(
      error    => Future.successful(BadRequest(JsError.toJson(error))),
      req      => {
        repository().get(req).map { interviews =>
            val json = Json.toJson(AnalyticsResponse(toResponse(interviews)))
            Ok(json)
        }
      }
    )
  }
}
