package controllers.voa

import play.api.mvc.{Action, Controller}

class VoaApp extends Controller {
  def index = Action { implicit request =>
    Ok()
  }
}
