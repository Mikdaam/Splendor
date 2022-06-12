package fr.uge.splendor.action;

import java.util.Objects;

import fr.uge.splendor.game.GameData;

public record QuitAction() implements GameAction {
  /**
   * Performs the action of a player buying a card from the board.
   * @param playerId - the player's ID
   * @param gameData - the game's Data
   * @return the updated game's data after the action
   */
  @Override
  public GameData apply(int playerId, GameData gameData) {
    Objects.requireNonNull(gameData, "Game can't be null");
    checkPlayerID(gameData, playerId);
    
    /* The action is done in the chooseAction method in Game  */
    
    return gameData;
  }
  
  /**
   * This method returns a String describing the action of buying a card from the board.
   * 
   * @return - The String describing the action of buying a card from the board.
   */
  @Override
  public String toString() {
    return "Press 'q' to quit the game.";
  }
}
