package fr.uge.splendor.deck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import fr.uge.splendor.card.Card;
import fr.uge.splendor.color.Color;

public class CardDeck {
  private final ArrayList<Card> deck;
  
  public CardDeck() {
    this.deck = new ArrayList<Card>();
  }
  
  public void add(Card card) {
    Objects.requireNonNull(card, "The card to add to the deck cannot be null!");
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
