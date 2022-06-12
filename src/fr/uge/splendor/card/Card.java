package fr.uge.splendor.card;

import java.awt.Graphics2D;
import java.util.HashMap;

import fr.uge.splendor.color.*;
import fr.uge.splendor.level.Level;
import fr.umlv.zen5.ApplicationContext;

public interface Card {
	
	Level level();
	
	Color color();
	
	int prestigePoint();
	
	HashMap<Color, Integer> price();
	
	void render(Graphics2D graphic, int x, int y);
}
