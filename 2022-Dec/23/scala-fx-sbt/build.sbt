
name := "scala-fx-sbt"

organization := "com.name"

scalaVersion := "2.13.7"


mainClass := Some("com.name.app.Application")

resolvers += "sfxcode-maven" at "https://bintray.com/sfxcode/maven/"


libraryDependencies += "org.specs2" %% "specs2-core" % "4.7.1" % Test

val JavaFXVersion = "16"

val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web").map(m => "org.openjfx" % s"javafx-$m" % JavaFXVersion classifier osName)


libraryDependencies +=   "com.sfxcode.sapphire" %% "sapphire-javafx" % "1.1.1"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"

enablePlugins(BuildInfoPlugin)

buildInfoPackage := "com.name.app"

buildInfoOptions += BuildInfoOption.BuildTime


enablePlugins(JavaFxPlugin)

javaFxMainClass := "com.name.app.Application"

javaFxJvmargs := Seq("-Xms512m", "-Xmx1024m", "-XX:ReservedCodeCacheSize=128m")

javaFxTitle := "scala-fx-sbt"

javaFxCategory := "Aplication"

javaFxNativeBundles := "image"

javaFxVerbose := true


