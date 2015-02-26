package blackjack
import akka.actor._

object Messages {
    // To Dealer from Game Driver
    case class StartGame(val game_name:String)
    
    // To Dealer from Player
    case class JoinGame(val player:ActorRef)
    case class LeaveGame(val player:ActorRef)
    case object Hit
    case object Stand
    
    // To Player from Game Driver
    case class JoinGameOfThisDealer(val dealer : ActorRef)
    
    // To Player from Dealer
    case class GameJoined(val gameName : String)
    case class HitOrStand(val total : Int, val dealersTotal : Int)
    case object Busted
    
    // Dealer to Dealer
    case object Play
    case object Begin
    case object ShowStatus
    case class InitializeDeck(val noOfDecks:Integer)
    case class ShuffleDeck(val deckId :Integer)
    case class GetCards(val numOfCards : Integer)
    
}