package fr.uge.splendor.action;

/**
 * This record represents the action of taking three tokens of different colors each.
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
 */
public record ThreeTokensAction() implements Action {  
  
  /**
   * This method returns the type (ActionType) of the action of taking three tokens of different colors each.
   * 
   * @return - ActionType describing the action of taking three tokens of different colors each.
   */
  @Override
  public ActionType type() {
    return ActionType.THREE_TOKENS;
  }
  
  /**
   * This method returns a String describing the action of taking three tokens of different colors each.
   * 
   * @return - The String describing the action of taking three tokens of different colors each.
   */
  @Override
  public String toString() {
    return "Take three tokens of different colors each.";
  }
}
