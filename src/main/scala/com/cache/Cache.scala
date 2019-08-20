package com.cache

trait Cache {
  def get[A](key: String): Option[A]
  def set(key: String, value: Any)
}