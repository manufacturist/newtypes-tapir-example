package busymachines

import io.circe._
import io.circe.generic.codec.DerivedAsObjectCodec
import shapeless.Lazy

package object json extends NewtypesCodecs {

  type Codec[A] = io.circe.Codec[A]

  def deriveCodec[A](implicit codec: Lazy[DerivedAsObjectCodec[A]]): Codec.AsObject[A] =
    codec.value
}
