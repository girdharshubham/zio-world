package org.d11.zioworld

import zhttp.http._
import zhttp.service.Server
import zio._

object Zioworld extends App {
  val app: Http[Any, Nothing, Request, Response] = Http.collect[Request] {
    case Method.GET -> !! / "greet" => Response.text("Hello, ZIO World!")
  }

  // Run it like any simple app
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    Server.start(8090, app).exitCode
}
