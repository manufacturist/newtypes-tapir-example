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

    BlazeServerBuilder[IO]
      .bindHttp(9001, "localhost")
      .withHttpApp(httpApp)
      .withBanner(List.empty)
      .resource
      .use(_ => IO.never)
  }
}
