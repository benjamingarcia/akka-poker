package main

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s._
import spoker.Table

import scala.io.StdIn

object WebServer extends Directives with Json4sSupport {
  implicit val serialization = native.Serialization // or native.Serialization
  implicit val formats = DefaultFormats

  // needed to run the route
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  // needed for the future map/flatmap in the end and future in fetchItem and saveOrder
  implicit val executionContext = system.dispatcher

  var nextId = 0
  val gameList: Map[Int, Table] = Map()

  type PlayerID = Long
  case class CreateGameOrder(players: List[PlayerID])

  def main(args: Array[String]) {
    val route: Route =
      get {
        pathPrefix("item" / LongNumber) { id =>
          complete(StatusCodes.OK)
        }
      } ~
        post {
          path("game") {
            entity(as[CreateGameOrder]) { _ =>
              val gameId = nextId
              nextId = nextId + 1
              complete(gameId.toString)
            }
          }
        }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8081)
    println(s"Server online at http://localhost:8081/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ ⇒ system.terminate()) // and shutdown when done

  }
}