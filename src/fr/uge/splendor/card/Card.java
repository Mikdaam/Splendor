package fr.uge.splendor.card;

import java.util.HashMap;

import fr.uge.splendor.color.*;
import fr.uge.splendor.level.Level;

public interface Card {
	
	Level level();
	
	Color color();
	
	int prestigePoint();
	
	HashMap<Color, Integer> price();
	
	static String emptyCardToString() {
	  var sb = new StringBuilder();
	  
	  for (var i = 0; i < 8; i++) {
	    sb.append("              \n");
	  }
	    
	  return sb.append("              ").toString();
	}
}
