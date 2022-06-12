package fr.uge.splendor.color;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Thos enum describes the colors (Precious Stones) possible for the Splendor game.
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 *
 */
public enum Color {
  UNKNOWN,
  EMPTY,
  DIAMOND,
  EMERALD,
  ONYX,
  RUBY,
  SAPPHIRE,
  GOLD,
  NOBLE;
  
  /**
   * Returns the Color associated to a String
   * @param colorString - the String to get the Color from
   * @return The Color associated to a String
   */
  public static Color getColor(String colorString) {
    Objects.requireNonNull(colorString);
    
    return switch (colorString) {
      case "DIAMOND" -> DIAMOND;
      case "EMERALD" -> EMERALD;
      case "GOLD" -> GOLD;
      case "ONYX" -> ONYX;
      case "RUBY" -> RUBY;
      case "SAPPHIRE" -> SAPPHIRE;
      case "NOBLE" -> NOBLE;
      default -> throw new IllegalArgumentException("Unexpected value: " + colorString);
    };
  }
  
  /**
   * Returns the awt.Color associated to a splendor.Color
   * @param color - the splendor.Color to get the awt.Color from
   * @return the awt.Color associated to a splendorColor
   */
  public static java.awt.Color getScreenColor(Color color) {
    Objects.requireNonNull(color);
    
   	return switch (color) {
 	    case DIAMOND -> java.awt.Color.WHITE;
 	    case EMERALD -> java.awt.Color.GREEN;
 	    case GOLD -> java.awt.Color.YELLOW;
 	    case ONYX -> java.awt.Color.BLACK;
 	    case RUBY -> java.awt.Color.RED;
 	    case SAPPHIRE -> java.awt.Color.BLUE;
 	    default -> throw new IllegalArgumentException("Unexpected value: " + color);
   	};
	 }
  
  /**
   * Returns all the colors possible for the tokens.
   * @return The colors possible for the tokens.
   */
  public static List<Color> getTokensColorsList() {
    return Arrays.stream(Color.values())
                 .filter(color -> color != NOBLE && color != UNKNOWN && color != EMPTY)
                 .collect(Collectors.toList());
  }
  
  /**
   * Returns all the colors possible for the cards.
   * @return The colors possible for the cards.
   */
  public static List<Color> getCardsColorsList() {
    return Arrays.stream(Color.values())
                 .filter(color -> color != GOLD && color != UNKNOWN && color != EMPTY)
                 .collect(Collectors.toList());
  }
}
