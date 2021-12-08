package busymachines

import busymachines.algebra.impl._
import busymachines.api.EndpointsInterpreter
import busymachines.api.routes._
import cats.effect._
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT
import org.http4s.server.Router
import org.log4s.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

import scala.concurrent.ExecutionContext

object Main extends IOApp {

  private val logger = Slf4jLogger.getLogger[IO]

  override def run(args: List[String]): IO[ExitCode] = {

    val actorRoutes = new ActorRoutes(ActorAlgebraNoOp)
    val movieRoutes = new MovieRoutes(MovieAlgebraNoOp)

    val allRoutes = actorRoutes.serverEndpoints ++ movieRoutes.serverEndpoints
    val httpApp = Router("/" -> EndpointsInterpreter(allRoutes)).orNotFound

    val port = 9001
    val host = "localhost"

    BlazeServerBuilder[IO]
      .bindHttp(port, host)
      .withHttpApp(httpApp)
      .withBanner(List.empty)
      .resource
      .use { _ =>
        logger.info(s"Swagger at http://$host:$port/api/public/swagger") *>
          logger.info(s"Redoc at http://$host:$port/api/public/redoc") *>
          IO.never
      }
  }
}
