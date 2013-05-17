package net.superbaloo
package jsonrepathmongo

import org.specs2.mutable._

import play.api.libs.json._
import play.api.libs.functional._
import play.api.libs.functional.syntax._


class Test extends Specification {
  "JsPath composition" should {
    "merge for.path.in.mongodb.format" in {
      case class Limit(low: Option[Int], high: Option[Int])
      case class App(limit: Option[Limit])

      val lowWriter = (__ \ "low").writeNullable[Int]
      val highWriter = (__ \ "high").writeNullable[Int]
      val limitWriter = (lowWriter and highWriter)(unlift(Limit.unapply))
      val appWriter = (__ \ "limit").writemongo[Limit](limitWriter)

      println(appWriter.writes(Limit(Some(1), None)))
      println(appWriter.writes(Limit(Some(1), Some(2))))
      println(appWriter.writes(Limit(None, None)))

      appWriter.writes(Limit(Some(1), None)) mustEqual
        Json.obj("limit.low" -> 1)
      appWriter.writes(Limit(Some(1), Some(2))) mustEqual
        Json.obj("limit.low" -> 1, "limit.high" -> 2)
      appWriter.writes(Limit(None, None)) mustEqual
        Json.obj()
    }
  }
}


