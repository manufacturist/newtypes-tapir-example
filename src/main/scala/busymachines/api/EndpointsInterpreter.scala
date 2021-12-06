package busymachines.api

import cats.effect.IO
import org.http4s.HttpRoutes
import sttp.tapir.apispec.ExtensionValue
import sttp.tapir.server._
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import sttp.tapir.openapi.Info
import sttp.tapir.openapi.circe.yaml.RichOpenAPI
import sttp.tapir.redoc.Redoc
import sttp.tapir.server.http4s.Http4sServerInterpreter

import scala.collection.immutable.ListMap

object EndpointsInterpreter {

  def apply(serverEndpoints: List[ServerEndpoint[Any, IO]]): HttpRoutes[IO] = {
    val apiInfo = Info(
      title = "Newtypes + Tapir = ❤️",
      version = "1.0.0",
      description = Some(
        """
          |## Main description
          |One cool thing about ReDoc is that we can use **markdown** in descriptions.
          |
          |## About the libraries
          |Both [sprout](https://github.com/lorandszakacs/sprout) & [tapir](https://github.com/softwaremill/tapir) 
          |are on Github.""".stripMargin
      ),
      extensions = ListMap(
        "x-logo" -> ExtensionValue(
          """{
            |  "url": "https://www.busymachines.com/wp-content/uploads/Website_BusyMachines%40x2.png",
            |  "href": "https://www.busymachines.com/"
            |}""".stripMargin
        )
      )
    )

    val openApiYaml = OpenAPIDocsInterpreter()
      .toOpenAPI(
        es = serverEndpoints.map(_.endpoint),
        info = apiInfo
      )
      .toYaml

    val redocEndpoints = Redoc[IO](
      title = apiInfo.title,
      yaml = openApiYaml,
      prefix = "api" :: "public" :: "docs" :: Nil
    )

    Http4sServerInterpreter[IO].toRoutes(redocEndpoints ++ serverEndpoints)
  }

}
