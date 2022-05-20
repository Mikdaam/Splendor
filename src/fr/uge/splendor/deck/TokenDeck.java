package fr.uge.splendor.deck;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import fr.uge.splendor.color.Color;

/**
 * This class represents a TokenDeck.
 * It's represented by a HashMap that has Colors as keys and Integers as values.
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
 *
 */
public class TokenDeck {
  private final HashMap<Color, Integer> deck;
  
  /**
   * This constructor creates the HashMap that describes a TokenDeck and initializes it
   * with all the colors and zero tokens in it.
   */
  public TokenDeck() {
    this.deck = new HashMap<Color, Integer>();
    
    Color.getTokensColorsList().stream()
                               .forEach(color -> deck.put(color, 0));
  }
 
  /**
   * This method adds some tokens to a TokenDeck.
   * 
   * @param tokens - The HashMap describing the tokens to add to the TokenDeck.
   */
  public void add(Map<Color, Integer> tokens) {
    Objects.requireNonNull(tokens, "You have to add at least 1 token, your map cannot be null!");
    
    tokens.forEach((color, number) -> deck.merge(color, number, Integer::sum));
  }

  /**
   * This method removes some tokens from a TokenDeck.
   * 
   * @param tokens - The HashMap describing the tokens to remove from the TokenDeck.
   */
  public void remove(Map<Color, Integer> tokens) {
    Objects.requireNonNull(tokens, "Your map of tokens to remove cannot be null");
    
    tokens.forEach((token, number) -> {
      
      if (number > 0 && number <= deck.get(token)) {
        deck.computeIfPresent(token, (key, oldValue) -> oldValue - number);
      } else {
        throw new IllegalArgumentException("You're trying to withdraw too much or too less from your TokenDeck!");
      }
      
    });
  }
  
  /**
   * This method creates the summary of a TokenDeck and computes it into an HashMap.
   * 
   * @return The HashMap describing the TokenDeck
   */
  public HashMap<Color, Integer> getDeckSummary() {
    var res = new HashMap<Color, Integer>();
    
    deck.forEach((color, amount) -> res.merge(color, amount, Integer::sum));
    
    return res;    
  }
  
  
  
  
  /**
   * This method computes a TokenDeck's colors into a row for the TokenDeck's String format.
   * 
   * @return The colors computed into a row for the TokenDeck's String.
   */
  private String colorsToString() {
    var sb = new StringBuilder();
    
    Color.getTokensColorsList().forEach(color -> {
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
   * This method computes a TokenDeck's values (the number of tokens for each Color) into a row for
   * the TokenDeck's String format.
   * 
   * @return The values computed into a row for the TokenDeck's String.
   */
  private String valuesToString() {
    var sb = new StringBuilder();
    
    Color.getTokensColorsList().forEach(color -> {
      
      sb.append("│     ").append(deck.get(color)).append("     ");
    });
    
    sb.append("│\n");
    return sb.toString();
  }
  
  /**
   * This method creates a full row for a TokenDeck's String format.
   * It takes in parameters a start, mid and end character to match the line's position.
   * 
   * @param start - The row's starting character
   * @param mid - The row's mid character (between all cells)
   * @param end - The row's ending character
   * @return A TokenDeck's row computed with the given characters.
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
   * This method returns the data of the TokenDeck into a fancy String format.
   * 
   * @return The TokenDeck computed into a String
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
  
  /**
   * This method returns the number of tokens associated to a color.
   * 
   * @param color - The color from which we want to get the number of tokens.
   * @return The number of tokens associated with this color
   */
  public int getColorNumber(Color color) {
    return deck.getOrDefault(color, 0);
  }


}
