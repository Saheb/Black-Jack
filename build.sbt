libraryDependencies ++= Seq(
	"com.typesafe.akka" %% "akka-actor" % "2.3.9"
)

lazy val commonSettings = Seq(
  organization := "com.games",
  version := "0.1.0",
  scalaVersion := "2.11.5"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "Black Jack"
  )