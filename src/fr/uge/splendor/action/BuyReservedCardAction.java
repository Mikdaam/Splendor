package fr.uge.splendor.action;

import java.util.Objects;

import fr.uge.splendor.card.Coordinate;
import fr.uge.splendor.game.GameData;
import fr.uge.splendor.utils.Utils;

/**
 * This record represents the action of buying a reserved card.
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
 */
public record BuyReservedCardAction() implements Action {  
    
  @Override
	public GameData apply(int playerId, GameData gameData) {
  	Objects.requireNonNull(gameData, "Game can't be null");
    
    var cardPosition = gameData.displayer().getCoordinates();
    cardPosition = new Coordinate(0, cardPosition.column());
    var card = gameData.players().get(playerId).removeFromReserved(cardPosition);
    
    var tokens = gameData.tokens();
    
    if (gameData.players().get(playerId).canBuyCard(card)) {      
      tokens = gameData.tokens().add(gameData.players().get(playerId).buyCard(card, Utils.cardsColorsList()));
    } else {
      gameData.players().get(playerId).pushToReserved(card);
      gameData.displayer().displayActionError("You are not able to buy the Card.");
      return gameData;
    }
    
    return new GameData(gameData.board(), gameData.decks(), gameData.noblesCards(), tokens, gameData.players(), gameData.displayer(), gameData.levelToInteger(), true);
	}
  
  /**
   * This method returns a String describing the action of buying a reserved card.
   * 
   * @return - The String describing the action of buying a reserved card.
   */
  @Override
  public String toString() {
    return "Buy a reserved card.";
  }
}
