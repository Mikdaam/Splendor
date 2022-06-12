package fr.uge.splendor.action;

import java.util.Objects;

import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.TokenPurse;
import fr.uge.splendor.game.GameData;

/**
 * This record represents the action of taking two tokens of the same color.
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 */
public record TwoTokensAction() implements GameAction {
  
  /**
   * Returns the amount of tokens to take for this action.
   * @param gameData - the game's data
   * @param color - the color to take the tokens from
   * @return 2 if there is enough tokens (4 or more), 1 otherwise
   */
  private static int tokensToTake(GameData gameData, Color color) {
    if (gameData.tokens().getColorAmount(color) < 4) { /*Not enough tokens to take two of them*/
      return 1;
    }
    
    return 2;
  }
  
  /**
   * Performs the action of a player taking two tokens of same color.
   * @param playerId - the player's ID
   * @param gameData - the game's Data
   * @return the updated game's data after the action
   */
  @Override
 	public GameData apply(int playerId, GameData gameData) {
    Objects.requireNonNull(gameData, "Game can't be null");
    checkPlayerID(gameData, playerId);
    
 		 var color = gameData.displayer().getUniqueColor();
    if (!checkTokenColor(gameData, color) || !checkTokensNumber(gameData, color)) {
      return gameData;
    }
    
    var toTake = tokensToTake(gameData, color);
    for (var given = 0; given < toTake; given++) {
    	 gameData.players().get(playerId).takeToken(color);
    }
    
    var tokens = gameData.tokens().remove((new TokenPurse()).addToken(color, toTake));
    return  new GameData(gameData.board(), gameData.decks(), gameData.noblesCards(), tokens, gameData.players(), gameData.displayer(), gameData.levelToInteger(), true);
 	}
  
  /**
   * This method returns a String describing the action of taking two tokens of the same color.
   * 
   * @return - The String describing the action of taking two tokens of the same color.
   */
  @Override
  public String toString() {
    return "Take two tokens of the same color.";
  }
}
