package blackjack
import akka.actor._

case class StartGame(val game_name:String)
case class InitializeDeck(val noOfDecks:Integer)
case class ShuffleDeck(val deckId :Integer)
case class GetCards(val numOfCards : Integer)
case class JoinGameOfThisDealer(val dealer : ActorRef)

object BlackJack {
  def main(args : Array[String])
  {
    println("Lets create the game Black Jack using Scala and Akka!")
    val blackjack = ActorSystem("Black_Jack")
    val dealer = blackjack.actorOf(Props(classOf[Dealer],"Game#1"),"Dealer")
    val player1 = blackjack.actorOf(Props(classOf[Player],"Saheb"),"Saheb")
    val player2 = blackjack.actorOf(Props(classOf[Player],"Neel"),"Neel")
    player1 ! JoinGameOfThisDealer(dealer)
    player2 ! JoinGameOfThisDealer(dealer)
    Thread.sleep(2000)
    dealer ! StartGame("Game#1")
  }
}