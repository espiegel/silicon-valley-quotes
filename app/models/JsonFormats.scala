package models

/**
 * Created by eidan on 9/21/15.
 */
object JsonFormats {
    import play.api.libs.json.Json

    implicit val quoteFormat = Json.format[Quote]
}
