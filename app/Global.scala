import com.mongodb.{MongoClientURI, MongoClient}
import org.mongeez.Mongeez
import org.springframework.core.io.ClassPathResource
import play.api._

/**
 * Created by eidan on 9/21/15.
 */
object Global extends GlobalSettings {

	override def onStart(app: Application) {
		super.onStart(app)
		Logger.info("Application has started")

		runMongeez()
	}

	override def onStop(app: Application) {
		super.onStop(app)
		Logger.info("Application shutdown...")
	}

	def runMongeez() = {
		Logger.info(sys.props.toString())

		val mongoUri = sys.props("MONGOLAB_URI")
		Logger.info("mongoUri = " + mongoUri)

		val mongeez = new Mongeez
		mongeez.setFile(new ClassPathResource("mongeez/mongeez.xml"))
		mongeez.setMongo(new MongoClient(new MongoClientURI(mongoUri)))
		mongeez.setDbName("siliconValleyQuotes")
		mongeez.process()
	}
}
