package busymachines.json

import io.circe.Decoder.Result
import io.circe._
import monix.newtypes.{HasBuilder, HasExtractor}

trait NewtypesCodecs {

  implicit def newtypeCirceDecoder[New, Base](
      implicit builder: HasBuilder.Aux[New, Base],
      decoder: Decoder[Base]
  ): Decoder[New] =
    decoder.map(_.asInstanceOf[New])

  implicit def newtypeCirceEncoder[New, Base](
      implicit extractor: HasExtractor.Aux[New, Base],
      encoder: Encoder[Base]
  ): Encoder[New] =
    encoder.contramap(extractor.extract)
}
