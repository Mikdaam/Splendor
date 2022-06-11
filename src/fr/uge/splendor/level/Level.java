package fr.uge.splendor.level;

public enum Level {
  UNKNOWN,
  EMPTY,
  LEVEL_1,
  LEVEL_2,
  LEVEL_3,
  LEVEL_NOBLE;
  
  
  public static Level getLevel(String levelString) {
    return switch (levelString) {
      case "1" -> LEVEL_1;
      case "2" -> LEVEL_2;
      case "3" -> LEVEL_3;
      case "NOBLE" -> LEVEL_NOBLE;
      default -> throw new IllegalArgumentException("Unexpected value: " + levelString);
    };
  }
  
  
  public static Level getLevel(int level) {
    return switch (level) {
      case 1 -> LEVEL_1;
      case 2 -> LEVEL_2;
      case 3 -> LEVEL_3;
      default -> UNKNOWN;
    };
  }
  
}
