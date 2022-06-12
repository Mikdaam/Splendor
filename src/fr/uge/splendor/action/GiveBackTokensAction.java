package fr.uge.splendor.action;

import java.util.Objects;

import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.TokenPurse;
import fr.uge.splendor.game.GameData;

/**
 * This record represents the action of buying a card from the board where the game takes place.
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 */
public record GiveBackTokensAction() implements GameAction {  
  
  /**
   * Performs the action of a player giving back some tokens until they have ten in total.
   * @param playerId - the player's ID
   * @param gameData - the game's Data
   * @return the updated game's data after the action
   */
  @Override
  public GameData apply(int playerId, GameData gameData) {
    Objects.requireNonNull(gameData, "Game can't be null");
    checkPlayerID(gameData, playerId);
    
    var tokens = gameData.tokens();
    var player = gameData.players().get(playerId);
    
    while (player.getAmountOfTokens() > 10) {
      gameData.displayer().displayActionError("You have more than 10 tokens, please give back some tokens...");
      
      var color = gameData.displayer().getUniqueColor();
      if (color.equals(Color.UNKNOWN)) {
        gameData.displayer().displayActionError("Unknown Token...");
        continue;
      }
      /*Adding back to the game's tokens the token that the player gave back*/
      tokens = tokens.add(new TokenPurse().addToken(color, player.removeTokens(color, 1))); 
    }
        
    return new GameData(gameData.board(), gameData.decks(), gameData.noblesCards(), tokens, gameData.players(), gameData.displayer(), gameData.levelToInteger(), true);
  }
  
  /**
   * This method returns a String describing the action of buying a card from the board.
   * 
   * @return - The String describing the action of buying a card from the board.
   */
  @Override
  public String toString() {
    return "Give back tokens until you have 10 left.";
  }
}
