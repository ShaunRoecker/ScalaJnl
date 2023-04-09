ThisBuild / scalaVersion := "3.2.1"


lazy val root = project
  .in(file("."))
  .settings(name := "Kata Sessions")
  .settings(commonSettings)
  .settings(consoleSettings)



lazy val commonSettings = Seq(
  scalacOptions ++= List(
    "-encoding", "utf8",        
    "-feature",                 
    "-language:implicitConversions",
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


