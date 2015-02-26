package blackjack

import akka.actor._
import scala.collection.mutable.Map
import scala.collection.mutable.Queue

import blackjack.Messages._

class Dealer(val gameName : String) extends Actor with ActorLogging{
  
  val scoreMap = Map.empty[ActorRef,Int] 
  val chanceQ = Queue.empty[ActorRef]
  val statusMap = Map.empty[ActorRef,String] 
  val deck = new Deck()
  var hiddenCard:Card = _
  
  override def receive = {
    case StartGame(gameName) => 
        scoreMap += (self -> 0)
        statusMap += (self -> "Playing")
        chanceQ.enqueue(self)
        deck.initialize
        println("Starting the Game " + gameName + " with " + (scoreMap.size-1) + " players!")
        self ! Play
    
    case JoinGame(player) => 
      log.info("Adding player " + player.path.name + "to Game " + gameName )
      scoreMap += (player -> 0)
      statusMap += (player -> "Playing")
      chanceQ.enqueue(player)
      player ! GameJoined(gameName)
      
    case LeaveGame(player) =>
      scoreMap -= player
      log.info("Removing player" + player + "from the Game #" + gameName)
    
    case Play =>
      for(i<- 0 to (2*chanceQ.size)-2){
              val currentPlayer = chanceQ.dequeue()
              val currentCard = new Card(deck.pop)
              scoreMap(currentPlayer)+=currentCard.getBlackJackValue
              log.info(currentPlayer.path.name  + " got Card "+ (i%2 + 1) +" -> " + currentCard.getValue +" of "+currentCard.getSuit)  
              chanceQ.enqueue(currentPlayer)  
        }
       chanceQ.enqueue(chanceQ.dequeue)
       hiddenCard = new Card(deck.pop)
       self ! Begin
       //println(scoreMap)
       //println(chanceQ)
    
    case Begin =>
      if(!chanceQ.isEmpty){
          val player = chanceQ.dequeue
          if(player == self)
          {
            scoreMap(self)+=hiddenCard.getBlackJackValue
            log.info(self.path.name + "'s hidden card was " + hiddenCard.getValue + " of " + hiddenCard.getSuit)
          }
          player ! HitOrStand(scoreMap(player),scoreMap(self))  
      }
      else
        self ! ShowStatus
      
    case Hit =>
      val currentCard = new Card(deck.pop)
      log.info(sender.path.name + " gets card -> " + currentCard.getValue +" of "+ currentCard.getSuit)
      scoreMap(sender)+=currentCard.getBlackJackValue
      if(scoreMap(sender)>21)
        {
          log.info(sender.path.name + " is busted!")
          statusMap(sender) = "Busted"
          sender ! Busted
          self ! Begin
        }
      else
        sender ! HitOrStand(scoreMap(sender),scoreMap(self))
      
    case Stand =>
      log.info(sender.path.name + " is standing with " + scoreMap(sender))
      self ! Begin
      
    case HitOrStand(total, dealersTotal) =>
      //Showing the hidden card!
      if(scoreMap(self) < 19)
        sender ! Hit
      else
        sender ! Stand
    
    case ShowStatus =>
      if(statusMap(self)=="Busted")
      {
        scoreMap.foreach {
        case(player,total) => 
            if(statusMap(player)=="Playing")
              statusMap(player) = "Won"
        }
        statusMap.foreach {
        case(player,status) =>
          log.info(player.path.name + " " + status)
        } 
      }
      else
      {
         scoreMap.foreach {
        case(player,total) => 
          if(total > scoreMap(self) && total <= 21)
              statusMap(player) = "Won"
          else if(total == scoreMap(self))
            statusMap(player) = "Draw"
          else
          {
            if(statusMap(player)=="Playing")
              statusMap(player) = "Lost"
          }
            
      }
      statusMap.foreach {
        case (player,status) =>
          log.info(player.path.name + " " + status)
      } 
      }  
      
      
    case _ => println("Code properly! No case matching")
  }
}