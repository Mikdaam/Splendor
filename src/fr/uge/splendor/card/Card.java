package fr.uge.splendor.card;

import java.util.EnumMap;
import java.awt.Graphics2D;
import java.util.HashMap;

import fr.uge.splendor.color.*;
import fr.uge.splendor.level.Level;
import fr.umlv.zen5.ApplicationContext;

public interface Card {
	
	Level level();
	
	Color color();
	
	int prestigePoint();
	
	EnumMap<Color, Integer> price();
	
	
	
	private void addMissingSpace(StringBuilder sb, int maxLength) {
	  for (var i = 8 - maxLength; i >= 0; i--) {
     sb.append(" ");
   }
	  sb.append(" ░│\n");
	}
	
	/**
  * 
  * @return
  */
 private String priceToCardString(EnumMap<Color, Integer> totalPrice) {
   var sb = new StringBuilder();
   
   totalPrice.forEach((color, price) -> {
     var colorText = color.toString();
     var maxLength = Integer.min(colorText.length(), 5);
     if (price != 0) {
       sb.append("│ ").append(colorText.substring(0, maxLength)).append(": ").append(price);
       
       /*
       for (var i = 8 - maxLength - 3; i >= 0; i--) {
         sb.append(" ");
       }*/
         //sb.append(" ░│\n");
       addMissingSpace(sb, maxLength-3);
     }
   });
   
   var nonNullPrices = totalPrice.keySet().stream().filter(color -> totalPrice.get(color) != 0) .count();
   for (var line = 4 - nonNullPrices; line > 0; line--) {
     sb.append("│           ░│\n");
   }
   
   return sb.toString();
 }
 
 /**
  * 
  * @return
  */
 private String colorToCardString(Color color) {
   var sb = new StringBuilder("│ ");
   var colorText = color.toString();
   
   sb.append(colorText);
   
   /*for (var i = 8 - colorText.length(); i > 0; i--) {
     sb.append(" ");
   }
   
   sb.append("  ░│\n");*/
   addMissingSpace(sb, colorText.length());
   return sb.toString();
 }
 
 /**
  * 
  * @return
  */
 private String prestigeToCardString(int prestigePoints) {
  var sb = new StringBuilder("│         ");
  
  if (prestigePoints != 0) {
    sb.append(prestigePoints);
  } else {
    sb.append(" ");
  }
  
  sb.append(" ░│\n");
  
  
   return sb.toString();
 }
 
 /*
  * 
  */
 default String cardToString(Color color, int prestigePoints, EnumMap<Color, Integer> totalPrice) {
   var sb = new StringBuilder("╭────────────╮\n");
   
   sb.append(colorToCardString(color))
     .append(prestigeToCardString(prestigePoints))
     .append("├────────────┤\n")
     .append(priceToCardString(totalPrice));
   
   return sb.append("╰────────────╯").toString();
 }

	
	void render(Graphics2D graphic, int x, int y);
}
