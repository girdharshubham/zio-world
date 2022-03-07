package org.d11.zioworld.middleware
import zhttp.http._
import zhttp.http.middleware.HttpMiddleware
import zhttp.service.Server
import zio.clock.Clock
import zio.duration.durationInt
import zio.{system, ExitCode, UIO, URIO}

object AppWithMiddleware extends zio.App {
  val app = Http.collectZIO[Request] {
    case Method.GET -> !! / "greet"       => UIO(Response.text("Hello, from ZIO-HTTP"))
    case Method.GET -> !! / "longrunning" => UIO(Response.text("took me a while")).delay(10.seconds)
  }

  val auth: HttpMiddleware[Any, Nothing]                                              = ???
  val timeout: Middleware[Any with Clock, Throwable, Nothing, Any, Request, Response] = ???
  val patchEnvironmentHeader: HttpMiddleware[system.System, SecurityException]        = ???

  val middleware =
    auth ++
      timeout ++
      patchEnvironmentHeader

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    Server.start(8090, app @@ middleware).exitCode
}
