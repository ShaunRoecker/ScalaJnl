addSbtPlugin("dev.zio" % "zio-sbt-website" % "0.1.5")
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.9.34+5-5dfe5fb6-SNAPSHOT")
resolvers += Resolver.sonatypeRepo("snapshots")

addDependencyTreePlugin
