package fr.uge.splendor.card;

import java.util.HashMap;

import fr.uge.splendor.color.*;
import fr.uge.splendor.level.*;

public interface Card {
	
	int prestigePoint();
	
	HashMap<Color, Integer> price();
	
	Level level();
	
	String image();
}
