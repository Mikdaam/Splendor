package fr.uge.splendor.level;

import java.util.Objects;

/**
 * This enum represents the different levels a card can have in Splendor.
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 *
 */
public enum Level {
  UNKNOWN,
  EMPTY,
  LEVEL_1,
  LEVEL_2,
  LEVEL_3,
  LEVEL_NOBLE;
  
  
  /**
   * Returns a level associated with a given string.
   * @param levelString - the string to get the level from
   * @return The level associated with the given string.
   */
  public static Level getLevel(String levelString) {
    Objects.requireNonNull(levelString);
    
    return switch (levelString) {
      case "1" -> LEVEL_1;
      case "2" -> LEVEL_2;
      case "3" -> LEVEL_3;
      case "NOBLE" -> LEVEL_NOBLE;
      default -> throw new IllegalArgumentException("Unexpected value: " + levelString);
    };
  }
  
  /**
   * Returns a level associated with a given integer.
   * @param levelString - the integer to get the level from
   * @return The level associated with the given integer.
   */
  public static Level getLevel(int level) {
    return switch (level) {
      case 1 -> LEVEL_1;
      case 2 -> LEVEL_2;
      case 3 -> LEVEL_3;
      default -> UNKNOWN;
    };
  }
  
}
