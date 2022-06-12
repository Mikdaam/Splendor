package fr.uge.splendor.card;

import java.util.EnumMap;

import fr.uge.splendor.color.Color;
import fr.uge.splendor.level.Level;

public record EmptyCard() implements Card {

  public Level level() {
    return Level.EMPTY;
  }

  public Color color() {
    return Color.EMPTY;
  }

  public int prestigePoint() {
    return 0;
  }

  public EnumMap<Color, Integer> price() {
    return new EnumMap<Color, Integer>(Color.class);
  }

  @Override
  public String toString() {
    var sb = new StringBuilder();

    for (var i = 0; i < 8; i++) {
      sb.append("              \n");
    }

    return sb.append("              ").toString();
  }
    
}
