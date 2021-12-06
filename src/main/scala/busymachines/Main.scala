package busymachines

import busymachines.algebra.impl._
import busymachines.api.EndpointsInterpreter
import busymachines.api.routes._
import cats.effect._
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT
import org.http4s.server.Router

import scala.concurrent.ExecutionContext

object Main extends IOApp {

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
        IO.println(s"Check http://$host:$port/api/public/docs/index.html") *> IO.never
      }
  }
}
