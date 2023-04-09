ThisBuild / scalaVersion := "2.13.10"

ThisBuild / watchTriggeredMessage := Watch.clearScreenOnTrigger
Global / onChangedBuildSource := IgnoreSourceChanges
ThisBuild / shellPrompt := (_ => fPrompt(name.value))
ThisBuild / Compile / run / mainClass := Some("Interpreter")

lazy val library =
    project
        .in(file("./library"))
        .settings(shellPrompt := (_ => fPrompt(name.value)))


lazy val application =
    project
        .in(file("./application"))
        .settings(shellPrompt := (_ => fPrompt(name.value)))
        .dependsOn(library)


lazy val main =
    project
        .in(file("./main"))
        .settings(shellPrompt := (_ => fPrompt(name.value)))
        .settings(commonSettings)
        .dependsOn(application)


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

def cyan(value: String): String = 
    scala.Console.CYAN + value + scala.Console.RESET


def fPrompt(projectName: String): String =
  s"""|
      |[info] Welcome to the ${cyan(projectName)} project!
      |sbt>""".stripMargin


addCommandAlias("lp", "projects")

addCommandAlias("cd", "project")

addCommandAlias("root", "project monads")

addCommandAlias("lib", "project library")

addCommandAlias("app", "project application")

