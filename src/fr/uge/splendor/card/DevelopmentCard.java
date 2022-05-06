package fr.uge.splendor.card;

import java.util.Objects;
import java.util.HashMap;

import fr.uge.splendor.level.*;
import fr.uge.splendor.color.*;

public record DevelopmentCard(int prestigePoint, 
                              HashMap<Color, Integer> price,
                              Level level,
                              Color color,
                              String image)
                              implements Card {
	
	public DevelopmentCard {
		Objects.requireNonNull(price, "Price can't be null");
		Objects.requireNonNull(level, "Level can't be null");
		Objects.requireNonNull(image, "Image can't be null");
	}
}
