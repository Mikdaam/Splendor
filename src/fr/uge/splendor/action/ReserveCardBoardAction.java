package fr.uge.splendor.action;

import java.util.Objects;

import fr.uge.splendor.card.Coordinate;
import fr.uge.splendor.game.GameData;
import fr.uge.splendor.level.Level;

/**
 * This record represents the action of reserving a card from the board.
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
 */
public record ReserveCardBoardAction() implements GameAction {
  
	@Override
	public GameData apply(int playerId, GameData gameData) {
		 Objects.requireNonNull(gameData, "Game can't be null");
    
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
   
   var card = gameData.board().remove(cardPosition);
   var tokens = gameData.tokens();
	
   if (gameData.players().get(playerId).numberOfReservedCards() < 3) {
   	 gameData.players().get(playerId).pushToReserved(card);
     tokens = givePlayerGoldToken(gameData, playerId);
     gameData.board().push(gameData.decks().get(level).removeFirstCard(), cardPosition.row());
   } else {
   	 gameData.board().push(card, cardPosition.row());
   	 gameData.displayer().displayActionError("You cannot reserve any more Card.");
     return gameData;
   }
   
   return new GameData(gameData.board(), gameData.decks(), gameData.noblesCards(), tokens, gameData.players(), gameData.displayer(), gameData.levelToInteger(), true);
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
