
// Generated with scalagen

lazy val root = (project in file(".")).
  settings(
    name := "biketracker",
    version := "1.0",
    scalaVersion := "2.12.2"
  )

//mainClass in (Compile, run) := Some("...")

libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.0" % "test"
  )

