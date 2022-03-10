package org.d11.zioworld.websocket

import zhttp.http._
import zhttp.service.Server
import zhttp.socket.{Socket, WebSocketFrame}
import zio._
import zio.stream.ZStream

object WsServer extends zio.App {
  private val socket =
    Socket.collect[WebSocketFrame] {
      case WebSocketFrame.Text("FOO") => ZStream.succeed(WebSocketFrame.text("BAR"))
      case WebSocketFrame.Text("BAR") => ZStream.succeed(WebSocketFrame.text("BAZ"))
      case _                          => ZStream.succeed(WebSocketFrame.close(1000))
    }

  private val app = Http.collectZIO[Request] {
    case Method.GET -> !! / "subscriptions" => Response.fromSocket(socket)
  }

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = Server.start(8090, app).exitCode
}
