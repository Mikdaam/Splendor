package fr.uge.splendor.displayer;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;

import fr.uge.splendor.action.GameAction;
import fr.uge.splendor.action.ActionType;
import fr.uge.splendor.card.Coordinate;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.game.GameData;
import fr.uge.splendor.level.Level;
import fr.uge.splendor.player.Player;
import fr.umlv.zen5.ApplicationContext;

public final class GraphicDisplayer implements Displayer{

 	@Override
 	public void display(GameData gameData, List<Color> colors) {
 	  
 	}
 
 	@Override
 	public void displayActionError(String message) {
 
 	}
 
 	@Override
 	public void displayWinner(ArrayList<Player> players, int winnerID) {
 
 	}
 
 	@Override
 	public void clear() {
 
 	}
 
 	@Override
 	public Coordinate getCoordinates() {
 		return null;
 	}
 
 	@Override
 	public Color getUniqueColor() {
 		return null;
 	}
 
 	@Override
 	public List<Color> getThreeColor() {
 		return null;
 	}
 
 	@Override
 	public Level getDeckLevel() {
 		return null;
 	}
 
 	@Override
 	public ActionType getPlayerAction(EnumMap<ActionType, GameAction> actions, String name) {
 		return null;
 	}
 	
 	private void draw(Graphics2D graphics) {
 		graphics.setColor(java.awt.Color.BLUE);
 		graphics.fill(new Rectangle2D.Float(0, 0, 50, 50));
 	}
 	
 	public static void draw(ApplicationContext context, GraphicDisplayer view) {
 	  Objects.requireNonNull(context);
 	  Objects.requireNonNull(view);
 		 context.renderFrame(graphics -> view.draw(graphics));
 	}
 	
 	/**
   * This method closes the resources before quitting the program
   */
  public void close() {}
}
