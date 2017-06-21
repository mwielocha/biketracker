package io.cyberdolphin.biketracker

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives
import akka.stream.{ ActorMaterializer, Materializer }
import scala.concurrent.Future
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport


/**
 * Created by Mikolaj Wielocha on 21/06/17
 */

object StartServer extends App {

  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()

  Http().bindAndHandle(route, "127.0.0.1", 9021)

  scala.sys.addShutdownHook {
    system.terminate()
  }

  private def route(implicit mat: Materializer) = {
    import Directives._
    import FailFastCirceSupport._
    import io.circe.generic.auto._

    pathPrefix("api") {
      (path("config") & get) {
        complete { Future.successful("Hi") }
      }
    }
  }
}
