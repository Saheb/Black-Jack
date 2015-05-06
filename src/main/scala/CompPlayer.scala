/**
 * Created by saheb on 5/6/15.
 */

import akka.actor.{ActorLogging, Actor}
import main.scala.Messages._
import java.util.Scanner

class CompPlayer(val name : String) extends Actor with ActorLogging{
  override def receive = {
    case HitOrStand(total, dealersTotal) =>
      //some logic here to decide to hit or stop
      if(total<=16)
      {
        log.info(self.path.name + " hits!")
        sender ! Hit
      }
      else
        sender ! Stand

    case JoinGameOfThisDealer(dealer) =>
      dealer ! JoinGame(self)

    case GameJoined(gameName) =>
      log.info(name + " joined the Game " + gameName)
  }
}
