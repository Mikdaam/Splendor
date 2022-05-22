package fr.uge.splendor.action;

/**
 * This record represents the action of taking two tokens of the same color.
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
 */
public record TwoTokensAction() implements Action {  
  
  /**
   * This method returns the type (ActionType) of the action of taking two tokens of the same color.
   * 
   * @return - ActionType describing the action of taking two tokens of the same color.
   */
  @Override
  public ActionType type() {
    return ActionType.TWO_TOKENS;
  }
  
  /**
   * This method returns a String describing the action of taking two tokens of the same color.
   * 
   * @return - The String describing the action of taking two tokens of the same color.
   */
  @Override
  public String toString() {
    return "Take two tokens of the same color.";
  }
}
