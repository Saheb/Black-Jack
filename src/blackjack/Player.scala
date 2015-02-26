package blackjack
import akka.actor._

sealed class Action
case class HitOrStand(val total : Int, val dealersTotal : Int) extends Action
case object Hit extends Action
case object Stand extends Action
case class GameJoined(val gameName : String) extends Action

class Player(val name:String) extends Actor with ActorLogging{
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