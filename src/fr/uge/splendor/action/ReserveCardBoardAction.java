package fr.uge.splendor.action;

import java.util.Objects;

import fr.uge.splendor.card.Coordinate;
import fr.uge.splendor.game.GameData;
import fr.uge.splendor.level.Level;

/**
 * This record represents the action of reserving a card from the board.
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 */
public record ReserveCardBoardAction() implements GameAction {
  
  /**
   * Performs the real action of reserving a card from a board
   * @param playerId - the player's id
   * @param gameData - the game's data
   * @param cardPosition - the card's position
   * @param level - the board's row level
   * @return the game's data after the action
   */
  private GameData reserveCardFromBoard(int playerId, GameData gameData, Coordinate cardPosition, Level level) {
    var card = gameData.board().remove(cardPosition);
    var tokens = gameData.tokens();
  
    if (gameData.players().get(playerId).amountOfReservedCards() < 3) {
      gameData.players().get(playerId).pushToReserved(card);
      tokens = givePlayerGoldToken(gameData, playerId);
      
      if (gameData.decks().get(level).size() > 0) { /*Check if we can push another card on the board*/
        gameData.board().push(gameData.decks().get(level).removeFirstCard(), cardPosition.row());
      }
    } else {
      gameData.board().push(card, cardPosition.row());
      gameData.displayer().displayActionError("You cannot reserve any more Card.");
      return gameData;
    }
    
    return new GameData(gameData.board(), gameData.decks(), gameData.noblesCards(), tokens, gameData.players(), gameData.displayer(), gameData.levelToInteger(), true);
  }
  
  /**
   * Performs the action of a player reserving a card from the board.
   * @param playerId - the player's ID
   * @param gameData - the game's Data
   * @return the updated game's data after the action
   */
 	@Override
 	public GameData apply(int playerId, GameData gameData) {
 	  Objects.requireNonNull(gameData, "Game can't be null");
    checkPlayerID(gameData, playerId);
     
 		 var cardPosition = gameData.displayer().getCoordinates();
    var level = Level.getLevel(cardPosition.row());
    
    if (!checkLevel(gameData, level)) {
      return gameData;
    }
    
    cardPosition = new Coordinate(gameData.levelToInteger().get(level), cardPosition.column());
    
    if (!gameData.board().canRemove(cardPosition)) {
      gameData.displayer().displayActionError("There is no card to buy here...");
      return gameData;
    }
    
    return reserveCardFromBoard(playerId, gameData, cardPosition, level);
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
