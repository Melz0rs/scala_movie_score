package com.cache.impls

import com.cache.Cache
import com.exceptions.NotImplementedException
import com.redis._
import com.redis.serialization.Parse

import scala.concurrent.Future

class RedisCache() extends Cache {

  val r = new RedisClient("localhost", 6379)

  override def get[A](key: String) /*(implicit parser: Parse[A])*/ : Future[Option[A]] = {
    throw NotImplementedException()

    // TODO: Is this the correct way?
    //    Future.successful(
    //      r.get[A](key))
  }

  override def set(key: String, value: Any): Future[Unit] = {
    throw NotImplementedException()

    // TODO: Is this the correct way?
    //    Future.successful(
    //      r.set(key, value))
  }
}
