package org.d11.zioworld

import zhttp.http._
import zio.test.Assertion._
import zio.test._

object ZioworldSpec extends DefaultRunnableSpec {
  override def spec: ZSpec[Environment, Failure] = suite("""ZioworldSpec""")(
    testM("200 ok") {
      checkAllM(Gen.fromIterable(List("greet"))) { uri =>
        val request = Request(Method.GET, URL(!! / uri))
        assertM(Zioworld.app(request).map(_.status))(equalTo(Status.OK))
      }
    },
  )
}
