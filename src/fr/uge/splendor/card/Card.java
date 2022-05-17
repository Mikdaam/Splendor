package fr.uge.splendor.card;

import java.util.HashMap;

import fr.uge.splendor.color.*;
import fr.uge.splendor.level.Level;

public interface Card {
	
	Level level();
	
	Color color();
	
	int prestigePoint();
	
	HashMap<Color, Integer> price();
}
