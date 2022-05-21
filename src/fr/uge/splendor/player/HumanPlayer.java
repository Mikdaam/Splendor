package fr.uge.splendor.player;

import fr.uge.splendor.card.*;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.token.*;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.deck.TokenDeck;

import java.util.HashMap;
import java.util.Map;
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
  
  public void removeTokensColor(Color color) {
    ownedTokens.removeColor(color);
  }
  
  public int getNumberOfTokens() {
    return ownedTokens.getDeckSummary().values().stream().reduce(0, Integer::sum);
  }
  
  public int id() {
    return id;
  }
  
  public int prestigePoints() {
    return ownedCards.getPrestigePoints();
  }
  
  /*Replace Token with Color?*/
  @Override
  public void takeToken(Token token) {
    ownedTokens.add(Map.of(token.color(), 1));
  }
  
  @Override
  public boolean canBuyCard(Card card) {    
    for (var color: card.price().keySet()) {
      var price = card.price().get(color);
      if (ownedCards.getColorNumber(color) + ownedTokens.getColorNumber(color) < price) {
        return false;
      }
    }
    
    return true;
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
  
  private String firstRowToString() {
    var sb = new StringBuilder("┌───────────────────────────────────┐\n");
    
    sb.append("│  PLAYER ").append(id)
      .append("  │  ").append(String.format("%02d", this.prestigePoints()))
      .append(" PRESTIGE POINTS  │\n")
      .append("└───────────────────────────────────┘\n");
    
    return sb.toString();
  }
  
  private String tokensToString() {
    var sb = new StringBuilder(" TOKENS:\n");
    sb.append(ownedTokens.toString())
      .append("\n");
    
    return sb.toString();
  }
  
  private String ownedCardToString() {
    var sb = new StringBuilder();
    
    sb.append(" CARDS:\n")
      .append(ownedCards.deckSummaryToString())
      .append("\n");
    
    return sb.toString();
  }
    
  @Override
  public String toString() {
    var sb = new StringBuilder();
    
    sb.append(firstRowToString())
      .append(tokensToString())
      .append(ownedCardToString())
      .append("\n");
    
    return sb.toString();
  }
}
