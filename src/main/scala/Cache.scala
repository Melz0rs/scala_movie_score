package cache

object Cache {

  val cache: scala.collection.mutable.Map[String, Any] = scala.collection.mutable.Map()

  def get[A](key: String): Option[A] = {
    for {
      v <- cache.get(key)
    } yield v.asInstanceOf[A]
  }

  def set(key: String, value: Any): Unit = {
    cache(key) = value
  }
}
