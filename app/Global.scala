import com.mongodb.{MongoClientURI, MongoClient}
import org.mongeez.Mongeez
import org.springframework.core.io.ClassPathResource
import play.api._

/**
 * Created by eidan on 9/21/15.
 */
object Global extends GlobalSettings {

	val mongolabUri: String = "MONGOLAB_URI"
	val mongoDBName: String = "MONGO_DB_NAME"

	override def onStart(app: Application) {
		super.onStart(app)
		Logger.info("Application has started")

		runMongeez()
	}

	override def onStop(app: Application) {
		super.onStop(app)
		Logger.info("Application shutdown...")
	}

	def getProperty(key: String): String = {
		var prop = sys.props(key)
		if(prop == null || prop.isEmpty) {
			prop = sys.env(key)
		}
		Logger.info(key + " = " + prop)
		prop
	}

	def runMongeez() = {
		Logger.info(sys.props.toString())

		val mongoUri = getProperty(mongolabUri)
		val dbName = getProperty(mongoDBName)

		val mongeez = new Mongeez
		mongeez.setFile(new ClassPathResource("mongeez/mongeez.xml"))
		mongeez.setMongo(new MongoClient(new MongoClientURI(mongoUri)))
		mongeez.setDbName(dbName)
		mongeez.process()
	}
}
