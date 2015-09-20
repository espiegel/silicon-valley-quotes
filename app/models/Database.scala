package models

import play.api.Play
import play.api.libs.json._
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import play.modules.reactivemongo.json.collection.{JSONCollection, _}
import reactivemongo.api.commands.WriteResult

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by eidan on 9/20/15.
 */
object Database {

	import models.JsonFormats._

    val reactiveMongoApi = Play.current.injector.instanceOf[ReactiveMongoApi]
	val collectionName = "Characters"

	def collection: JSONCollection =
        reactiveMongoApi.db.collection[JSONCollection](collectionName)

	def putCharacter(char: Character): Future[WriteResult] = {
		collection.insert(char)
	}
	
	def getCharacter(name: String): Future[Option[Character]] = {
		collection.find(Json.obj("name" -> name)).one[Character]
	}
}
