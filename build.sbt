name := """silicon-valley-quotes"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
    jdbc,
    cache,
    ws,
    specs2 % Test
)

libraryDependencies ++= Seq(
    "com.h2database" % "h2" % "1.4.190",
    "com.typesafe.slick" %% "slick" % "3.0.0"
)
resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
