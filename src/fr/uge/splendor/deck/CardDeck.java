package fr.uge.splendor.deck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import fr.uge.splendor.card.Card;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.level.Level;

public class CardDeck {
  private final ArrayList<Card> deck;
  private final Level level;
  
  public CardDeck(Level level) {
    Objects.requireNonNull(level, "Tu ne sais pas lire ? Un niveau on a dit!!!!!!!!!!!!!!!!!!!");
    this.deck = new ArrayList<Card>();
    this.level = level;
  }
  
  public void add(Card card) {
    Objects.requireNonNull(card, "The card to add to the deck cannot be null!");
    if (!isOfLevel(card.level())) {
      throw new IllegalArgumentException("You cannot add this card at this level");
    }
    deck.add(card);
  }
  
  public Card remove(Card card) {
    Objects.requireNonNull(card, "The card to remove from the deck cannot be null!");
    
    if (deck.remove(card)) {
      return card;
    }
    
    throw new IllegalArgumentException("The card wasn't in the CardDeck");
  }
  
  
  public Card removeFirstCard() {
    if (deck.size() == 0) {
      System.out.println("The deck is empty.");
      return null;
    }
    
    return deck.remove(deck.size() - 1);    
  }
  
  
  public HashMap<Color, Integer> getDeckSummary() {
    var res = new HashMap<Color, Integer>();
    
    deck.forEach(card -> res.merge(card.color(), 1, Integer::sum));
    
    return res;    
  }
  
  public void displayCards() {
    deck.forEach(card ->System.out.println(card));
  }
  
  public boolean isOfLevel(Level level) {
    return this.level.equals(level);
  }
  
  
  @Override
  public String toString() {
    var line = "------------------";
    var sb = new StringBuilder(line);
    
    sb.append("\nCARD DECK :\n\n");
    
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
