package common.webServer

trait WebServer {
  def listen(host: String = "localhost", port: Int = 8080): Unit
}
