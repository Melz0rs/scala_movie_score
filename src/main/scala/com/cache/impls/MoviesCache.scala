package com.cache.impls

import com.cache.Cache

import scala.concurrent.Future

class MoviesCache() extends Cache {
  val cache: scala.collection.mutable.Map[String, Any] = scala.collection.mutable.Map()

  override def get[A](key: String): Future[Option[A]] = {
    val result = for {
      v <- cache.get(key)
    } yield v.asInstanceOf[A]

    Future.successful(result)
  }

  override def set(key: String, value: Any): Future[Unit] = {
    cache(key) = value

    Future.successful()
  }
}
