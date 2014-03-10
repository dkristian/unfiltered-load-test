organization := "com.example"

name := "unfiltered-load-test"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
   "net.databinder.dispatch" %% "dispatch-core" % "0.10.0",
   "org.clapper" %% "avsl" % "1.0.1",
   "io.netty" % "netty-codec-http" % "4.0.17.Final"
)

resolvers ++= Seq(
  "jboss repo" at "http://repository.jboss.org/nexus/content/groups/public-jboss/"
)
