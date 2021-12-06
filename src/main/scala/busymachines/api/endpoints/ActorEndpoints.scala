package busymachines.api.endpoints

import busymachines.domain._
import sttp.model.StatusCode
import sttp.tapir._

import java.util.UUID

object ActorEndpoints {

  private val baseEndpoint = v1Endpoint.tag("Actor")

  val createActors: Endpoint[Unit, List[CreateActor], Unit, Unit, Any] =
    baseEndpoint
      .in("actors")
      .post
      .name("Create actors")
      .description(
        "This endpoint is used to add multiple actors to the system."
      )
      .in(
        jsonBody[List[CreateActor]]
          .description("A list of new actors.")
          .example(
            List(
              CreateActor(
                code = ActorCode("MTX905"),
                name = FullName("Keanu Reeves"),
                age = Age(57)
              )
            )
          )
      )
      .out(emptyOutput)
      .out(statusCode(StatusCode.Created))

  val findActorById: Endpoint[Unit, ActorId, Unit, Option[Actor], Any] =
    baseEndpoint
      .in("actor" / path[ActorId])
      .get
      .name("Find actor by id")
      .description(
        "Attempts to retrieve the actor pertaining to the specified id."
      )
      .out(
        jsonBody[Option[Actor]]
          .description("The found actor.")
          .example(
            Some(
              Actor(
                id = ActorId(UUID.randomUUID()),
                code = ActorCode("MTX905"),
                name = FullName("Keanu Reeves"),
                age = Age(57)
              )
            )
          )
      )
      .out(statusCode(StatusCode.Ok))
}
