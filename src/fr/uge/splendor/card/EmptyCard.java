package fr.uge.splendor.card;

import java.util.EnumMap;
import java.awt.Graphics2D;
import java.util.HashMap;

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
	public void render(Graphics2D graphic, int x, int y) {
		int width = 75, height = 100;
		// draw the board
		graphic.setColor(java.awt.Color.WHITE);
		graphic.drawRoundRect(x, y, width, height, 5, 5);
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
