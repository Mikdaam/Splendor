package fr.uge.splendor.action;

import fr.uge.splendor.deck.TokenPurse;
import fr.uge.splendor.game.GameData;

/**
 * This record represents the action of taking two tokens of the same color.
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
 */
public record TwoTokensAction() implements GameAction {  
  
  /**
   * This method returns the type (ActionType) of the action of taking two tokens of the same color.
   * 
   * @return - ActionType describing the action of taking two tokens of the same color.
   */
  @Override
  public ActionType type() {
    return ActionType.TWO_TOKENS;
  }
  
  @Override
	public GameData apply(int playerId, GameData gameData) {
		var color = gameData.displayer().getUniqueColor();
    if (!checkPlayersTokensNumber(gameData, playerId) || !checkTokenColor(gameData, color) || !checkTokensNumber(gameData, color)) {
      return gameData;
    }
    
    var toTake = 2;
    if (gameData.tokens().getColorNumber(color) < 4) { /*Not enough tokens to take two of them*/
      toTake = 1;
    }
    
    var given = 0; /*To check how much we gave*/
    while(given < toTake && gameData.players().get(playerId).getNumberOfTokens() < 10) {
    	gameData.players().get(playerId).takeToken(color);
      given++;
    }
    
    //System.out.println("Before : " + gameData.tokens());
    var tokens = gameData.tokens().remove((new TokenPurse()).addToken(color, given));
    //System.out.println("After : " + tokens);
    
		//gameData = new GameData(gameData.board(), gameData.decks(), gameData.noblesCards(), tokens, gameData.players(), gameData.displayer());
    
		//System.out.println("After creating a new state : " + gameData.tokens());
		
    /*if (given == 0) {
      return false;
    }*/
    
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
