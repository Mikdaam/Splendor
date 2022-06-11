package fr.uge.splendor.action;

import fr.uge.splendor.card.Coordinate;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.TokenPurse;
import fr.uge.splendor.game.GameData;

/**
 * This class represents an action to perform in the game.
 * It has been created to display the actions correctly.
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
 *
 */
public interface Action {
  
  /**
   * This method returns the ActionType of the action.
   * 
   * @return the ActionType of the action.
   */
	 ActionType type();
	 
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
	 
	 /**
	   * This method checks if a player has 10 tokens or less.
	   * 
	   * @param playerID - the player's ID
	   * @return true if the player has less than 10 tokens, false otherwise.
	   */
	  default boolean checkPlayersTokensNumber(GameData gameData, int playerId) {
	    if (gameData.players().get(playerId).getNumberOfTokens() == 10) {
	      /*Exchange the tokens maybe?*/
	    	gameData.displayer().displayActionError("You already have 10 tokens. You cannot get more than that.");
	      return false;
	    }
	    
	    return true;
	 }
	 
	 
	 default void givePlayerGoldToken(GameData gameData, int playerId) {
		 if (gameData.tokens().getColorNumber(Color.GOLD) > 0) {
			 gameData.players().get(playerId).takeToken(Color.GOLD);
			 var tokens = gameData.tokens().remove((new TokenPurse()).addToken(Color.GOLD, 1));
			 gameData = new GameData(gameData.board(), gameData.decks(), gameData.noblesCards(), tokens, gameData.players(), gameData.displayer(), gameData.actionSucceed());
		 }
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
