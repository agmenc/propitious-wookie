package controllers.dvla

import play.api.mvc.{Action, Controller}

class Dvla extends Controller {
  def index = Action { implicit request =>
    Ok(views.html.dvla.vehicle())
  }
}
