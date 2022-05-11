package fr.uge.splendor.deck;

import java.util.ArrayList;
import java.util.Objects;

import fr.uge.splendor.card.Card;

public record CardDeck(ArrayList<Card> deck) {
  
  public void add(Card card) {
    Objects.requireNonNull(card, "The card to add to the deck cannot be null!");
    //deck.putIfAbsent(card.color(), new ArrayList<Card>()).add(card);
    deck.add(card);
  }

  
  public Card remove(Card card) {
    Objects.requireNonNull(card, "The card to remove from the deck cannot be null!");
    if (deck.remove(card)) {
      return card;
    }
    
    throw new IllegalArgumentException("The card wasn't in your CardDeck");
  }
  
  
  public Card removeFirstCard() {
    if (deck.size() == 0) {
      System.out.println("The deck is empty.");
      return null;
    }
    
    return deck.remove(deck.size() - 1);    
  }

}
