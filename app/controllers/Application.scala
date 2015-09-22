package controllers

import play.api._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.Future

class Application extends Controller {

	import models.JsonFormats._
	import models._

	def index = Action {
		Ok(views.html.index("Silicon Valley Quotes"))
	}

	def putCharacter = Action.async(parse.json) { request =>
		/*
		 * request.body is a JsValue.
		 * There is an implicit Writes that turns this JsValue as a JsObject,
		 * so you can call insert() with this JsValue.
		 * (insert() takes a JsObject as parameter, or anything that can be
		 * turned into a JsObject using a Writes.)
		 */
		request.body.validate[models.Character].map { char =>
			Database.putCharacter(char).map { lastError =>
				Logger.debug(s"Successfully inserted with LastError: $lastError")
				Created
			}
		}.getOrElse(Future.successful(BadRequest("invalid json")))
	}

	def findByName(name: String) = Action.async {
		Database.getCharacter(name).map(c =>
			Ok(Json.toJson(c.orElse(None))))
	}

	def getRandomQuote = Action.async {
		Database.getRandomQuote.map(q =>
			Ok(Json.toJson(q.orElse(None))))
	}
}
