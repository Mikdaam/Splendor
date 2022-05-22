package fr.uge.splendor.card;

import java.util.Objects;
import java.util.HashMap;

import fr.uge.splendor.color.*;
import fr.uge.splendor.level.*;

/**
 * This record represent a development card of the game
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 */
public record DevelopmentCard(Level level, 
                              Color color,
                              int prestigePoint, 
                              HashMap<Color, Integer> price)
                              implements Card {
	
	public DevelopmentCard {
		Objects.requireNonNull(price, "Price can't be null");
		Objects.requireNonNull(level, "Level can't be null");
		Objects.requireNonNull(color, "Color can't be null");
	}
	
	
	/**
	 * 
	 * @return
	 */
	private String priceToCardString() {
	  var sb = new StringBuilder();
	  
	  price.forEach((color, price) -> {
	    var colorText = color.toString();
	    var maxLength = Integer.min(colorText.length(), 5);
	    if (price != 0) {
	      sb.append("│ ").append(colorText.substring(0, maxLength)).append(": ").append(price);
	      
	      /*To add the missing spaces*/
	      for (var i = 8 - maxLength - 3; i >= 0; i--) {
	        sb.append(" ");
	      }
	        sb.append(" ░│\n");
	    }
   });
	  
	  var nonNullPrices = price.keySet().stream().filter(color -> price.get(color) != 0) .count();
   for (var line = 4 - nonNullPrices; line > 0; line--) {
     sb.append("│           ░│\n");
   }
	  
	  return sb.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	private String colorToCardString() {
	  var sb = new StringBuilder("│ ");
	  var colorText = color.toString();
	  
	  sb.append(colorText);
	  
	  for (var i = 8 - colorText.length(); i > 0; i--) {
	    sb.append(" ");
	  }
	  
	  sb.append("  ░│\n");
	  return sb.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	private String prestigeToCardString() {
	 var sb = new StringBuilder("│         ");
	 
	 if (prestigePoint != 0) {
	   sb.append(prestigePoint);
	 } else {
	   sb.append(" ");
	 }
	 
	 sb.append(" ░│\n");
	 
	 
	 	return sb.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public static String emptyCardToString() {
	  var sb = new StringBuilder();
	  
	  for (var i = 0; i < 8; i++) {
	    sb.append("              \n");
	  }
	    
	  return sb.append("              ").toString();
	}
	
	/*
	 * 
	 */
	@Override
	public String toString() {
	  var sb = new StringBuilder("╭────────────╮\n");
	  
	  sb.append(this.colorToCardString())
	    .append(this.prestigeToCardString())
	    .append("├────────────┤\n")
	    .append(this.priceToCardString());
	  
	  return sb.append("╰────────────╯").toString();
	}
	
}