package fr.uge.splendor.deck;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import fr.uge.splendor.color.Color;

/**
 * This class represents a TokenPurse.
 * It's represented by a HashMap that has Colors as keys and Integers as values.
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
 *
 */
public class TokenPurse {
  private final HashMap<Color, Integer> deck;
  
  /**
   * This constructor creates the HashMap that describes a TokenPurse and initializes it
   * with all the colors and zero tokens in it.
   */
  public TokenPurse() {
    this.deck = new HashMap<Color, Integer>();
    
    /*Color.getTokensColorsList().stream()
                               .forEach(color -> deck.put(color, 0));*/
  }
 
  /**
   * This method adds some tokens to a TokenPurse.
   * 
   * @param tokens - The HashMap describing the tokens to add to the TokenPurse.
   */
  public TokenPurse add(TokenPurse tokens) {
    Objects.requireNonNull(tokens, "You have to add at least 1 token, your map cannot be null!");
    
    var res = new TokenPurse();
    deck.forEach((color, number) -> res.deck.put(color, number));
    
    tokens.deck.forEach((color, number) -> res.deck.merge(color, number, Integer::sum));
    
    return res;
  }
  
  public TokenPurse addToken(Color color, int number) {
    Objects.requireNonNull(color);
    var res = (new TokenPurse()).add(this);
    
    res.deck.merge(color, number, Integer::sum);
    
    return res;    
  }

  /**
   * This method removes some tokens from a TokenPurse.
   * 
   * @param tokens - The HashMap describing the tokens to remove from the TokenPurse.
   */
  public TokenPurse remove(TokenPurse tokens) {
    Objects.requireNonNull(tokens, "Your map of tokens to remove cannot be null");
    
    var res = new TokenPurse();
    deck.forEach((color, number) -> res.deck.put(color, deck.get(color)));
    
    tokens.deck.forEach((color, number) -> {
      if (number >= 0 && number <= deck.getOrDefault(color, 0)) {
        res.deck.merge(color, -number, Integer::sum);
        //deck.computeIfPresent(color, (key, oldValue) -> oldValue - number);
      } else {
        throw new IllegalArgumentException("You're trying to withdraw too much or too less from your TokenPurse!");
      }
      
    });
    
    return res;
  }
  
  /**
   * This method creates the summary of a TokenPurse and computes it into an HashMap.
   * 
   * @return The HashMap describing the TokenPurse
   */
  public TokenPurse getDeckSummary() {
    var res = new TokenPurse();
    
    deck.forEach((color, amount) -> res.deck.merge(color, amount, Integer::sum));
    
    return res;    
  }
  
  
  public int numberOfTokens() {
    int res = 0;
    
    for (int value : deck.values()) {
      res += value;
    }
     
    return res;
  }
  
  /**
   * This method returns the number of tokens associated to a color.
   * 
   * @param color - The color from which we want to get the number of tokens.
   * @return The number of tokens associated with this color
   */
  public int getColorNumber(Color color) {
    return deck.getOrDefault(color, 0);
  }
  
  /**
   * This method remove a given color from the TokenPurse.
   * 
   * @param color
   */
  public TokenPurse removeColor(Color color) {
      var res = new TokenPurse();
      
      res = res.add(this);
		  res.deck.remove(color);
		  
		  return res;
	 }
  
  /**
   * This method computes a TokenPurse's colors into a row for the TokenPurse's String format.
   * 
   * @return The colors computed into a row for the TokenPurse's String.
   */
  private String colorsToString() {
    var sb = new StringBuilder();
    
    deck.keySet().forEach(color -> {
      var colorText = color.toString();
      sb.append("│ ").append(colorText);
      
      for (var i = 10 - colorText.length(); i > 0; i--) {
        sb.append(" ");
      }
    });
    
    sb.append("│\n");
    return sb.toString();
  }

  /**
   * This method computes a TokenPurse's values (the number of tokens for each Color) into a row for
   * the TokenPurse's String format.
   * 
   * @return The values computed into a row for the TokenPurse's String.
   */
  private String valuesToString() {
    var sb = new StringBuilder();
    
    deck.keySet().forEach(color -> {
      
      sb.append("│     ").append(deck.get(color)).append("     ");
    });
    
    sb.append("│\n");
    return sb.toString();
  }
  
  /**
   * This method creates a full row for a TokenPurse's String format.
   * It takes in parameters a start, mid and end character to match the line's position.
   * 
   * @param start - The row's starting character
   * @param mid - The row's mid character (between all cells)
   * @param end - The row's ending character
   * @return A TokenPurse's row computed with the given characters.
   */
  private String rowToString(String start, String mid, String end) {
    var sb = new StringBuilder(start);
    sb.append("───────────");
    
    for (var i = deck.size() - 1; i > 0; i--) {
      sb.append(mid).append("───────────");
    }
    
    sb.append(end).append("\n");
    return sb.toString(); 
  }
  
  /**
   * This method returns the data of the TokenPurse into a fancy String format.
   * 
   * @return The TokenPurse computed into a String
   */
  public String toString() {    
    var sb = new StringBuilder();
    
    sb.append(rowToString("┌", "┬", "┐"))
      .append(colorsToString())
      .append(rowToString("├", "┼", "┤"))
      .append(valuesToString())
      .append(rowToString("└", "┴", "┘"));
    
    return sb.toString();
  }
}
