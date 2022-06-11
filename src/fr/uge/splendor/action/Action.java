package fr.uge.splendor.action;

import fr.uge.splendor.game.Game;
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
	 boolean apply(int playerId, GameData gameState);
	 
	 /**
	  * This method returns a String describing the action's effect.
	  * 
	  * @return a String describing the action's effect.
	  */
	 String toString();
}
