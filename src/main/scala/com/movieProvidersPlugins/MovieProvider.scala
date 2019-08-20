package com.movieProvidersPlugins

import com.classes.Score
import com.httpClient.HttpClient

import scala.concurrent.Future

trait MovieProvider {

  def getScore(movieName: String): Future[Score]

}
