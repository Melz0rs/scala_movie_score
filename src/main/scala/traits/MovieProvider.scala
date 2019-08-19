package traits

import classes.Score
import httpClient.HttpClient
import scala.concurrent.Future

trait MovieProvider {
  def getScore(movieName: String)(implicit httpClient: HttpClient): Future[Score]
}
