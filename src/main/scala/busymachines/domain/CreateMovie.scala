package busymachines.domain

import cats.data.NonEmptyList
import busymachines.json._

case class CreateMovie(
    year: Year,
    directors: NonEmptyList[FullName],
    publisher: String,
    title: Title
)

object CreateMovie {

  implicit val createMovieCodec: Codec[CreateMovie] = deriveCodec
}
