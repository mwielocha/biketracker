package io.cyberdolphin.biketracker

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.directives._
import akka.stream.{ ActorMaterializer, Materializer }
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import pureconfig._
import better.files._
import ContentTypeResolver.Default

/**
 * Created by Mikolaj Wielocha on 21/06/17
 */

object StartServer extends App {

  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()

  private val appConfig = loadConfigOrThrow[AppConfig]("app")

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
        complete { appConfig }
      } ~ (path("tracks") & get) {
        complete {
          File(appConfig.gpxDir).list.filter(
            _.extension
              .map(_.toLowerCase)
              .contains(".gpx")
          ).map(_.name)
        }
      } ~ (path("track" / Segment) & get) { name =>
        getFromFile(s"${appConfig.gpxDir}/$name")
      }
    }
  }
}
