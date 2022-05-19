package fr.uge.splendor.deck;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import fr.uge.splendor.color.*;
import fr.uge.splendor.token.*;

public class TokenDeck {
  private final HashMap<Color, Integer> deck;
  
  public TokenDeck() {
    this.deck = new HashMap<Color, Integer>();
    
    Color.getTokensColorsList().stream()
                               .forEach(color -> deck.put(color, 0));
  }
 
  public void add(Map<Token, Integer> tokens) {
    Objects.requireNonNull(tokens, "You have to add at least 1 token, your list cannot be null!");
    
    tokens.forEach((token, number) -> deck.merge(token.color(), number, Integer::sum));
  }

  
  public void remove(Map<Color, Integer> tokens) {
    Objects.requireNonNull(tokens, "Your list of tokens to remove cannot be null");
    
    tokens.forEach((token, number) -> {
      
      if (number > 0 && number <= deck.get(token)) {
        deck.computeIfPresent(token, (key, oldValue) -> oldValue - number);
      } else {
        throw new IllegalArgumentException("You're trying to withdraw too much or too less from your TokenDeck!");
      }
      
    });
  }
  
  
  public HashMap<Color, Integer> getDeckSummary() {
    var res = new HashMap<Color, Integer>();
    
    deck.forEach((color, amount) -> res.merge(color, amount, Integer::sum));
    
    return res;    
  }
  
   
  
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

  
  private String valuesToString() {
    var sb = new StringBuilder();
    
    Color.getTokensColorsList().forEach(color -> {
      
      sb.append("│     ").append(deck.get(color)).append("     ");
    });
    
    sb.append("│\n");
    return sb.toString();
  }
  
  
  private String lineToString(String start, String mid, String end) {
    var sb = new StringBuilder(start);
    sb.append("───────────");
    
    for (var i = deck.size() - 1; i > 0; i--) {
      sb.append(mid).append("───────────");
    }
    
    sb.append(end).append("\n");
    return sb.toString(); 
  }
  
  
  @Override
  public String toString() {    
    var sb = new StringBuilder();
    
    sb.append(lineToString("┌", "┬", "┐"))
      .append(colorsToString())
      .append(lineToString("├", "┼", "┤"))
      .append(valuesToString())
      .append(lineToString("└", "┴", "┘"));
    
    return sb.toString();
  }


}
