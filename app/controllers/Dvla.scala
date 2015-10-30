package controllers

import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n._
import play.api.mvc._

import javax.inject._

class Dvla @Inject() (val messagesApi: MessagesApi) extends Controller with I18nSupport {
  val searchForm: Form[VehicleSearchForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "make" -> nonEmptyText
    )(VehicleSearchForm.apply)(VehicleSearchForm.unapply)
  }

  def index = Action { implicit request =>
    // Ok(views.html.index(searchForm))
    Ok(views.html.dvla.vehicle(searchForm))
  }

  def searchVehicle = Action { implicit request =>
    searchForm.bindFromRequest.fold(
      errorForm => {
        Ok(views.html.dvla.vehicle(errorForm))
      },
      results => {
        println(s"results: ${results}")
        Redirect(routes.Dvla.index)
      }
    )
  }
}

case class VehicleSearchForm(name: String, make: String)