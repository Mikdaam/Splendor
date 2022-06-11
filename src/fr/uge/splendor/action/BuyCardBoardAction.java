package fr.uge.splendor.action;

import java.util.Objects;

import fr.uge.splendor.game.GameData;
import fr.uge.splendor.level.Level;
import fr.uge.splendor.utils.Utils;

/**
 * This record represents the action of buying a card from the board where the game takes place.
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
 */
public record BuyCardBoardAction() implements Action {  
  
  /**
   * This method returns the type (ActionType) of the action of buying a card from the board.
   * 
   * @return - ActionType describing the action of buying a card from the board.
   */
  @Override
  public ActionType type() {
    return ActionType.BUY_CARD_BOARD;
  }
  
  /**
   * TODO: find a better way to pass the player id;
   * TODO: Make Yunen change ..... (add variable which contains possible colors ) ????
   */
  @Override
  public GameData apply(int playerId, GameData gameData) {
    Objects.requireNonNull(gameData, "Game can't be null");
    
    var cardPosition = gameData.displayer().getCoordinates();
    var level = Level.getLevel(cardPosition.row());
    
    if (level.equals(Level.UNKNOWN) || !gameData.board().canRemove(cardPosition)) {
      gameData.displayer().displayActionError("There is no card to buy here...");
      return gameData;
    }
    
    var card = gameData.board().remove(cardPosition);
    
    if(gameData.players().get(playerId).canBuyCard(card)) {
      gameData.tokens().add(gameData.players().get(playerId).buyCard(card, Utils.cardsColorsList()));
      gameData.board().add(gameData.decks().get(level).removeFirstCard(), cardPosition);
    } else {
      gameData.board().add(card, cardPosition);
      gameData.displayer().displayActionError("You are not able to buy the Card.");
      return gameData;
    }
    
    return new GameData(gameData.board(), gameData.decks(), gameData.noblesCards(), gameData.tokens(), gameData.players(), gameData.displayer(), true);
  }
  
  /**
   * This method returns a String describing the action of buying a card from the board.
   * 
   * @return - The String describing the action of buying a card from the board.
   */
  @Override
  public String toString() {
    return "Buy a card from the board.";
  }
}
