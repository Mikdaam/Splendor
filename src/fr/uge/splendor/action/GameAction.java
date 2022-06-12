package fr.uge.splendor.action;

import fr.uge.splendor.card.Coordinate;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.TokenPurse;
import fr.uge.splendor.game.GameData;
import fr.uge.splendor.level.Level;

/**
 * This class represents an action to perform in the game.
 * It has been created to display the actions correctly.
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
 *
 */
public interface GameAction {
	 
	 /**
	  * Apply an action to the game
	  * 
	  * @param game
	  */
	 GameData apply(int playerId, GameData gameData);
	 
	 
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
	 
	 
	 default boolean checkLevel(GameData gameData, Level level) {
	   if (level.equals(Level.UNKNOWN)) {
	     gameData.displayer().displayActionError("This level does not exist");
	     return false;
	   }
	   
	   return true;
	 }
	 
	 default TokenPurse givePlayerGoldToken(GameData gameData, int playerId) {
	   var tokens = gameData.tokens();
		  if (gameData.tokens().getColorNumber(Color.GOLD) > 0) {
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
	    if (gameData.tokens().getColorNumber(color) <= 0) {
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
