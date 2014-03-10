organization := "com.example"

name := "unfiltered-load-test"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
   "net.databinder" %% "unfiltered-netty-server" % "0.7.1",
   "net.databinder.dispatch" %% "dispatch-core" % "0.10.0",
   "org.clapper" %% "avsl" % "1.0.1",
   "net.databinder" %% "unfiltered-spec" % "0.7.1" % "test"
)

resolvers ++= Seq(
  "jboss repo" at "http://repository.jboss.org/nexus/content/groups/public-jboss/"
)
