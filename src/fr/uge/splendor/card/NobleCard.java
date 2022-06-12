package fr.uge.splendor.card;

import java.util.EnumMap;
import java.util.Objects;

import fr.uge.splendor.color.Color;
import fr.uge.splendor.level.Level;

/**
 * This record represents a noble card of the game
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 */
public record NobleCard(int prestigePoint, 
    										EnumMap<Color, Integer> price) 
												implements Card {
  
  /**
   * Creates a NobleCard with its given information
   * @param prestigePoint - the NobleCard's prestige points
   * @param price - the NobleCard's price
   */
  public NobleCard {
    Objects.requireNonNull(price, "Price can't be null");
    
    if (prestigePoint < 0) {
      throw new IllegalArgumentException("prestige points must be positive");
    }
  }
  
  /**
   * Returns a NobleCard's level
   * @return A NobleCard's level
   */
 	@Override
 	public Level level() {
 		 return Level.LEVEL_NOBLE;
 	}
  
 	/**
 	 * Returns a NobleCard's color
 	 * @return A NobleCard's color
 	 */
 	@Override
 	public Color color() {
 		 return Color.NOBLE;
 	}
 	
 	
 	/**
   * Returns a NobleCard's String with all it's information
   * @return A NobleCard's String with all it's information
   */
 	@Override
  public String toString() {
    return this.cardToString(color(), prestigePoint, price);
  }
}
