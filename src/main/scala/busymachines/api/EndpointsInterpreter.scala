package busymachines.api

import cats.effect.IO
import org.http4s.HttpRoutes
import sttp.tapir.{Codec, DecodeResult, DocsExtension}
import sttp.tapir.apispec.ExtensionValue
import sttp.tapir.server._
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import sttp.tapir.openapi.Info
import sttp.tapir.openapi.circe.yaml.RichOpenAPI
import sttp.tapir.redoc.Redoc
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.swagger.SwaggerUI

import scala.collection.immutable.ListMap

object EndpointsInterpreter {

  private val baseApiInfo = Info(
    title = "Newtypes + Tapir = ❤️",
    version = "1.0.0"
  )

  private def generateSwaggerDocs(
      serverEndpoints: List[ServerEndpoint[Any, IO]]
  ): List[ServerEndpoint[Any, IO]] = {
    val apiInfo = baseApiInfo.description(
      """
        |<a href="https://www.busymachines.com">
        | <img src="https://www.busymachines.com/wp-content/uploads/Website_BusyMachines%40x2.png">
        |</a><br>
        |Example brought to you by [BusyMachines](https://www.busymachines.com).
        |<hr>
        |
        |## Swagger docs
        |Swagger differs from Redoc in a few key aspects. The ones that are of interest for this example:
        |1. Iterables display the underlying schema of newtypes, where as Redoc does not (unless explicit in schema)
        |2. Redoc supports custom extensions. [Here's a list of them](https://redoc.ly/docs/api-reference-docs/spec-extensions/)
        |3. It has a `try it` (execute request from browser) feature
        |4. Swagger documentation links will always open in a new tab
        |
        |## About the libraries
        |Both [sprout](https://github.com/lorandszakacs/sprout) & [tapir](https://github.com/softwaremill/tapir) 
        |are on Github.""".stripMargin
    )

    val openApiYaml = OpenAPIDocsInterpreter()
      .toOpenAPI(
        es = serverEndpoints.map(_.endpoint),
        info = apiInfo
      )
      .toYaml

    SwaggerUI[IO](
      yaml = openApiYaml,
      prefix = "api" :: "public" :: "swagger" :: Nil
    )
  }

  private def generateRedocDocs(
      serverEndpoints: List[ServerEndpoint[Any, IO]]
  ): List[ServerEndpoint[Any, IO]] = {
    val apiInfo = baseApiInfo
      .description(
        """
          |Example brought to you by **[BusyMachines](https://www.busymachines.com)**.<br>
          |
          |## Redoc docs
          |Unlike Swagger, the open source variant of Redoc lacks a `try it` feature (execute request from browser). It 
          |is accessible with a **free** account on the [redoc.ly](https://redoc.ly/pricing/) platform.
          |
          |When creating an `Api` definition in `app.redoc.ly`, you need to specify a source. Two approaches for private 
          |projects are: 
          |1. URL - You need to specify the url of the `docs.yaml` file & a polling interval (implies exposing docs)
          |2. Upload via CI/CD - In your build pipeline, you can upload the specification using an API key 
          |
          |One could also attempt to write his own Redoc html generator instead of using the tapir one, to use this 
          |[redoc-try extension](https://github.com/wll8/redoc-try) that would enable the `try it` feature.
          |
          |## About the libraries
          |Both [sprout](https://github.com/lorandszakacs/sprout) & [tapir](https://github.com/softwaremill/tapir) 
          |are on Github.""".stripMargin
      )
      .extensions(
        ListMap(
          "x-logo" -> ExtensionValue(
            """{
              |  "url": "https://www.busymachines.com/wp-content/uploads/Website_BusyMachines%40x2.png",
              |  "href": "https://www.busymachines.com/"
              |}""".stripMargin
          )
        )
      )

    val groupTags =
      """[
        |  {
        |    "name": "Example endpoints",
        |    "tags": ["Actor", "Movie"]
        |  }
        |]""".stripMargin

    val redocGroupingExtension = DocsExtension[String](
      key = "x-tagGroups",
      value = groupTags,
      codec = Codec.json[String](DecodeResult.Value(_))(identity)
    )

    val openApiYaml = OpenAPIDocsInterpreter()
      .toOpenAPI(
        es = serverEndpoints.map(_.endpoint),
        info = apiInfo,
        docsExtensions = redocGroupingExtension :: Nil
      )
      .toYaml

    Redoc[IO](
      title = apiInfo.title,
      yaml = openApiYaml,
      prefix = "api" :: "public" :: "redoc" :: Nil
    )
  }

  def apply(serverEndpoints: List[ServerEndpoint[Any, IO]]): HttpRoutes[IO] = {
    val swaggerEndpoints = generateSwaggerDocs(serverEndpoints)
    val redocEndpoints = generateRedocDocs(serverEndpoints)

    Http4sServerInterpreter[IO].toRoutes(serverEndpoints ++ redocEndpoints ++ swaggerEndpoints)
  }

}
