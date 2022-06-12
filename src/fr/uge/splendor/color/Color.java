package fr.uge.splendor.color;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
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
  
  public static Color getColor(String colorString) {
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
  
  public static java.awt.Color getScreenColor(Color color) {
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
  
  public static List<Color> getTokensColorsList() {
    return Arrays.stream(Color.values())
                 .filter(color -> color != NOBLE && color != UNKNOWN && color != EMPTY)
                 .collect(Collectors.toList());
  }
  
  public static List<Color> getCardsColorsList() {
    return Arrays.stream(Color.values())
                 .filter(color -> color != GOLD && color != UNKNOWN && color != EMPTY)
                 .collect(Collectors.toList());
  }
}
