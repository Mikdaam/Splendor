package fr.uge.splendor.card;

import java.util.Objects;
import java.util.HashMap;

import fr.uge.splendor.color.*;
import fr.uge.splendor.game.level.Level;

public record DevelopmentCard(int prestigePoint, 
                              HashMap<Color, Integer> price,
                              Level level,
                              Color color)
                              implements Card {
	
	public DevelopmentCard {
		Objects.requireNonNull(price, "Price can't be null");
		Objects.requireNonNull(level, "Level can't be null");
		Objects.requireNonNull(color, "Color can't be null");
	}
	
	
	private String priceToString() {
	  var sb = new StringBuilder("------------\n");
	  
	  /* Display the price within the following format -> "COLOR: PRICE" */
    price.forEach((color, price) -> {
      if (price != 0) {
        sb.append(color).append(": ").append(price).append("\n");
      }
    });
     
    /* 
     * Since a card has a price of maximum 4 different colors, we add the missing lines
     * to have cards of equal height.
     */
    for (var line = 4 - price.size(); line > 0; line--) {
      sb.append("           \n");
    }
	  
	  return sb.toString();
	}
	
	@Override
	public String toString() {
	  var sb = new StringBuilder("------------\n");
	  
	  sb.append(color).append("\n");
	  
	  if (prestigePoint != 0) {
	    sb.append("           ").append(prestigePoint).append("\n");
	  } else {
	    sb.append("           \n");
	  }
	  
	  sb.append(this.priceToString());
	  
	  return sb.append("------------").toString();
	}
}
