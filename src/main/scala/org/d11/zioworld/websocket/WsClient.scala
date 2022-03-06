package org.d11.zioworld.websocket

import zhttp.service.{ChannelFactory, Client, EventLoopGroup}
import zio.{ExitCode, URIO, ZEnv, ZIO}

object WsClient extends zio.App {
  val env = EventLoopGroup.auto(8) ++ ChannelFactory.auto
  val url = "ws://localhost:8090/subscription"

  val app: ZIO[_, Throwable, Client.ClientResponse] = ???

  override def run(args: List[String]): URIO[ZEnv, ExitCode] = {
    for {
      unit <- ZIO.unit
    } yield unit
  }.provideCustomLayer(env).exitCode
}
