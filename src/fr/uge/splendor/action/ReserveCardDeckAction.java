package fr.uge.splendor.action;

/**
 * This record represents the action of reserving a card from a deck.
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
 */
public record ReserveCardDeckAction() implements Action {  
  
  /**
   * This method returns the type (ActionType) of reserving a card from a deck.
   * 
   * @return - ActionType describing the action of reserving a card from a deck.
   */
  @Override
  public ActionType type() {
    return ActionType.RESERVE_CARD_DECK;
  }
  
  /**
   * This method returns a String describing the action of reserving a card from a deck.
   * 
   * @return - The String describing the action of reserving a card from a deck.
   */
  @Override
  public String toString() {
    return "Reserve a card from a deck.";
  }
}
