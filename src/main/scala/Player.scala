package main.scala

import java.util.Scanner

import akka.actor._
import Messages._

class Player(val name:String) extends Actor with ActorLogging{
  override def receive = {
    case HitOrStand(total, dealersTotal) =>
    {
      print("Hit or Stand : ")
      val s = new Scanner(System.in)
      val hitOrStand = s.next
      hitOrStand match {
        case "Hit" => sender ! Hit
        case "Stand" => sender ! Stand
      }
    }

    case JoinGameOfThisDealer(dealer) =>
      dealer ! JoinGame(self)

    case GameJoined(gameName) =>
       log.info(name + " joined the Game " + gameName)
  }
}