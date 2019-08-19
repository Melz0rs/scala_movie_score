package traits

import classes.Score
import clients.HttpClient
import org.json4s._
import org.json4s.jackson.JsonMethods._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait MovieProvider {
  def getScore(movieName: String)(implicit httpClient: HttpClient): Future[Score]
}
