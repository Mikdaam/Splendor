package fr.uge.splendor.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import fr.uge.splendor.card.Card;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.level.Level;

/**
 * This class represents a CardDeck.
 * It's represented by a list of cards.
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
 *
 */
public class CardDeck {
  private final ArrayList<Card> deck;
  private final String name;
  
  /**
   * Creates an empty CardDeck
   */
  public CardDeck(String name) {
    Objects.requireNonNull(name);
    
    deck = new ArrayList<Card>();
    this.name = name;
  }
  
  /**
   * Shuffles a CardDeck.
   */
  public void shuffleCardDeck() {
    Collections.shuffle(deck);
  }
  
  /**
   * Returns the CardDeck's size.
   * @return The CardDeck's size.
   */
  public long size() {
    return deck.size();
  }
  
  /**
   * Converts a level to a name
   * @param level - the level to convert
   * @return The name associated to the level
   */
  private static String levelToName(Level level) {
    Objects.requireNonNull(level);
    
    return switch(level) {
      case LEVEL_1 -> "LV. 1";
      case LEVEL_2 -> "LV. 2";
      case LEVEL_3 -> "LV. 3";
      default -> "LV. ?";
    };
  }
  
  /**
   * Adds a card to the CardDeck.
   * @param card - the card to add to the CardDeck.
   */
  public void add(Card card) {
    Objects.requireNonNull(card, "The card to add to the deck cannot be null!");
    deck.add(card);
  }
  
  /**
   * Removes the first card of the deck (the last one that was pushed into it)
   * @return
   */
  public Card removeFirstCard() {    
    return deck.remove(deck.size() - 1);    
  }
  
  /**
   * Returns a Map grouping each CardDeck with its level.
   * @return A Map grouping each CardDeck with its level.
   */
  public HashMap<Level, CardDeck> groupByLevel() {
  		var groupBy = new HashMap<Level, CardDeck>();
  		deck.forEach(card -> groupBy.computeIfAbsent(card.level(), level -> new CardDeck(levelToName(level))).add(card));
  		return groupBy;
	 }
  
 
  /**
   * Returns a HashMap summarizing a CardDeck
   * @param colors
   * @return
   */
  public HashMap<Color, Integer> getDeckSummary(List<Color> colors) {
    Objects.requireNonNull(colors);
    
    var summary = new HashMap<Color, Integer>();
    
    deck.forEach(card -> {
      if (colors.contains(card.color())) {
        summary.merge(card.color(), 1, Integer::sum);
      } 
    });
    
    return summary;    
  }
  
  /**
   * Returns the amount of cards of a given color
   * @param color - the color we're looking for
   * @return The amount of cards of the given color
   */
  public long getColorAmount(Color color) {
    Objects.requireNonNull(color);
    return deck.stream().filter(card -> card.color().equals(color)).count();
  }
    
  /**
   * Returns the total amount of prestige points contained in a CardDeck
   * @return the amount of prestige points contained in a CardDeck
   */
  public int getPrestigePoints() {
    return deck.stream()
               .map(card -> card.prestigePoint())
               .reduce(0, Integer::sum);
  }
  
  
  
  /**
   * Returns a row with the colors names found in the given list.
   * @param colors - the list of colors to display
   * @return A row with the colors names found in the given list.
   */
  private String colorsToString(List<Color> colors) {
    var sb = new StringBuilder();
    
    colors.forEach(color -> {
      var colorText = color.toString();
      sb.append("│ ").append(colorText);
      
      for (var i = 10 - colorText.length(); i > 0; i--) {
        sb.append(" ");
      }
    });
    
    sb.append("│\n");
    return sb.toString();
  }
  
  /**
   * Returns a row with the cards amount per color.
   * @param colors - the list of possible colors
   * @return A row with the cards amount per color.
   */
  private String valuesToString(List<Color> colors) {
    var sb = new StringBuilder();
    var deckSummary = this.getDeckSummary(colors);
    
    colors.forEach(color -> {
      sb.append("│     ").append(deckSummary.getOrDefault(color, 0)).append("     ");
    });
    
    sb.append("│\n");
    return sb.toString();
  }
  
  /**
   * Returns a row with a given start, mid and end character.
   * @param colors - the list of colors to get the total size of the row
   * @param start - the starting character
   * @param mid - the mid character
   * @param end - the end character
   * @return the row computed witht he given characters
   */
  private String rowToString(List<Color> colors, String start, String mid, String end) {
    var sb = new StringBuilder(start);
    sb.append("───────────");
    
    for (var i = colors.size() - 1; i > 0; i--) {
      sb.append(mid).append("───────────");
    }
    
    sb.append(end).append("\n");
    return sb.toString(); 
  }
  
  /**
   * Returns a String describing a summary of a CardDeck with the amount of each card for each color. 
   * @param colors - The list of possible card colors
   * @return The String describing the CardDeck's summary
   */
  public String deckSummaryToString(List<Color> colors) {
    Objects.requireNonNull(colors);
    
    var sb = new StringBuilder(); 
     
    sb.append(rowToString(colors, "┌", "┬", "┐"))
      .append(colorsToString(colors))
      .append(rowToString(colors, "├", "┼", "┤"))
      .append(valuesToString(colors))
      .append(rowToString(colors, "└", "┴", "┘"));

    return sb.toString();
  }
  
  /**
   * Returns an ASCII Art of a CardDeck with it's name (level) and its amount of cards.
   * @return The CardDeck's ASCII Art
   */
  @Override
  public String toString() {
    var sb = new StringBuilder();
    
    sb.append("╭────────────╮\n")
      .append("│           ░│\n")
      .append("│   DECK    ░│\n")
      .append("│   ").append(name).append("   ░│\n")
      .append("│           ░│\n")
      .append("│  ").append(String.format("%02d", deck.size())).append(" CARDS ░│\n")
      .append("│           ░│\n")
      .append("│           ░│\n")
      .append("╰────────────╯\n");
    
    return sb.toString();
  }

}
