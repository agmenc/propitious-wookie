package controllers

import javax.inject._

import data.VehicleRepository
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n._
import play.api.mvc._

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
        Ok(views.html.dvla.search(errorForm))
      },
      completedForm => {
        VehicleRepository.search(completedForm).fold(
          Ok(views.html.dvla.vehicleNotFound())
        ) (
          vehicle => Ok(views.html.dvla.results(vehicle))
        )
      }
    )
  }
}

case class VehicleSearchForm(numberPlate: String, make: String)