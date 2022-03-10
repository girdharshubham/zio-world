package org.d11.zioworld.websocket

import zhttp.service._
import zhttp.socket._
import zio._
import zio.stream._

object WsClient extends zio.App {
  private val env = EventLoopGroup.auto(8) ++ ChannelFactory.auto
  private val url = "ws://localhost:8090/subscriptions"

  private val app: ZIO[zio.console.Console with EventLoopGroup with ChannelFactory,Throwable,Client.ClientResponse] =
    Socket
      .collect[WebSocketFrame] {
        case fr @ WebSocketFrame.Text(_) => ZStream.succeed(fr)
      }
      .toSocketApp
      .onOpen(Socket.succeed(WebSocketFrame.text("FOO")))
      .onClose(cn => zio.console.putStrLn(s"Connection closed: ${cn}").!)
      .onError(thr => ZIO.die(thr))
      .connect(url)

  override def run(args: List[String]): URIO[ZEnv, ExitCode] =
    app
      .provideCustomLayer(env)
      .exitCode
}
