package org.d11.zioworld.websocket

import zhttp.http._
import zhttp.socket.{Socket, WebSocketFrame}
import zio._

object WsServer extends zio.App {
  private val socket: Socket[Any, Nothing, WebSocketFrame, WebSocketFrame] = ???

  private val app: Http[Any, Throwable, Request, Response] = ???

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = ???
}
