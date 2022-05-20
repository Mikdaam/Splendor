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
  
  public long getColorNumber(Color color) {
    return deck.stream().filter(card -> card.color().equals(color)).count();
  }

  public int getPrestigePoints() {
    return deck.stream()
               .map(card -> card.prestigePoint())
               .reduce(0, Integer::sum);
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
    
  private String colorsToString() {
    var sb = new StringBuilder();
    
    Color.getCardsColorsList().forEach(color -> {
      var colorText = color.toString();
      sb.append("│ ").append(colorText);
      
      for (var i = 10 - colorText.length(); i > 0; i--) {
        sb.append(" ");
      }
    });
    
    sb.append("│\n");
    return sb.toString();
  }

  private String valuesToString() {
    var sb = new StringBuilder();
    var deckSummary = this.getDeckSummary();
    
    Color.getCardsColorsList().forEach(color -> {
      
      sb.append("│     ").append(deckSummary.getOrDefault(color, 0)).append("     ");
    });
    
    sb.append("│\n");
    return sb.toString();
  }
  
  private String rowToString(String start, String mid, String end) {
    var sb = new StringBuilder(start);
    sb.append("───────────");
    
    var colors = Color.getCardsColorsList();
    
    for (var i = colors.size() - 1; i > 0; i--) {
      sb.append(mid).append("───────────");
    }
    
    sb.append(end).append("\n");
    return sb.toString(); 
  }
  
  public String deckSummaryToString() {
    var sb = new StringBuilder(); 
     
    sb.append(rowToString("┌", "┬", "┐"))
      .append(colorsToString())
      .append(rowToString("├", "┼", "┤"))
      .append(valuesToString())
      .append(rowToString("└", "┴", "┘"));

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
