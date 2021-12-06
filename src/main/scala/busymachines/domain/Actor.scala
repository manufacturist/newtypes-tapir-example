package busymachines.domain

import busymachines.json._

case class Actor(
    id: ActorId,
    code: ActorCode,
    name: FullName,
    age: Age
)

object Actor {

  implicit val actorCodec: Codec[Actor] = deriveCodec
}
