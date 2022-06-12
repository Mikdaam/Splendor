package fr.uge.splendor.card;

import java.util.EnumMap;
import java.util.Objects;

import fr.uge.splendor.color.*;
import fr.uge.splendor.level.Level;

/**
 * This interface represents a Card from the Splendor game.
 * A card has a level, a color, an amount of prestige points, a price.
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 *
 */
public interface Card {
	  
  /**
   * Returns the card's level
   * @return The card's level
   */
 	Level level();
 	
 	/**
   * Returns the card's color
   * @return The card's color
   */
 	Color color();
 	
 	/**
   * Returns the card's amount of prestige points
   * @return The card's amount of prestige points
   */
 	int prestigePoint();
 	
 	/**
   * Returns the card's price
   * @return The card's price
   */
 	EnumMap<Color, Integer> price();
 	
 	
 	
 	/**
 	 * Adds the missing space for a card's String
 	 * @param sb - the StringBuilder
 	 * @param spaceLeft - the space left 
 	 */
 	private void addMissingSpace(StringBuilder sb, int spaceLeft) {
 	  for (var i = 8 - spaceLeft; i >= 0; i--) {
      sb.append(" ");
    }
 	  sb.append(" ░│\n");
 	}
 	
 	/**
 	 * Returns the String of a Card's price
 	 * @param totalPrice - the card's price
 	 * @return The String of a Card's price
 	 */
  private String priceToCardString(EnumMap<Color, Integer> totalPrice) {
    var sb = new StringBuilder();
    
    totalPrice.forEach((color, price) -> {
      var colorText = color.toString();
      var maxLength = Integer.min(colorText.length(), 5);
      if (price != 0) {
        sb.append("│ ").append(colorText.substring(0, maxLength)).append(": ").append(price);
        
        /*
        for (var i = 8 - maxLength - 3; i >= 0; i--) {
          sb.append(" ");
        }*/
          //sb.append(" ░│\n");
        addMissingSpace(sb, maxLength+3);
      }
    });
    
    var nonNullPrices = totalPrice.keySet().stream().filter(color -> totalPrice.get(color) != 0) .count();
    for (var line = 4 - nonNullPrices; line > 0; line--) {
      sb.append("│           ░│\n");
    }
    
    return sb.toString();
  }
  
  /**
   * Returns the String of a Card's color
   * @param totalPrice - the card's color
   * @return The String of a Card's color
   */
  private String colorToCardString(Color color) {
    var sb = new StringBuilder("│ ");
    var colorText = color.toString();
    
    sb.append(colorText);
    
    /*for (var i = 8 - colorText.length(); i > 0; i--) {
      sb.append(" ");
    }
    
    sb.append("  ░│\n");*/
    addMissingSpace(sb, colorText.length());
    return sb.toString();
  }
  
  /**
   * Returns the String of a Card's prestige points
   * @param totalPrice - the card's prestige points
   * @return The String of a Card's prestige points
   */
  private String prestigeToCardString(int prestigePoints) {
   var sb = new StringBuilder("│         ");
   
   if (prestigePoints != 0) {
     sb.append(prestigePoints);
   } else {
     sb.append(" ");
   }
   
   sb.append(" ░│\n");
   
   
    return sb.toString();
  }
  
  /**
   * Returns the String of a Card with all its information.
   * @param color - the card's color
   * @param prestigePoints - the card's prestige points
   * @param totalPrice - the card's price
   * @return The String of a Card with all its information
   */
  default String cardToString(Color color, int prestigePoints, EnumMap<Color, Integer> totalPrice) {
    Objects.requireNonNull(color);
    Objects.requireNonNull(totalPrice);
    
    var sb = new StringBuilder("╭────────────╮\n");
    
    sb.append(colorToCardString(color))
      .append(prestigeToCardString(prestigePoints))
      .append("├────────────┤\n")
      .append(priceToCardString(totalPrice));
    
    return sb.append("╰────────────╯").toString();
  }
}