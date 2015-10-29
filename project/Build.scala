import sbt._
import Keys._

object Build extends Build {

  def sharedSettings = Seq(
    scalaVersion:= "2.11.7",
    scalacOptions += "-deprecation",
    libraryDependencies ++= Seq(
      "junit" % "junit" % "4.11" % "test->default",
      "org.scalatest" %% "scalatest" % "2.2.4" % "test"
    ),
    // add scala-xml dependency when needed (for Scala 2.11 and newer)
    // this mechanism supports cross-version publishing
    libraryDependencies := {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, scalaMajor)) if scalaMajor >= 11 => libraryDependencies.value :+ "org.scala-lang.modules" %% "scala-xml" % "1.0.5"
        case _ => libraryDependencies.value
      }
    }
  )

  lazy val main = Project(id = "voa", base = file(".")).settings(sharedSettings: _*)
}
