package fr.uge.splendor.action;

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
   * This method returns a String describing the action of buying a card from the board.
   * 
   * @return - The String describing the action of buying a card from the board.
   */
  @Override
  public String toString() {
    return "Buy a card from the board.";
  }
}
