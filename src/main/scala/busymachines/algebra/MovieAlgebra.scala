package busymachines.algebra

import busymachines.domain._
import cats.effect.IO

trait MovieAlgebra {

  def createMovie(newMovie: CreateMovie): IO[Movie]

  def addActorToMovie(actorId: ActorId, movieId: MovieId): IO[Movie]

}
