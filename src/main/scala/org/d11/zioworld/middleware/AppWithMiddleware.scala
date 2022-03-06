package org.d11.zioworld.middleware
import zhttp.http.{Http, Middleware, Request, Response}
import zhttp.service.Server
import zio.{ExitCode, URIO}

object AppWithMiddleware extends zio.App {
  val app = Http.collect[Request] {
    case _ => Response.text("Hello, from ZIO-HTTP")
  }

  val middleware: Middleware[Any, Nothing, Request, Response, Request, Response] = ???

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    Server.start(8090, app @@ ???).exitCode
}
