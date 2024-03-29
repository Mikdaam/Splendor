package fr.uge.splendor.action;

import java.util.Objects;

import fr.uge.splendor.card.Coordinate;
import fr.uge.splendor.game.GameData;
import fr.uge.splendor.utils.Utils;

/**
 * This record represents the action of buying a reserved card.
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 */
public record BuyReservedCardAction() implements GameAction {  
  
  /**
   * Performs the real action of buying a reserved card.
   * @param playerId - the player's ID
   * @param gameData - the game's data
   * @param cardPosition - the card's position
   * @return the game's data after the action
   */
  private static GameData buyReservedCard(int playerId, GameData gameData, Coordinate cardPosition) {
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
   * Performs the action of a player buying a card from their reserved cards.
   * @param playerId - the player's ID
   * @param gameData - the game's Data
   * @return the updated game's data after the action
   */
  @Override
 	public GameData apply(int playerId, GameData gameData) {
    Objects.requireNonNull(gameData, "Game can't be null");
    checkPlayerID(gameData, playerId);
     
    var cardPosition = gameData.displayer().getCoordinates();
    cardPosition = new Coordinate(0, cardPosition.column());
    
    if (!gameData.players().get(playerId).reservedCards().canRemove(cardPosition)) {
      gameData.displayer().displayActionError("There is no card to buy here...");
      return gameData;
    }
    
    return buyReservedCard(playerId, gameData, cardPosition);
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
