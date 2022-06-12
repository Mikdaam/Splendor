package fr.uge.splendor.action;

import java.util.Objects;

import fr.uge.splendor.game.GameData;
import fr.uge.splendor.level.Level;

/**
 * This record represents the action of reserving a card from a deck.
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
 */
public record ReserveCardDeckAction() implements GameAction {  
  
  /**
   * This method returns the type (ActionType) of reserving a card from a deck.
   * 
   * @return - ActionType describing the action of reserving a card from a deck.
   */
  @Override
  public ActionType type() {
    return ActionType.RESERVE_CARD_DECK;
  }
  
  
  @Override
	public GameData apply(int playerId, GameData gameData) {
  	Objects.requireNonNull(gameData, "Game can't be null");
    
  	var level = gameData.displayer().getDeckLevel();
  	
  	if (!checkLevel(gameData, level)) {
     return gameData;
   }
  	
   var card = gameData.decks().get(level).removeFirstCard();
   var tokens = gameData.tokens();
   
   if (gameData.players().get(playerId).numberOfReservedCards() < 3) {
   	 gameData.players().get(playerId).pushToReserved(card);
     tokens = givePlayerGoldToken(gameData, playerId);
   } else {
    	gameData.decks().get(level).add(card);
    	gameData.displayer().displayActionError("You cannot reserve any more Card.");
     return gameData;
   }
   
   return new GameData(gameData.board(), gameData.decks(), gameData.noblesCards(), tokens, gameData.players(), gameData.displayer(), gameData.levelToInteger(), true);
	}
  
  /**
   * This method returns a String describing the action of reserving a card from a deck.
   * 
   * @return - The String describing the action of reserving a card from a deck.
   */
  @Override
  public String toString() {
    return "Reserve a card from a deck.";
  }
}
