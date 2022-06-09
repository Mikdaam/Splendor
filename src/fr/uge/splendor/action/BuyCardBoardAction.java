package fr.uge.splendor.action;

import java.util.Objects;

import fr.uge.splendor.game.Game;
import fr.uge.splendor.game.GameData;
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
  public boolean apply(int playerId, GameData gameData) {
    Objects.requireNonNull(gameData, "Game can't be null");
    
    var cardPosition = gameData.displayer().getCoordinates();
    
    var card = gameData.board().remove(cardPosition[0], cardPosition[1]);
    if(gameData.players()[playerId].canBuyCard(card)) {
      gameData.tokens().add(gameData.players()[playerId].buyCard(card, Utils.cardsColorsList()));
      gameData.board().add(gameData.decks()[0].removeFirstCard(), cardPosition[0], cardPosition[1]);
    } else {
      gameData.board().add(card, cardPosition[0], cardPosition[1]);
      gameData.displayer().displayActionError("You are not able to buy the Card.");
      return false;
    }
    
    return true;
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
