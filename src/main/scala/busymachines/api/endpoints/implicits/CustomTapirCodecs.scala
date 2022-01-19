package busymachines.api.endpoints.implicits

import monix.newtypes._
import sttp.tapir.Codec.PlainCodec
import sttp.tapir._

trait CustomTapirCodecs {

  implicit def newtypesCodec[New, Base](
      implicit builder: HasBuilder.Aux[New, Base],
      extractor: HasExtractor.Aux[New, Base],
      codec: PlainCodec[Base],
      schema: Schema[New]
  ): PlainCodec[New] =
    codec
      .mapDecode { base =>
        builder.build(base) match {
          case Right(value) => DecodeResult.Value(value)
          case Left(error) => DecodeResult.Error(error.toReadableString, error.toException)
        }
      }(extractor.extract)
      .schema(schema)
}
