ThisBuild / organization := "com.upcr"
ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.2.2"
ThisBuild / trackInternalDependencies := TrackLevel.TrackIfMissing
ThisBuild / exportJars := true

// Enter in sbt shell to temporarily change the scala version for the project
//++2.11.8!


// another way to establish common settings

// lazy val commonSettings = Seq(
//   organization := "com.upcr",
//   version := "0.1.0-SNAPSHOT",
//   scalaVersion := "3.2.2"
// )


val scala3Version = "3.2.2"
val zioVersion = "2.0.0"

// Examples of creating keys:
// val scalacOptions = taskKey[Seq[String]]("Options for the Scala compiler.")
// val update = taskKey[UpdateReport]("Resolves and optionally retrieves dependencies, producing a report.")
// val clean = taskKey[Unit]("Deletes files produced by the build, such as generated sources, compiled classes, and task caches.")

//Here’s how we can rewire scalacOptions:
// scalacOptions := {
//   val ur = update.value  // update task happens-before scalacOptions
//   val x = clean.value    // clean task happens-before scalacOptions
//   // ---- scalacOptions begins here ----
//   ur.allConfigurations.take(3)
// }

// lazy val root = (project in file("."))
//   .settings(
//     name := "Hello",
//     organization := "com.example",
//     scalaVersion := "2.12.16",
//     version := "0.1.0-SNAPSHOT",
//     scalacOptions := List("-encoding", "utf8", "-Xfatal-warnings", "-deprecation", "-unchecked"),
//     scalacOptions := {
//       val old = scalacOptions.value
//       scalaBinaryVersion.value match {
//         case "2.12" => old
//         case _      => old filterNot (Set("-Xfatal-warnings", "-deprecation").apply)
//       }
//     }
//   )


lazy val hello = taskKey[Unit]("An example task")

lazy val zioSessions = (project in file("."))
    .aggregate(core)  //Set aggregate so that the command sent to this progect is 
                              // broadcasted to the specified project as well
 // .dependsOn(otherProject)  
    .enablePlugins(JavaAppPackaging)
    .settings(                  // 
        name := "zio-sessions",
        version := "0.1.0-SNAPSHOT",
        scalaVersion := scala3Version,
        libraryDependencies ++= Seq(
          "dev.zio" %% "zio" % zioVersion,
          "org.scalameta" %% "munit" % "0.7.29" % Test
        ),
        hello := { println("Hello!") },
        update / aggregate := false
        
    ).dependsOn(core % "test->test;compile->compile")

lazy val core = (project in file("core"))
    .settings(
      libraryDependencies ++= Seq(
          "dev.zio" %% "zio" % zioVersion,
          "org.scalameta" %% "munit" % "0.7.29" % Test
      )
    )
    // .settings(
    //   commonSettings
    // )

// Optionally the base directory may be omitted if it’s the same as the name of the val.
// lazy val core = project


lazy val dontTrackMe = (project in file("dontTrackMe"))
  .settings(
    exportToInternal := TrackLevel.NoTracking
  )

