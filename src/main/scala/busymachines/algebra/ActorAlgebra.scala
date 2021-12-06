package busymachines.algebra

import busymachines.domain._
import cats.effect.IO

trait ActorAlgebra {

  def createActors(actors: List[CreateActor]): IO[Unit]

  def findActorById(actorId: ActorId): IO[Option[Actor]]
}
