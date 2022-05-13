package fr.uge.splendor.deck;

import java.util.HashMap;
import java.util.Objects;

import fr.uge.splendor.token.Token;

public class TokenDeck {
  private final HashMap<Token, Integer> deck;
  
  public TokenDeck() {
    this.deck = new HashMap<Token, Integer>();
  }
 
  public void add(HashMap<Token, Integer> tokens) {
    Objects.requireNonNull(tokens, "You have to add at least 1 token, your list cannot be null!");
    tokens.forEach((token, number) -> deck.merge(token, 1, Integer::sum));
  }

  
  public HashMap<Token, Integer> remove(HashMap<Token, Integer> tokens) {
    Objects.requireNonNull(tokens, "Your list of tokens to remove cannot be null");
    tokens.forEach((token, number) -> {
      if (number > 0 && number <= deck.get(token)) {
        deck.computeIfPresent(token, (key, oldValue) -> oldValue - number);
        //deck.merge(token, 0, (oldValue, newValue) -> oldValue - newValue);
      } else {
        throw new IllegalArgumentException("You're trying to withdraw too much or too less from your TokenDeck!");
      }
    });
    
    return tokens;
  }
}
