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
  
  
  @Override
  public String toString() {
    var line = "------------------";
    var sb = new StringBuilder(line);
    
    sb.append("\nTOKEN DECK :\n\n");
    
    var deckSummary = this.getDeckSummary();
    deckSummary.forEach((color, cardNumber) -> {
      sb.append(color)
        .append(" : ")
        .append(cardNumber)
        .append("\n");
    });
    
    sb.append(line);
    
    return sb.toString();
  }


}
