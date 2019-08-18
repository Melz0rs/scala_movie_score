package utils

case class ParseOp[T](op: String => T)

object Utils {

  def tryParse[T: ParseOp](s: String) =
    try {
      Some(implicitly[ParseOp[T]].op(s))
    } catch {
      case _ => None
    }
}
