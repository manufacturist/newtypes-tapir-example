package busymachines.domain

import cats.data.NonEmptyList
import busymachines.json._

case class Movie(
    id: MovieId,
    year: Year,
    directors: NonEmptyList[FullName],
    publisher: String,
    title: Title,
    actors: List[Actor]
)

object Movie {

  implicit val movieCodec: Codec[Movie] = deriveCodec
}
