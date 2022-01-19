package busymachines.api.endpoints.implicits

import monix.newtypes.{HasBuilder, HasExtractor}
import sttp.tapir.Codec.PlainCodec
import sttp.tapir._

trait CustomTapirCodecs {

  implicit def sproutCodec[New, Base](
      implicit builder: HasBuilder.Aux[New, Base],
      extractor: HasExtractor.Aux[New, Base],
      codec: PlainCodec[Base],
      schema: Schema[New]
  ): PlainCodec[New] =
    codec.map(_.asInstanceOf[New])(extractor.extract).schema(schema)
}
