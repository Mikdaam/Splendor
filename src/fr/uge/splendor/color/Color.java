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
  
  public static List<Color> getTokensColorsList() {
    return Arrays.stream(Color.values())
                 .filter(color -> color != NOBLE)
                 .collect(Collectors.toList());
  }
  
  /*TODO: add a getGameVersionToken*/
}
