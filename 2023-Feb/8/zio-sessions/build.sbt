
import Dependencies._


Global / excludeLintKeys += name

ThisBuild / name := "zio-sessions"
ThisBuild / version := "0.1.0-SNAPSHOT"



val scala3Version = "3.2.2"
val zioVersion = "2.0.6"


lazy val root = project
  .in(file("."))
  .withId("ZIO02082023")
  .settings(scalaVersion := scala3Version)
  .settings(libraryDependencies ++= backendDeps)
  
  
  
    
    

    
