import sbt._

object Dependencies {
  val ZioVersion   = "1.0.13"
  val ZHTTPVersion = "1.0.0.0-RC25+14-0dca97d1-SNAPSHOT"

  val `zio-http`      = "io.d11" %% "zhttp" % ZHTTPVersion
  val `zio-http-test` = "io.d11" %% "zhttp" % ZHTTPVersion % Test

  val `zio-test`     = "dev.zio" %% "zio-test"     % ZioVersion % Test
  val `zio-test-sbt` = "dev.zio" %% "zio-test-sbt" % ZioVersion % Test
}
