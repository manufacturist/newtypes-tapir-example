package busymachines.api.routes

import cats.effect.IO
import sttp.tapir.server.ServerEndpoint

trait Routes {

  def serverEndpoints: List[ServerEndpoint[Any, IO]]
}
