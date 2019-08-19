package traits

trait Cache {
  def get[A](key: String): Some[A]
  def set(key: String, value: Any)
}
