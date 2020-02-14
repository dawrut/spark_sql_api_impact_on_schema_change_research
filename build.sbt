lazy val root = (project in file("."))
  .settings(
    name := "research-spark-sql-api-impact-on-schema",
    organization := "pl.rutowicz",
    scalacOptions ++= Seq(
      "-Xfatal-warnings",
      "-language:higherKinds,implicitConversions",
      "-unchecked",
      "-deprecation",
      "-feature"
    ),
    scalaVersion := "2.12.8",
    javacOptions ++= {
      val ver = "1.8"

      Seq(
        "-source", ver,
        "-target", ver,
        "-Xms2G",
        "-Xmx2G",
        "-XX:MaxPermSize=2048M",
        "-XX:+CMSClassUnloadingEnabled"
      )
    },
    libraryDependencies ++= Seq(
      // @formatter:off
      "org.apache.spark"  %% "spark-core"   % "2.4.5",
      "org.apache.spark"  %% "spark-sql"    % "2.4.5",
      "org.apache.spark"  %% "spark-hive"   % "2.4.5"
      // @formatter:om
    ),
    parallelExecution in Test := false,
    fork := true
  )