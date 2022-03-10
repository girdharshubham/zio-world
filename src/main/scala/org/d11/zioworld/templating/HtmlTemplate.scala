package org.d11.zioworld.templating

import zhttp.html._
import zhttp.http._
import zio._

object HtmlTemplate extends zio.App {
  val template: Dom = html(
    head(
      title("Hello, from ZIO-HTTP"),
    ),
    body(
      div(
        css := "container" :: "text-align-left" :: Nil,
        h1("Hello, World"),
      ),
    ),
  )
  private val app   = Http.html(template.encode)

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {
    for {
      response <- app(Request())
    } yield println(response.data)
  }.exitCode
}
