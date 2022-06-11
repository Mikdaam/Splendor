package fr.uge.splendor.action;

import java.util.Objects;

import fr.uge.splendor.game.GameData;
import fr.uge.splendor.level.Level;

/**
 * This record represents the action of reserving a card from the board.
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
 */
public record ReserveCardBoardAction() implements Action {  
  
  /**
   * This method returns the type (ActionType) of the action of reserving a card from the board.
   * 
   * @return - ActionType describing the action of reserving a card from the board.
   */
  @Override
  public ActionType type() {
    return ActionType.RESERVE_CARD_BOARD;
  }
  
	@Override
	public GameData apply(int playerId, GameData gameData) {
		Objects.requireNonNull(gameData, "Game can't be null");
    
    var cardPosition = gameData.displayer().getCoordinates();
    
    if(!checkCoordinates(gameData, cardPosition, playerId, true)) {
      return gameData;
    }
    
    int level = cardPosition.row();
    var card = gameData.board().remove(cardPosition);
		
    if (gameData.players().get(playerId).numberOfReservedCards() < 3) {
    	gameData.players().get(playerId).pushToReserved(card);
      givePlayerGoldToken(gameData, playerId);
      gameData.board().add(gameData.decks().get(Level.getLevel(level)).removeFirstCard(), cardPosition);
    } else {
    	gameData.board().add(card, cardPosition);
    	gameData.displayer().displayActionError("You cannot reserve any more Card.");
      return gameData;
    }
    
    return new GameData(gameData.board(), gameData.decks(), gameData.noblesCards(), gameData.tokens(), gameData.players(), gameData.displayer(), true);
	}
  
  /**
   * This method returns a String describing the action of reserving a card from the board.
   * 
   * @return - The String describing the action of reserving a card from the board.
   */
  @Override
  public String toString() {
    return "Reserve a card from the board";
  }
}
