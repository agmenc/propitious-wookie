package controllers

import play.Logger
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n._
import play.api.mvc._

import javax.inject._

class Dvla @Inject() (val messagesApi: MessagesApi) extends Controller with I18nSupport {
  val searchForm: Form[VehicleSearchForm] = Form {
    mapping(
      "numberPlate" -> nonEmptyText,
      "make" -> nonEmptyText
    )(VehicleSearchForm.apply)(VehicleSearchForm.unapply)
  }

  def index = Action { implicit request =>
    Ok(views.html.dvla.search(searchForm))
  }

  def search = Action { implicit request =>
    searchForm.bindFromRequest.fold(
      errorForm => {
        Logger.error(s"Boom: $errorForm")
        Ok(views.html.dvla.search(errorForm))
      },
      results => {
        Redirect(routes.Dvla.results(results.numberPlate))
      }
    )
  }

  def results(plate: String) = Action { implicit request =>
    Ok(views.html.dvla.results(plate))
  }
}

case class VehicleSearchForm(numberPlate: String, make: String)