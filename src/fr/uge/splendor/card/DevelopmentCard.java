package fr.uge.splendor.card;

import java.util.Objects;
import java.util.EnumMap;

import fr.uge.splendor.color.*;
import fr.uge.splendor.level.*;

/**
 * This record represents a development card of the game
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 */
public record DevelopmentCard(Level level, 
                              Color color,
                              int prestigePoint, 
                              EnumMap<Color, Integer> price)
                              implements Card {
	
  /**
   * Creates a DevelopmentCard with its given information
   * @param level - the DevelopmentCard's level
   * @param color - the DevelopmentCard's color
   * @param prestigePoint - the DevelopmentCard's prestige points
   * @param price - the DevelopmentCard's price
   */
 	public DevelopmentCard {
  		Objects.requireNonNull(price, "Price can't be null");
  		Objects.requireNonNull(level, "Level can't be null");
  		Objects.requireNonNull(color, "Color can't be null");
  		
  		if (prestigePoint < 0) {
  		  throw new IllegalArgumentException("prestige points must be positive");
  		}
 	}
 	
 	/**
 	 * Returns a DevelopmenCard's String with all it's information
 	 * @return A DevelopmenCard's String with all it's information
 	 */
 	@Override
  public String toString() {
    return this.cardToString(color, prestigePoint, price);
  }
}