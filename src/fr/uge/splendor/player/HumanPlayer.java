package fr.uge.splendor.player;

import fr.uge.splendor.board.Board;
import fr.uge.splendor.card.*;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.token.*;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.deck.TokenDeck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class HumanPlayer implements Player {
  private final int id;
  private final String name;
  private final CardDeck ownedCards;
  private final TokenDeck ownedTokens;
  private final Board reservedCards;
  
  
  public HumanPlayer(int id, String name) {
    Objects.requireNonNull(name, "The player's name cannot be null!");
    
    if (id < 0) {
      throw new IllegalArgumentException("The ID should be zero or positive!");
    }
    
    this.id = id;
    this.name = name;
    ownedCards = new CardDeck();
    ownedTokens = new TokenDeck();
    reservedCards = new Board(1, 3);
  }
  
  public void removeTokensColor(Color color) {
    Objects.requireNonNull(color);
    ownedTokens.removeColor(color);
  }
  
  public int getNumberOfTokens() {
    return ownedTokens.getDeckSummary().values().stream().reduce(0, Integer::sum);
  }
  
  public int id() {
    return id;
  }
  
 	@Override
 	public String name() {
 	  return name;
 	}
  
  public int prestigePoints() {
    return ownedCards.getPrestigePoints();
  }
  
  @Override
  public int numberOfDevelopmentCards(List<Color> colors) {
    var summary = ownedCards.getDeckSummary(colors);
    var res = 0;
    
    for (var color: summary.keySet()) {
      if (color != Color.NOBLE) {
        res += summary.getOrDefault(color, 0);
      }
    }
    
    return res;
  }
  
  public Card removeFromReserved(int row, int col) {
    return reservedCards.remove(row, col);
  }
  
  public void addToReserved(Card card, int row, int col) {
    reservedCards.add(card, row, col);
  }
  
  public void pushToReserved(Card card) {
    reservedCards.push(card);
  }
  
  public int numberOfReservedCards() {
    return reservedCards.numberOfCards();
  }
  
  /*Replace Token with Color?*/
  @Override
  public void takeToken(Token token) {
    Objects.requireNonNull(token);
    ownedTokens.add(Map.of(token.color(), 1));
  }
  
  @Override
  public boolean canBuyCard(Card card) {
    Objects.requireNonNull(card);
    
    var jokers =  ownedTokens.getColorNumber(Color.GOLD);
    
    for (var color: card.price().keySet()) {
      var price = card.price().get(color);
      if (ownedCards.getColorNumber(color) + ownedTokens.getColorNumber(color) < price) {
        if (ownedCards.getColorNumber(color) + ownedTokens.getColorNumber(color) + jokers >= price) {
          jokers -= price - ownedCards.getColorNumber(color) - ownedTokens.getColorNumber(color);
        } else {
          return false;
        }
      }
    }
    
    return true;
  }
  
  @Override
  public HashMap<Color, Integer> buyCard(Card card, List<Color> colors) {
    Objects.requireNonNull(card);
    
    var tokensToGiveBack = new HashMap<Color, Integer>();
    var deckSummary = ownedCards.getDeckSummary(colors);
    var jokers = 0;
    
    for (var color: card.price().keySet()) {
      var price = card.price().get(color);
     
      if (deckSummary.getOrDefault(color, 0) < price) { /*Check if there is enough bonus or not*/
        var potentialJoker = 0; /*Potential gold to add*/
        if (ownedTokens.getColorNumber(color) + deckSummary.getOrDefault(colors, 0) < price) { /*if not enough with tokens + bonuses*/
          potentialJoker = price - ownedTokens.getColorNumber(color) - deckSummary.getOrDefault(colors, 0); /*Calculating the jokers to use there*/
        }
        
        tokensToGiveBack.put(color, price - deckSummary.getOrDefault(color, 0) - potentialJoker); /*tokens = price - bonus - potentialJoker*/
        jokers += potentialJoker;
      }
    }
    
    if (jokers != 0) {
      tokensToGiveBack.put(Color.GOLD, jokers);
    }
    
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
  
  private String ownedCardsToString(List<Color> colors) {
    var sb = new StringBuilder();
    
    sb.append(" CARDS:\n")
      .append(ownedCards.deckSummaryToString(colors))
      .append("\n");
    
    return sb.toString();
  }
  
  private String reservedCardsToString() {
    if(numberOfReservedCards() == 0) {
      return "";
    }
    
    var sb = new StringBuilder();
    
    sb.append(" RESERVED CARDS:\n")
      .append(reservedCards)
      .append("\n");
    
    return sb.toString();
  }
  
  
  @Override
  public String toString(List<Color> colors) {
    Objects.requireNonNull(colors);
    
    var sb = new StringBuilder();
    
    sb.append(firstRowToString())
      .append(tokensToString())
      .append(ownedCardsToString(colors))
      .append(reservedCardsToString())
      .append("\n");
    
    return sb.toString();
  }
}
