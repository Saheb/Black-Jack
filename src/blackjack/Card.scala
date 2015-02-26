package blackjack

// Range of id is from 0 to 51
class Card(val id : Int) {
    
    val suitMap : Map[Int,String] = Map(0 -> "Hearts", 1-> "Clubs", 2-> "Diamonds", 3 -> "Spades")
    
    val valueMap : Map[Int, String] = Map(10 -> "J", 11 -> "Q", 12 -> "K") 
    
    def getId = id
    
    def getSuit = suitMap.getOrElse(id/13,None)
    
    def getValue = {
      if(id % 13 < 10)
        (id%13 + 1).toString()
      else 
        valueMap.getOrElse(id%13, None)
    }
   
    def getBlackJackValue = {
      if(id % 13 < 10)
        (id % 13) + 1
      else
        10
    }
}

object Card extends App {
  for(i<- 0 to 51){
      val card = new Card(i)
      println(i + "=> Suit = " + card.getSuit + " and Value = " + card.getValue )
  } 
}