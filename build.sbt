
// Generated with scalagen

lazy val root = (project in file(".")).
  settings(
    name := "biketracker",
    version := "1.0",
    scalaVersion := "2.12.2"
  )

mainClass in (Compile, run) := Some("io.cyberdolphin.biketracker.StartServer")

val akkaHttpVersion = "10.0.8"
val circeVersion = "0.8.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "de.heikoseeberger" %% "akka-http-circe" % "1.17.0",
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
  "org.scalatest" %% "scalatest" % "3.0.0" % Test
)

