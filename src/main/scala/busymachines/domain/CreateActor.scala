package busymachines.domain

import busymachines.json._

case class CreateActor(
    code: ActorCode,
    name: FullName,
    age: Age
)

object CreateActor {

  implicit val createActorCodec: Codec[CreateActor] = deriveCodec
}
