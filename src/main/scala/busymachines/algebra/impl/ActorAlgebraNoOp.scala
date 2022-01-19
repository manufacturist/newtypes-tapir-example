package busymachines.algebra.impl

import busymachines.algebra._
import busymachines.domain._
import cats.effect.IO

import java.util.UUID

object ActorAlgebraNoOp extends ActorAlgebra {

  override def createActors(actors: List[CreateActor]): IO[Unit] =
    IO.unit

  override def findActorById(actorId: ActorId): IO[Option[Actor]] =
    IO.pure(
      Some(
        Actor(
          id = ActorId(UUID.fromString("f1914228-c8ca-4cfb-a7da-7d31bcd13e19")),
          code = ActorCode.unsafe("THR742"),
          name = FullName("Brad Pitt"),
          age = Age(57)
        )
      )
    )
}
