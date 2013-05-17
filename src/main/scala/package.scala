package net.superbaloo
package jsonrepathmongo


import play.api.libs.json._

object `package` {

  case class JsPathMongo(jp: JsPath) {
    def writemongo[A](implicit writer: Writes[A]): OWrites[A] = {
      new OWrites[A] {
        def writes(o: A) = {
          val newPath = jp.path.flatMap{
            case e: KeyPathNode => Some(e.key)
            case e: RecursiveSearch => Some(s"$$.${e.key}")
            case e: IdxPathNode => Some(s"${e.idx}")
          }.mkString(".")

          val orig = writer.writes(o)
          orig match {
            case JsObject(e) => 
              JsObject(e.flatMap{
                case (k, v) => Seq(s"${newPath}.${k}" -> v)
              })
            case e: JsValue => JsObject(Seq(newPath -> e))
          }
        }
      }

    }
  }

  implicit def jsPathMongo(jp: JsPath): JsPathMongo = JsPathMongo(jp)
}


