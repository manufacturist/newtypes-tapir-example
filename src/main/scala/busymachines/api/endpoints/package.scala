package busymachines.api

import busymachines.api.endpoints.implicits._
import sttp.tapir._
import sttp.tapir.generic.SchemaDerivation
import sttp.tapir.integ.cats.TapirCodecCats
import sttp.tapir.json.circe.TapirJsonCirce

package object endpoints
    extends Tapir
    with SchemaDerivation
    with TapirCodecCats
    with TapirJsonCirce
    with CustomTapirSchemas
    with CustomTapirCodecs {

  val v1: Int = 1

  val v1Endpoint: Endpoint[Unit, Unit, Unit, Unit, Any] =
    endpoint.prependIn("api" / s"v$v1")
}
