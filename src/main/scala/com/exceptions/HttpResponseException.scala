package com.exceptions

import akka.http.scaladsl.model.HttpResponse

case class HttpResponseException(response: HttpResponse) extends Exception {

}
