package fr.uge.splendor.color;

public enum Color {
  DIAMOND,
  EMERALD,
  GOLD,
  ONYX,
  RUBY,
  SAPPHIRE,
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
}
