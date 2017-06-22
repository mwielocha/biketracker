import com.typesafe.sbt.packager.archetypes.ServerLoader

// Generated with scalagen

lazy val root = (project in file(".")).
  settings(
    name := "biketracker",
    version := "1.0",
    scalaVersion := "2.12.2"
  ).enablePlugins(JavaServerAppPackaging)

mainClass in (Compile, run) := Some("io.cyberdolphin.biketracker.StartServer")

val akkaHttpVersion = "10.0.8"
val circeVersion = "0.8.0"

libraryDependencies ++= Seq(
  "com.github.pureconfig" %% "pureconfig" % "0.7.2",
  "com.github.pathikrit"  %% "better-files" % "3.0.0",
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "de.heikoseeberger" %% "akka-http-circe" % "1.17.0",
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
  "org.scalatest" %% "scalatest" % "3.0.0" % Test
)

assemblyMergeStrategy in assembly := {
  case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.discard
  case x if x.endsWith("reference.conf") => MergeStrategy.concat
  case x => (assemblyMergeStrategy in assembly).value(x)
}

mappings in Universal += {
    val conf = (resourceDirectory in Compile).value / "reference.conf"
    conf -> "conf/reference.conf"
}

bashScriptExtraDefines += """addJava "-Dconfig.file=${app_home}/../conf/application.conf""""

maintainer := "Mikolaj Wielocha <mwielocha@icloud.com>"

packageSummary := "Biketracker"

packageDescription := "Biketracker"

serverLoading := ServerLoader.Upstart

