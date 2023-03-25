ThisBuild / scalaVersion := "2.13.10"
ThisBuild / version          := "0.0.1"
ThisBuild / organization     := "dev.scr"

// ThisBuild / scalafmtOnCompile := true
ThisBuild / watchTriggeredMessage := Watch.clearScreenOnTrigger
ThisBuild / shellPrompt := (_ => fPrompt(name.value))

resolvers ++= Resolver.sonatypeOssRepos("snapshots")


lazy val root = project
  .in(file("."))
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(JavaServerAppPackaging)
  .settings(name := "Scala Sessions")
  .settings(dependencies)
  .settings(commonSettings)
  .settings(consoleSettings)



lazy val commonSettings = Seq(
  scalacOptions ++= List(
    "-encoding", "utf8",        
    "-feature",                 
    "-language:implicitConversions",
    "-language:higherKinds",
    "-language:existentials",
    "-unchecked",
    "-Werror",
  )
)

lazy val consoleSettings = Seq(
  Compile / console / scalacOptions --= Seq("-Ywarn-unused", "-Ywarn-unused-import")
)

lazy val compilerOptions = {
  val commonOptions = Seq(
    "-unchecked",
    "-deprecation",
    "-encoding",
    "-Ywarn-unused:imports"
  )

  scalacOptions ++= commonOptions
}


lazy val dependencies = 
  libraryDependencies ++= Dependencies.commonDependencies



def cyan(value: String): String = 
    scala.Console.CYAN + value + scala.Console.RESET


def fPrompt(projectName: String): String =
  s"""|
      |[info] Welcome to the ${cyan(projectName)} project!
      |sbt>""".stripMargin



addCommandAlias("lp", "projects")

addCommandAlias("cd", "project")

addCommandAlias("root", "project root")


