package fr.uge.splendor.player;

import fr.uge.splendor.board.Board;
import fr.uge.splendor.card.*;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.token.*;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.deck.TokenPurse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class HumanPlayer implements Player {
  private final int id;
  private final String name;
  private final CardDeck ownedCards;
  private TokenPurse ownedTokens;
  private final Board reservedCards;
  
  
  public HumanPlayer(int id, String name) {
    if (id < 0) {
      throw new IllegalArgumentException("The ID should be zero or positive!");
    }
    Objects.requireNonNull(name, "The player's name cannot be null!");
    
    this.id = id;
    this.name = name;
    ownedCards = new CardDeck();
    reservedCards = new Board(1, 3);
    ownedTokens = new TokenPurse();
    this.initTokens();
    
  }
  
  private void initTokens() {
    ownedTokens = ownedTokens.addToken(Color.DIAMOND, 0);
    ownedTokens = ownedTokens.addToken(Color.EMERALD, 0);
    ownedTokens = ownedTokens.addToken(Color.ONYX, 0);
    ownedTokens = ownedTokens.addToken(Color.RUBY, 0);
    ownedTokens = ownedTokens.addToken(Color.SAPPHIRE, 0);
    ownedTokens = ownedTokens.addToken(Color.GOLD, 0);
  }
  
  public void removeTokensColor(Color color) {
    Objects.requireNonNull(color);
    ownedTokens = ownedTokens.removeColor(color);
  }
  
  public int getNumberOfTokens() {
    return ownedTokens.numberOfTokens();
    //return ownedTokens.getDeckSummary().values().stream().reduce(0, Integer::sum);
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
  
  public Card removeFromReserved(Coordinate coordinate) {
    return reservedCards.remove(coordinate);
  }
 
  
  public void pushToReserved(Card card) {
    reservedCards.push(card, 0);
  }
  
  public int numberOfReservedCards() {
    return reservedCards.numberOfCards();
  }
  
  /*Replace Token with Color?*/
  @Override
  public void takeToken(Color color) {
    Objects.requireNonNull(color);
    ownedTokens = ownedTokens.addToken(color, 1);
  }
  
  public boolean canGetNoble(Card noble) {
  	Objects.requireNonNull(noble);
    
    for (var color: noble.price().keySet()) {
      var price = noble.price().get(color);
      if (ownedCards.getColorNumber(color) < price) {
        return false;
      }
    }
    
    return true;
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
  public TokenPurse buyCard(Card card, List<Color> colors) {
    Objects.requireNonNull(card);
    
    var tokensToGiveBack = new TokenPurse();
    var deckSummary = ownedCards.getDeckSummary(colors);
    var jokers = 0;
    
    for (var color: card.price().keySet()) {
      var price = card.price().get(color);
     
      if (deckSummary.getOrDefault(color, 0) < price) { /*Check if there is enough bonus or not*/
        var potentialJoker = 0; /*Potential gold to add*/
        if (ownedTokens.getColorNumber(color) + deckSummary.getOrDefault(colors, 0) < price) { /*if not enough with tokens + bonuses*/
          potentialJoker = price - ownedTokens.getColorNumber(color) - deckSummary.getOrDefault(colors, 0); /*Calculating the jokers to use there*/
        }
        
        tokensToGiveBack = tokensToGiveBack.addToken(color, price - deckSummary.getOrDefault(color, 0) - potentialJoker); /*tokens = price - bonus - potentialJoker*/
        jokers += potentialJoker;
      }
    }
    
    if (jokers != 0) {
      tokensToGiveBack = tokensToGiveBack.addToken(Color.GOLD, jokers);
    }
    
    ownedCards.add(card);
    ownedTokens = ownedTokens.remove(tokensToGiveBack);
    return tokensToGiveBack;
  }
  
  public Board reservedCards() {
		return reservedCards;
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
