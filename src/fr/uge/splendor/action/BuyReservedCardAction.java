package fr.uge.splendor.action;

/**
 * This record represents the action of buying a reserved card.
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
 */
public record BuyReservedCardAction() implements Action {  
  
  /**
   * This method returns the type (ActionType) of the action of buying a reserved card.
   * 
   * @return - ActionType describing the action of buying a reserved card.
   */
  @Override
  public ActionType type() {
    return ActionType.BUY_RESERVED_CARD;
  }
  
  /**
   * This method returns a String describing the action of buying a reserved card.
   * 
   * @return - The String describing the action of buying a reserved card.
   */
  @Override
  public String toString() {
    return "Buy a reserved card.";
  }
}
