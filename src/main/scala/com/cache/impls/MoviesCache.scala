package com.cache.impls

import com.cache.Cache

class MoviesCache() extends Cache {
  val cache: scala.collection.mutable.Map[String, Any] = scala.collection.mutable.Map()

  override def get[A](key: String): Option[A] = {
    for {
      v <- cache.get(key)
    } yield v.asInstanceOf[A]
  }

  override def set(key: String, value: Any): Unit = {
    cache(key) = value
  }
}
