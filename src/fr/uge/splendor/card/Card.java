package fr.uge.splendor.card;

import java.util.HashMap;

import fr.uge.splendor.color.*;
import fr.uge.splendor.game.level.Level;

public interface Card {
	
	int prestigePoint();
	
	HashMap<Color, Integer> price();
	
	Color color();
	
	Level level();
}
