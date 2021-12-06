package busymachines.api.routes
import busymachines.algebra.ActorAlgebra
import busymachines.api.endpoints.ActorEndpoints
import cats.effect.IO
import sttp.tapir.server.ServerEndpoint

class ActorRoutes(actorAlgebra: ActorAlgebra) extends Routes {

  override def serverEndpoints: List[ServerEndpoint[Any, IO]] =
    List(
      ActorEndpoints.createActors.serverLogic[IO] { createActorCommands =>
        actorAlgebra.createActors(createActorCommands).map(Right(_))
      },
      ActorEndpoints.findActorById.serverLogic[IO] { actorId =>
        actorAlgebra.findActorById(actorId).map {
          case None => Left(())
          case existingActor => Right(existingActor)
        }
      }
    )
}
