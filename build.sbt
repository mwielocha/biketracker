
// Generated with scalagen

lazy val root = (project in file(".")).
  settings(
    name := "biketracker",
    version := "1.0",
    scalaVersion := "2.12.2"
  )

mainClass in (Compile, run) := Some("io.cyberdolphin.biketracker.StartServer")

val akkaVersion = "2.5.3"
val akkaHttpVersion = "10.0.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.0.0" % Test
)

