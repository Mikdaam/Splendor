package fr.uge.splendor.player;

import fr.uge.splendor.card.*;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.token.*;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.deck.TokenDeck;

import java.util.HashMap;
import java.util.Objects;

public final class HumanPlayer implements Player {
  private final int id;
  private final String name;
  private final CardDeck ownedCards;
  private final TokenDeck ownedTokens;
  
  
  public HumanPlayer(int id, String name) {
    Objects.requireNonNull(name, "The player's name cannot be null!");
    
    if (id < 0) {
      throw new IllegalArgumentException("The ID should be zero or positive!");
    }
    
    this.id = id;
    this.name = name;
    this.ownedCards = new CardDeck();
    this.ownedTokens = new TokenDeck();
  }
  
  @Override
  public boolean takeToken(Token token) {
    ownedTokens.add(null);
    return true;
  }
  
  @Override
  public boolean canBuyCard(Card card) {
    return true; //to put in DevCard instead? We give the carddeck and tokendeck of the player
  }
  
  @Override
  public HashMap<Color, Integer> buyCard(Card card) {
    var tokensToGiveBack = new HashMap<Color, Integer>();
    var deckSummary = ownedCards.getDeckSummary();
    
    card.price().forEach((color, price) -> {
      tokensToGiveBack.put(color, price - deckSummary.getOrDefault(color, 0));
    });
    
    
    ownedCards.add(card);
    ownedTokens.remove(tokensToGiveBack);
    return tokensToGiveBack;
  }
  
  @Override
  public boolean doAction() {
    return true;
  }
  
  @Override
  public String toString() {
    return "";
  }
  
  private String displayASCII() {
    return "";
  }
  
  @Override
  public void display(boolean isASCII) {
    
  }
}
