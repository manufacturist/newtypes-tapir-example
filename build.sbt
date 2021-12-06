scalaVersion := "2.13.7"
name := "newtypes-tapir-cats-example"
organization := "busymachines"
version := "1.0.0"

val http4sV = "0.23.6"
val logbackV = "1.2.3"
val tapirV = "0.19.1"
val sproutV = "0.0.5"

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full)

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % logbackV withSources (),
  "com.lorandszakacs" %% "sprout" % sproutV withSources (),
  "com.softwaremill.sttp.tapir" %% "tapir-core" % tapirV withSources (),
  "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % tapirV withSources (),
  "com.softwaremill.sttp.tapir" %% "tapir-cats" % tapirV withSources (),
  "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % tapirV withSources (),
  "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs" % tapirV withSources (),
  "com.softwaremill.sttp.tapir" %% "tapir-openapi-circe-yaml" % tapirV withSources (),
  "com.softwaremill.sttp.tapir" %% "tapir-redoc" % tapirV withSources (),
  "org.http4s" %% "http4s-dsl" % http4sV withSources (),
  "org.http4s" %% "http4s-blaze-server" % http4sV withSources (),
  "org.http4s" %% "http4s-blaze-client" % http4sV withSources ()
)
