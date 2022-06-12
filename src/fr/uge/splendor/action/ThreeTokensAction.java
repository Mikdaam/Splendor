package fr.uge.splendor.action;

import java.util.List;
import java.util.Objects;

import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.TokenPurse;
import fr.uge.splendor.game.GameData;

/**
 * This record represents the action of taking three tokens of different colors each.
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 */
public record ThreeTokensAction() implements GameAction { 
  
  /**
   * This method checks, for a list of tokens' colors, if the color is well defined
   * and if there is at least 1 token of this color in the TokenDeck.
   * 
   * @param colors - the list of colors to check
   * @return true if the list is correct, false otherwise.
   */
  private boolean checkTokensColorsList(GameData gameData, List<Color> colors) {
    for (var color : colors) {
      if (!checkTokenColor(gameData, color) || !checkTokensNumber(gameData, color)) {
        return false;
      }
    }
    
    return true;
  }
  
  /**
   * This method checks if a list of colors effectively contains 3 distinct colors.
   * 
   * @param colors - the list of colors to check.
   * @return true if there is three distinct colors in the list, false otherwise.
   */
  private boolean checkThreeDistinctColors(GameData gameData, List<Color> colors) {    
    if(colors.stream().distinct().count() != 3) {
    	 gameData.displayer().displayActionError("You have not entered three distinct colors");
      return false;
    }
    
    return true;
  }
  
  /**
   * Performs the action of a player taking three tokens of different colors.
   * @param playerId - the player's ID
   * @param gameData - the game's Data
   * @return the updated game's data after the action
   */
  @Override
 	public GameData apply(int playerId, GameData gameData) {
    Objects.requireNonNull(gameData, "Game can't be null");
    checkPlayerID(gameData, playerId);
    
   	var colors = gameData.displayer().getThreeColor();
   	
     if (!checkThreeDistinctColors(gameData, colors) || !checkTokensColorsList(gameData, colors)) {
       return gameData;
     }
     
     TokenPurse tokens = gameData.tokens();
     for (var color : colors) {
     		gameData.players().get(playerId).takeToken(color);
      	tokens = tokens.remove((new TokenPurse()).addToken(color, 1));
 		  }
     
     return new GameData(gameData.board(), gameData.decks(), gameData.noblesCards(), tokens, gameData.players(), gameData.displayer(), gameData.levelToInteger(), true);
 	}
  
  /**
   * This method returns a String describing the action of taking three tokens of different colors each.
   * 
   * @return - The String describing the action of taking three tokens of different colors each.
   */
  @Override
  public String toString() {
    return "Take three tokens of different colors each.";
  }
}
