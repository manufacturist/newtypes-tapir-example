package busymachines.api.routes

import busymachines.algebra.MovieAlgebra
import busymachines.api.endpoints._
import cats.effect.IO
import sttp.tapir.server.ServerEndpoint

class MovieRoutes(movieAlgebra: MovieAlgebra) extends Routes {

  override def serverEndpoints: List[ServerEndpoint[Any, IO]] =
    List(
      MovieEndpoints.createMovie.serverLogic[IO] { createMovieCommand =>
        movieAlgebra.createMovie(createMovieCommand).map(Right(_))
      },
      MovieEndpoints.addActorToMovie.serverLogic[IO] {
        case (movieId, actorId) =>
          movieAlgebra.addActorToMovie(actorId, movieId).map(_ => Right(()))
      }
    )
}
