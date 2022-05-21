package fr.uge.splendor.action;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import fr.uge.splendor.color.Color;
import fr.uge.splendor.level.Level;
import fr.uge.splendor.token.Token;

public class ActionResult {
  
  /*
   * To take 2/3 Tokens : HashMap Color, Integer
   * To buy a card : Board / Reserved + Position
   * To reserve a card : Deck / Board + Position
   */
  
  /*This is the HashMap for the actions related to taking some tokens*/
  private HashMap<Color, Integer> tokens;
  
  /*This is to buy a card*/
  private int row;
  private int col;
  private boolean onBoard;
  
  
  /*This is for reservations only*/
  private Level deck;
  
  
  public ActionResult() {
    this.tokens = new HashMap<>();
  }
  
  public ActionResult(List<Token> tokens) {
    this();
    tokens.forEach(token -> this.tokens.merge(token.color(), 1, Integer::sum));
  }
  
  public ActionResult(int row, int col, boolean onBoard) {
    this();
    
    if (row < 0 || col < 0) {
      throw new IllegalArgumentException("Your position in the board is incorrect");
    }
    
    this.row = row;
    this.col = col;
    this.onBoard = onBoard;
  }
  
  public ActionResult(Level level) {
    this();
    
    Objects.requireNonNull(level);
    deck = level;
  }
}
