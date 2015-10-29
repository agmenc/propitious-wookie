package controllers

import play.api.libs.iteratee.Enumerator
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.Comet
import play.api.Play.current

class Application extends Controller {
  def demo = Action.async { implicit request =>
    futureString(request).map {s => Ok(s)}
  }

  private def futureString(request: Request[_]) = scala.concurrent.Future {
    views.html.demo("Title from Controller")
  }

  def comet = Action {
    val events = Enumerator("one", "two", "three")
    Ok.chunked(events &> Comet(callback = "console.log"))
  }

  def socket = WebSocket.acceptWithActor[String, String] { request => out =>
    MyWebSocketActor.props(out)
  }
}

import akka.actor._

object MyWebSocketActor {
  def props(out: ActorRef) = Props(new MyWebSocketActor(out))
}

class MyWebSocketActor(out: ActorRef) extends Actor {
  def receive = {
    case msg: String =>
      out ! ("I received your message: " + msg)
  }
}