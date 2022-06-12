package fr.uge.splendor.card;

import java.util.EnumMap;

import fr.uge.splendor.color.Color;
import fr.uge.splendor.level.Level;

public record NobleCard(int prestigePoint, 
    										EnumMap<Color, Integer> price) 
												implements Card {

	@Override
	public Level level() {
		return Level.LEVEL_NOBLE;
	}

	@Override
	public Color color() {
		return Color.NOBLE;
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
	  var colorText = color().toString();
	  
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
	  /*var sb = new StringBuilder("╭────────────╮\n");
	  
	  sb.append(this.colorToCardString())
	    .append(this.prestigeToCardString())
	    .append("├────────────┤\n")
	    .append(this.priceToCardString());
	  
	  return sb.append("╰────────────╯").toString();*/
	  return this.cardToString(color(), prestigePoint, price);
	}

}
