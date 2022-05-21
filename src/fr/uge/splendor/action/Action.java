package fr.uge.splendor.action;

import fr.uge.splendor.game.Game;

public interface Action {

	ActionType type();
	
	//ActionResult applyGraphical();
	
	void applyConsole(Game game);
	
	String toString();
}
