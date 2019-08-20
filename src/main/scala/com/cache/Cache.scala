package com.cache

import com.redis.serialization.Parse

import scala.concurrent.Future

trait Cache {
  def get[A](key: String) /*(implicit parser: Parse[A])*/ : Future[Option[A]]
  def set(key: String, value: Any): Future[Unit]
}
