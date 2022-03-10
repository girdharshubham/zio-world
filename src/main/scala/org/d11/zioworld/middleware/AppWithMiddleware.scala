package org.d11.zioworld.middleware
import zhttp.http._
import zhttp.http.middleware.HttpMiddleware
import zhttp.service.Server
import zio.duration.durationInt
import zio.{system, ExitCode, UIO, URIO, ZIO}

object AppWithMiddleware extends zio.App {
  val app = Http.collectZIO[Request] {
    case Method.GET -> !! / "greet"       => UIO(Response.text("Hello, from ZIO-HTTP"))
    case Method.GET -> !! / "longrunning" => UIO(Response.text("took me a while")).delay(10.seconds)
  }

  val auth = Middleware.basicAuth("shubham", "changeme")

  val timeout = Middleware.timeout(1.seconds)

  val customTimeout = Middleware
    .identity
    .race {
      Middleware
        .fromHttp(
          Http
            .status(Status.REQUEST_TIMEOUT)
            .delay(2.seconds),
        )
        .mapZIO { response =>
          ZIO.debug("Request took too long. Timing out") *>
            ZIO(response)
        }
    }

  val patchEnvironmentHeader: HttpMiddleware[zio.system.System,SecurityException] = Middleware.patchZIO { _ =>
    zio
      .system
      .envOrElse("ENV", "Dev")
      .mapBoth(
        thr => Option(thr),
        env => Patch.addHeader("X-Environment", env),
      )
  }

  val middleware =
    auth ++
      customTimeout ++
      patchEnvironmentHeader

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    Server.start(8090, app @@ middleware).exitCode
}
