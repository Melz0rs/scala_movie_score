package traits

import classes.{MoviesDataProviderParseOptions, Score}
import clients.BaseHttpClient
import utils.{ParseOp, Utils}
import org.json4s._
import org.json4s.jackson.JsonMethods._

import scala.concurrent.Future

trait MoviesDataProviderTrait extends BaseHttpClient {

  val parseOptions: MoviesDataProviderParseOptions
  val url: String
  val headers: Map[String, String]
  val onError: Exception => Unit = (e: Exception) => println(s"[MovieDataProvider] an error occured: $e")

  def getScore(movieName: String): Future[Option[Score]] = {
    get(url.format(movieName)).map(parseScore)
  }

  private def parseScore(movieJsonString: String): Option[Score] = {
    implicit val popDouble = ParseOp[Double](_.toDouble)
    implicit val formats: DefaultFormats = org.json4s.DefaultFormats

    val movieJson = parse(movieJsonString).extract[Map[String, String]]

    val rawScore = Utils.tryParse[Double](movieJson(parseOptions.prop))

    rawScore.map(Score)
  }
}
