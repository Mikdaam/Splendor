package fr.uge.splendor.action;

import java.util.Objects;

import fr.uge.splendor.game.GameData;
import fr.uge.splendor.level.Level;

/**
 * This record represents the action of reserving a card from a deck.
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 */
public record ReserveCardDeckAction() implements GameAction {    
  
  /**
   * Performs the real action of reserving a card from a deck
   * @param playerId - the player's id
   * @param gameData - the game's data
   * @param level - the deck's level
   * @return the game's data after the action
   */
  private GameData reserveCardFromDeck(int playerId, GameData gameData, Level level) {
    var card = gameData.decks().get(level).removeFirstCard();
    var tokens = gameData.tokens();
    
    if (gameData.players().get(playerId).amountOfReservedCards() < 3) {
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
   * Performs the action of a player reserving a card from on of the game's decks.
   * @param playerId - the player's ID
   * @param gameData - the game's Data
   * @return the updated game's data after the action
   */
  @Override
 	public GameData apply(int playerId, GameData gameData) {
    Objects.requireNonNull(gameData, "Game can't be null");
    checkPlayerID(gameData, playerId);
     
   	var level = gameData.displayer().getDeckLevel();
   	
   	if (!checkLevel(gameData, level) || gameData.decks().get(level).size() == 0) {
   	  gameData.displayer().displayActionError("There is no card to reserve here...");
      return gameData;
    }
   	
    return reserveCardFromDeck(playerId, gameData, level);
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
