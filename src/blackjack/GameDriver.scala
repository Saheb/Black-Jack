package blackjack
import akka.actor._
import Messages._

object GameDriver {
  def main(args : Array[String])
  {
    println("Lets create the game Black Jack using Scala and Akka!")
    val blackjack = ActorSystem("Black_Jack")
    val dealer = blackjack.actorOf(Props(classOf[Dealer],"Game#1"),"Dealer")
    val player1 = blackjack.actorOf(Props(classOf[Player],"Saheb"),"Saheb")
    val player2 = blackjack.actorOf(Props(classOf[Player],"Neel"),"Neel")
    player1 ! JoinGameOfThisDealer(dealer)
    player2 ! JoinGameOfThisDealer(dealer)
    Thread.sleep(1000)
    dealer ! StartGame("Game#1")
  }
}