package busymachines.algebra.impl

import busymachines.algebra._
import busymachines.domain._
import cats.data.NonEmptyList
import cats.effect.IO

import java.util.UUID

object MovieAlgebraNoOp extends MovieAlgebra {

  override def createMovie(newMovie: CreateMovie): IO[Movie] =
    for {
      movieId <- IO.delay(MovieId(UUID.randomUUID()))
    } yield Movie(
      id = movieId,
      year = newMovie.year,
      directors = newMovie.directors,
      publisher = newMovie.publisher,
      title = newMovie.title,
      actors = Nil
    )

  /**
   * Probably one transaction to:
   *   i. Check if actor exists
   *   i. Insert actor
   *   i. Return updated movie with attached actor
   */
  override def addActorToMovie(actorId: ActorId, movieId: MovieId): IO[Movie] =
    IO.pure(
      Movie(
        id = movieId,
        year = Year(2021),
        directors = NonEmptyList.of(FullName("manufacturist")),
        publisher = "BusyMachines",
        title = Title("Newtypes + Tapir = <3"),
        actors = Actor(
          id = actorId,
          code = ActorCode("SQG388"),
          name = FullName("Scala Community"),
          age = Age(17)
        ) :: Nil
      )
    )
}
