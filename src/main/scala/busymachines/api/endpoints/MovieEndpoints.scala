package busymachines.api.endpoints

import busymachines.domain._
import cats.data.NonEmptyList
import sttp.model.StatusCode
import sttp.tapir._

import java.util.UUID

object MovieEndpoints {

  private val baseEndpoint = v1Endpoint.tag("Movie").in("movie")

  val createMovie: Endpoint[Unit, CreateMovie, Unit, Movie, Any] =
    baseEndpoint
      .post
      .name("Create movie")
      .description("Creates a movie with no actors associated with it.")
      .in(
        jsonBody[CreateMovie]
          .description("The details of the new movie.")
          .example(
            CreateMovie(
              year = Year(2021),
              directors = NonEmptyList.of(FullName("Test test")),
              publisher = "",
              title = Title("Title")
            )
          )
      )
      .out(
        jsonBody[Movie]
          .description("The newly created movie.")
          .example(
            Movie(
              id = MovieId(UUID.randomUUID()),
              year = Year(2021),
              directors = NonEmptyList.of(FullName("Test test")),
              publisher = "",
              title = Title("Title"),
              actors = Nil
            )
          )
      )
      .out(statusCode(StatusCode.Ok))

  val addActorToMovie: Endpoint[Unit, (MovieId, ActorId), Unit, Unit, Any] =
    baseEndpoint
      .put
      .in(path[MovieId] / "actor" / path[ActorId])
      .name("Associate actor with movie")
      .description(
        "Associates an actor with a movie. Multiple actors can be associated with the same movie."
      )
      .out(emptyOutput)
      .out(statusCode(StatusCode.Ok))
}
