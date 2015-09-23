package controllers

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc._

class Application extends Controller {

	import models.JsonFormats._
	import models._

	def index = Action {
		Ok(views.html.index("Silicon Valley Quotes"))
	}

	def getRandomQuote = Action.async {
		Database.getRandomQuote.map(q =>
			Ok(Json.toJson(q.orElse(None))))
	}
}
