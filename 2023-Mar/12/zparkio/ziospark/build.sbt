val projectName = IO.readLines(new File("PROJECT_NAME")).head
val v = IO.readLines(new File("VERSION")).head

lazy val rootSettings = Seq(
  organization := "com.organization",
  scalaVersion := "2.12.17",
  version      := v
)

lazy val sparkVersion = "3.3.1"
lazy val zparkioVersion = {
  val raw = "2.4.8_1.1.0"
  val zparkioVersion = raw.split("_")(1)
  s"${sparkVersion}_${zparkioVersion}"
}

lazy val root = (project in file("."))
  .settings(
    name := projectName,
    rootSettings,
    libraryDependencies ++= Seq(
      // https://mvnrepository.com/artifact/org.apache.spark/spark-core
      "org.apache.spark" %% "spark-core" % sparkVersion % Provided ,
      // https://mvnrepository.com/artifact/org.apache.spark/spark-sql
      "org.apache.spark" %% "spark-sql"  % sparkVersion % Provided ,
      // https://github.com/leobenkel/ZparkIO
      "com.leobenkel"    %% "zparkio"      % zparkioVersion,
      "com.leobenkel"    %% "zparkio-test" % zparkioVersion    % Test,
      // https://www.scalatest.org/
      "org.scalatest"    %% "scalatest"    % "3.2.15" % Test
    )
  )
