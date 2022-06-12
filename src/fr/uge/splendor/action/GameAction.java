package fr.uge.splendor.action;

import java.util.Objects;

import fr.uge.splendor.card.Coordinate;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.TokenPurse;
import fr.uge.splendor.game.GameData;
import fr.uge.splendor.level.Level;

/**
 * This class represents an action to perform in the game.
 * It has been created to display the actions correctly.
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 *
 */
public interface GameAction {
	 
  /**
   * Performs a certain action that will have an effect on the game's data.
   * @param playerId - the player's ID
   * @param gameData - the game's Data
   * @return the updated game's data after the action
   */
	 GameData apply(int playerId, GameData gameData);
	 
	 /**
	  * Checks if the player's ID is valid
	  * @param gameData - the game's data
	  * @param playerId - the player's ID
	  */
	 default void checkPlayerID(GameData gameData, int playerId) {
	   if (playerId < 0 || playerId >= gameData.players().size()) {
	     throw new IllegalArgumentException("playerId " + playerId + " is out of bounds...");
	   }
	 }
	 
	 /**
	   * This method checks if the given coordinates are correct in the context of a 2D array defined by
	   * its number of rows and columns.
	   * 
	   * @param coordinates - the coordinates to check.
	   * @param rows - the rows of the array we want to use the coordinates on.
	   * @param cols - the columns of the array we want to use the coordinates on.
	   * @return true if the coordinates are correct, false otherwise
	   */
	 default boolean checkCoordinates(GameData gameData, Coordinate coordinate, int playerId, boolean isReservedCards) {
	   Objects.requireNonNull(gameData);
	   Objects.requireNonNull(coordinate);
    checkPlayerID(gameData, playerId);
    
 		 var board = (isReservedCards) ? gameData.players().get(playerId).reservedCards() : gameData.board();
 		 	   
 		 if (!board.rowIsInBoard(coordinate)) {
	  	  gameData.displayer().displayActionError("You've entered a wrong row number");
	     return false;
	   } else if (!board.colIsInBoard(coordinate)) {
	  	  gameData.displayer().displayActionError("You've entered a wrong column number");
	     return false;
	   }
	    
	   return true;
	 }
	 
	 /**
	  * Checks if the level is correct (not UNKNOWN for the game)
	  * @param gameData - the game's data
	  * @param level - the level to check
	  * @return true if the level is correct, false otherwise
	  */
	 default boolean checkLevel(GameData gameData, Level level) {
	   Objects.requireNonNull(gameData);
	   Objects.requireNonNull(level);
	   
	   if (level.equals(Level.UNKNOWN)) {
	     gameData.displayer().displayActionError("This level does not exist");
	     return false;
	   }
	   
	   return true;
	 }
	 
	 /**
	  * Perform the action of giving a gold token to a player if there is at least 1 gold token.
	  * @param gameData - the game's data
	  * @param playerId - the player's id
	  * @return the game's token purse, updated after removing a token if there is enough
	  */
	 default TokenPurse givePlayerGoldToken(GameData gameData, int playerId) {
	   Objects.requireNonNull(gameData);
	   checkPlayerID(gameData, playerId);
	   
	   var tokens = gameData.tokens();
	   
		  if (gameData.tokens().getColorAmount(Color.GOLD) > 0) {
			   gameData.players().get(playerId).takeToken(Color.GOLD);
			   tokens = gameData.tokens().remove((new TokenPurse()).addToken(Color.GOLD, 1));
		  }
		 
		  return tokens;
	 }
	 
	 /**
   * This method checks if there is at least 1 token of the given Color in the TokenDeck.
   * 
   * @param color - the color of the token we want to check on.
   * @return true if the color has at least 1 token, false otherwise.
   */
  default boolean checkTokensNumber(GameData gameData, Color color) {
    Objects.requireNonNull(gameData);
    Objects.requireNonNull(color);
    
    if (gameData.tokens().getColorAmount(color) <= 0) {
    	 gameData.displayer().displayActionError("Not enough tokens to perform the action.");
      return false;
    }
    
    return true;
  }
	  
  /**
   * This method checks if the color isn't an UNKNOWN Color.
   * 
   * @param color - the Color to check.
   * @return true if the color is not UNKNOWN, false otherwise.
   */
  default boolean checkTokenColor(GameData gameData, Color color) {
    Objects.requireNonNull(gameData);
    Objects.requireNonNull(color);
    
    if (color == Color.UNKNOWN || color == Color.GOLD) {
    	 gameData.displayer().displayActionError("Unknown Color.");
      return false;
    }
    
    return true;
  }
	  
	 /**
	  * This method returns a String describing the action's effect.
	  * 
	  * @return a String describing the action's effect.
	  */
	 String toString();
}
