package org.d11.zioworld.experimental

import zhttp.http._
import zhttp.service._
import zio._

object RequestStreaming extends zio.App {
  private val app = Http.collect[Request] {
    case req @ Method.POST -> !! / "echo" =>
      Response(data = HttpData.fromStream(req.bodyAsStream))
  }

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    Server.start(8090, app).exitCode
}
