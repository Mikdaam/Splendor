package fr.uge.splendor.action;

import java.util.Objects;

import fr.uge.splendor.deck.TokenPurse;
import fr.uge.splendor.game.GameData;

/**
 * This record represents the action of buying a card from the board where the game takes place.
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
 */
public record GiveBackTokensAction() implements GameAction {  
  
  /**
   * TODO: find a better way to pass the player id;
   * TODO: Make Yunen change ..... (add variable which contains possible colors ) ????
   */
  @Override
  public GameData apply(int playerId, GameData gameData) {
    Objects.requireNonNull(gameData, "Game can't be null");
    
    var tokens = gameData.tokens();
    var player = gameData.players().get(playerId);
    gameData.displayer().displayActionError("You have more than 10 tokens, please give back some tokens...");
    
    while (player.getNumberOfTokens() > 10) {
      var color = gameData.displayer().getUniqueColor();
      var number = player.removeTokens(color, 1);
      tokens = tokens.add(new TokenPurse().addToken(color, number));
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
