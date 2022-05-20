package fr.uge.splendor.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

import fr.uge.splendor.card.Card;
import fr.uge.splendor.card.DevelopmentCard;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.utils.Utils;

public class CardDeck {
  private final ArrayList<Card> deck;
  /* TODO: Add a name */
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
  
  public long size() {
    return deck.size();
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
  
  /**
   * Shuffle the card Deck
   */
  public void shuffleCardDeck() {
    Collections.shuffle(deck);
  }
  
  public void displayCards() {
    var rows = 1;
    
    if (deck.size() > 4) {
      rows = deck.size() / 4;
    }
    
    var deckStrings = new String[rows][4];
    var i = 0;
    var j = 0;
    
    for (var card : deck) {
      if (j == 4) {
        j = 0;
        i++;
      }
      
      deckStrings[i][j] = card.toString();
      j++;
    }
    while (j < 4) {
      deckStrings[i][j] = DevelopmentCard.emptyCardToString();
      j++;
    }
    
    
    for (var row = 0; row < deckStrings.length; row++) {
      System.out.println(Utils.computeStringsToLine(deckStrings[row]));
    }
  }
  
  
  public String displayDeckSummary() {
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
  
  @Override
  public String toString() {
    var sb = new StringBuilder("╭────────────╮\n");

    sb.append("│           ░│\n")
      .append("│           ░│\n")
      .append("│    DECK   ░│\n")
      .append("│           ░│\n")
      .append("│  ").append(String.format("%02d", deck.size())).append(" CARDS ░│\n")
      .append("│           ░│\n")
      .append("│           ░│\n")
      .append("╰────────────╯");
    
    return sb.toString();
  }


}
