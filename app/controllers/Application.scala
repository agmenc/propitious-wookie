package controllers

import play.api.libs.iteratee.Enumerator
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.Comet

import scala.concurrent.Future

class Application extends Controller {
  def index = Action.async { implicit request =>
    futureString(request).map {s => Ok(s)}
  }

  private def futureString(request: Request[_]): Future[String] = scala.concurrent.Future {
    "Got async request [" + request + "]"
  }

  def comet = Action {
    val events = Enumerator("one", "two", "three")
    Ok.chunked(events &> Comet(callback = "console.log"))
  }
}