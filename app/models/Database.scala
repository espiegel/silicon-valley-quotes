package models

import play.api.Play
import play.api.libs.json._
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import play.modules.reactivemongo.json.collection.{JSONCollection, _}
import reactivemongo.api.QueryOpts
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument
import reactivemongo.core.commands.Count

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Random

/**
 * Created by eidan on 9/20/15.
 */
object Database {

	import models.JsonFormats._

    val reactiveMongoApi = Play.current.injector.instanceOf[ReactiveMongoApi]
	val charactersCollection = "characters"
	val quoteCollection = "quotes"

	def collection(col: String): JSONCollection =
        reactiveMongoApi.db.collection[JSONCollection](col)

	def putCharacter(char: Character): Future[WriteResult] = {
		collection(charactersCollection).insert(char)
	}
	
	def getCharacter(name: String): Future[Option[Character]] = {
		collection(charactersCollection).find(Json.obj("name" -> name)).one[Character]
	}

	def getRandomQuote: Future[Option[Quote]] = {
		val futureCount = reactiveMongoApi.db.command(Count(quoteCollection))
		futureCount.flatMap { count =>
			val skip = Random.nextInt(count)
			collection(quoteCollection).find(BSONDocument()).options(QueryOpts(skipN = skip)).one[Quote]
		}
	}
}
