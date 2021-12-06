package busymachines.json

import io.circe._
import sprout._

trait SproutCodecs {

  implicit def sproutCirceEncoder[Old, New](
      implicit oldType: OldType[Old, New],
      encoder: Encoder[Old]): Encoder[New] =
    encoder.contramap(oldType.oldType)

  implicit def sproutCirceDecoder[Old, New](
      implicit newType: NewType[Old, New],
      decoder: Decoder[Old]): Decoder[New] = decoder.map(newType.newType)
}
