package main.scala

import akka.actor._
import Messages._
import java.util.Scanner
object GameDriver {
  def main(args : Array[String])
  {
    println("Lets play the game Black Jack")
    print("Enter your name : ")
    val s = new Scanner(System.in)
    val name = s.next
    val blackjack = ActorSystem("Black_Jack")
    val dealer = blackjack.actorOf(Props(classOf[Dealer],"Game#1"),"Dealer")
    //val player1 = blackjack.actorOf(Props(classOf[Player],"Saheb"),"Saheb")
    //val player2 = blackjack.actorOf(Props(classOf[Player],"Neel"),"Neel")
    val consolePlayer = blackjack.actorOf(Props(classOf[Player],name),name)
    //player1 ! JoinGameOfThisDealer(dealer)
    //player2 ! JoinGameOfThisDealer(dealer)
    consolePlayer ! JoinGameOfThisDealer(dealer)
    Thread.sleep(1000)
    dealer ! StartGame("Game#1")
    Thread.sleep(10000)
    //blackjack.shutdown()
  }
}