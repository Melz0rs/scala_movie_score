package movieProvidersPlugins

import classes.Score
import httpClient.HttpClient

import scala.concurrent.Future

trait MovieProvider {

  def getScore(movieName: String): Future[Score]

  //  def internalGetScore(movieName: String)(implicit httpClient: HttpClient): Future[Score]

}
