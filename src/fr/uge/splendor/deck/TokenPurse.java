package fr.uge.splendor.deck;

import java.util.EnumMap;
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
  private final EnumMap<Color, Integer> deck;
  
  /**
   * This constructor creates the EnumMap that describes a TokenPurse and initializes it
   * with all the colors and zero tokens in it.
   */
  public TokenPurse() {
    deck = new EnumMap<Color, Integer>(Color.class);
  }
 
  
  /**
   * This method adds some tokens to a TokenPurse.
   * 
   * @param tokens - The HashMap describing the tokens to add to the TokenPurse.
   * @return A new updated TokenPurse
   */
  public TokenPurse add(TokenPurse tokens) {
    Objects.requireNonNull(tokens, "You have to add at least 1 token, your map cannot be null!");
    
    var newPurse = new TokenPurse();
    deck.forEach((color, number) -> newPurse.deck.put(color, number));
    
    tokens.deck.forEach((color, number) -> newPurse.deck.merge(color, number, Integer::sum));
    
    return newPurse;
  }
  
  /**
   * Adds a given amount of tokens of a given color.
   * @param color - the color fo the tokens to add
   * @param amount - the amount of tokens to add
   * @return A new updated TokenPurse
   */
  public TokenPurse addToken(Color color, int amount) {
    Objects.requireNonNull(color);
    
    if (amount < 0) {
      throw new IllegalArgumentException("You cannot add a negative amount");
    }
    
    var newPurse = (new TokenPurse()).add(this);
    newPurse.deck.merge(color, amount, Integer::sum);
    
    return newPurse;    
  }

  /**
   * This method removes some tokens from a TokenPurse.
   * 
   * @param tokens - The HashMap describing the tokens to remove from the TokenPurse.
   * @return A new updated TokenPurse
   */
  public TokenPurse remove(TokenPurse tokens) {
    Objects.requireNonNull(tokens, "Your map of tokens to remove cannot be null");
    
    var newPurse = new TokenPurse();
    deck.forEach((color, number) -> newPurse.deck.put(color, deck.get(color)));
    
    tokens.deck.forEach((color, number) -> {
      if (number >= 0 && number <= deck.getOrDefault(color, 0)) {
        newPurse.deck.merge(color, -number, Integer::sum);
      } else {
        throw new IllegalArgumentException("You're trying to withdraw too much or too less from your TokenPurse!");
      }
      
    });
    
    return newPurse;
  }
  
  /**
   * This method remove a given color from the TokenPurse.
   * 
   * @param color - The color to remove
   * @return A new updated TokenPurse
   */
  public TokenPurse removeColor(Color color) {
    Objects.requireNonNull(color);
    
    var newPurse = new TokenPurse();
    newPurse = newPurse.add(this);
    newPurse.deck.remove(color);
    
    return newPurse;
  }
  
  
  
  /**
   * Returns the amount of Tokens in the TokenPurse
   * @return The amount of Tokens in the TokenPurse
   */
  public int amountOfTokens() {
    int amount = 0;
    
    for (int value : deck.values()) {
      amount += value;
    }
     
    return amount;
  }
  
  /**
   * This method returns the amount of tokens associated to a color.
   * 
   * @param color - The color from which we want to get the number of tokens.
   * @return The number of tokens associated with this color
   */
  public int getColorAmount(Color color) {
    Objects.requireNonNull(color);
    return deck.getOrDefault(color, 0);
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
  @Override
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
