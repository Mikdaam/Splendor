package fr.uge.splendor.action;

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
