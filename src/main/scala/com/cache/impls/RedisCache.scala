package com.cache.impls

import com.cache.Cache
import com.redis._

class RedisCache() extends Cache {

  val r = new RedisClient("localhost", 6379)

  override def get[A](key: String): Option[A] = {
    r.get(key).asInstanceOf(A)
  }

  override def set(key: String, value: Any): Unit = {
    r.set(key, value)
  }
}
