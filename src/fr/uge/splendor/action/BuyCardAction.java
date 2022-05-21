package fr.uge.splendor.action;

import fr.uge.splendor.game.Game;

public class BuyCardAction implements Action {
  private final ActionType type;
  
  public BuyCardAction() {
    type = ActionType.BUY_CARD;
  }
  
  @Override
  public ActionType type() {
    return type;
  }

  @Override
  public void applyConsole(Game game) {
    
    /*
     * Write BOARD and Coordinates
     * Write RESERVED and Coordinates
     * 
     * */
  }

}
