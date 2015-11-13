package models

// Use H2Driver to connect to an H2 database

import slick.driver.H2Driver.api._
import slick.lifted.{SimpleFunction, TableQuery}

import scala.concurrent.Future

/**
 * Created by eidan on 9/20/15.
 */

/**
 * Getting started: simple table definition.
 */
case class Quote(name: String, quote: String)

class Quotes(tag: Tag) extends Table[Quote](tag, "QUOTES") {
    def name = column[String]("NAME")
    def quote = column[String]("QUOTE")
    def * = (name, quote) <>(Quote.tupled, Quote.unapply)
}

object Quotes {
    val db = slick.driver.H2Driver.api.Database.forConfig("h2mem1")
    val quotes = TableQuery[Quotes]

    def init(): Unit = {
        val setup = DBIO.seq(
            quotes.schema.create,
            quotes += new Quote("Erlich", "Your muffins smell like shit and so do your ideas."),
            quotes += new Quote("Erlich", "We're walking in there with three foot cocks covered in Elvis dust."),
            quotes += new Quote("Erlich", "You just brought piss to a shit fight!"),
            quotes += new Quote("Erlich", "Yeah, we're gonna win even if I have to go into the auditorium and personally jerk off every guy in the audience."),
            quotes += new Quote("Erlich", "Jared? I want you call the VC's and set up meetings with all of them. Line 'em up. Nuts to butts."),
            quotes += new Quote("Gavin", "I don't know about you people, but I don't want to live in a world where someone else makes the world a better place better than we do."),
            quotes += new Quote("Gavin", "It's weird. They always travel in groups of five. These programmers, there's always a tall, skinny white guy; short, skinny Asian guy; fat guy with a ponytail; some guy with crazy facial hair; and then an East Asian guy. It's like they trade guys until they all have the right group."),
            quotes += new Quote("Gavin", "The greatness of human accomplishment has always been measured by size. The bigger, the better. Until now. Nanotech. Smart cars. Small is the new big. In the coming months, Hooli will deliver Nucleus, the most sophisticated compression software platform the world has ever seen. Because if we can make your audio and video files smaller, we can make cancer smaller. And hunger. And AIDS."),
            quotes += new Quote("Richard", "Jobs was a poser. He didn't even write code."),
            quotes += new Quote("Jared", "Hey! Sorry if I scared you, I know I have somewhat ghost-like features. My uncle used to say, \"You look like someone starved a virgin to death\"."),
            quotes += new Quote("Jared", "But Hooli was like an abusive spouse to me. You know, like that guy who married Julia Roberts in \"Sleeping With The Enemy\"? It was dehumanizing. But then, you, Richard, you pulled me out of the life and you gave me hope and you gave me a sense of self-worth. Like Richard Gere did to Julia Roberts in 'Pretty Woman'. Every day here has been like that shopping-spree scene. I'm putting on hats."),
            quotes += new Quote("Gilfoyle", "While you were busy minoring in gender studies and singing a capella at Sarah Lawrence, I was gaining root access to NSA servers. I was one click away from starting a second Iranian Revolution."),
            quotes += new Quote("Gilfoyle", "I prevent cross-site scripting. I monitor for DDoS attacks, emergency database rollbacks, and faulty transaction handlings. The Internet - heard of it? - transfers half a petabyte of data minute; do you have any idea how that happens? All those YouPorn ones and zeroes streaming directly to your shitty little smartphone day after day, every dipshit who shits his pants if he can't get the new dubstep Skrillex remix in under twelve seconds? It's not magic, it's talent and sweat. People like me ensure your packets get delivered unsniffed. So what do I do? I makes sure that one bad config on one key component doesn't bankrupt the entire fucking company. That's what the fuck I do."),
            quotes += new Quote("Gilfoyle", "Face it, Dinesh, you're gay for my code. You're code gay."),
            quotes += new Quote("Gilfoyle", "You'd like to fuck my code"),
            quotes += new Quote("Dinesh", "That's not really our logo, is it? It looks like a guy sucking a dick, and he's got another dick tucked behind his ear for later. Like a snack dick."),
            quotes += new Quote("Dinesh", "Hey, Jared, you know who else is Canadian? Justin Bieber, the Hitler of music."),
            quotes += new Quote("Dinesh", "I know Gilfoyle probably came in here and puked out a bunch of tech specs, three-fourths of which are total horse-shit. Did he bring up the Iranian revolution thing? Yeah, those words mean nothing. But here's a fact: I'm the only one of these clowns that can code in Java. And I write sleek performant low-overhead scala code with higher order functions that will run on anything. Period. End of sentence. So basically, I think whatever equity I get, it should reflect that I contribute more than Gilfoyle. ")

        )
        db.run(setup)
    }

    def getRandomQuote = {
        val rand = SimpleFunction.nullary[Double]("random")
        val future: Future[Seq[Quotes#TableElementType]] = db.run(quotes.sortBy(x => rand).take(1).result)
        future
    }
}

