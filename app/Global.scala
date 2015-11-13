import models.Quotes
import play.api._

/**
 * Created by eidan on 9/21/15.
 */
object Global extends GlobalSettings {

    def init(): Unit = {
        Quotes.init()
    }

    override def onStart(app: Application) {
        super.onStart(app)
        Logger.info("Application has started")

        init()
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
}
