package busymachines.api.endpoints.implicits

import sprout._
import sttp.tapir.Codec.PlainCodec
import sttp.tapir._

trait CustomTapirCodecs {

  implicit def sproutCodec[Old, New](
      implicit nt: NewType[Old, New],
      codec: PlainCodec[Old],
      schema: Schema[New]
  ): PlainCodec[New] =
    codec.map[New](nt.newType _)(nt.oldType).schema(schema)
}
