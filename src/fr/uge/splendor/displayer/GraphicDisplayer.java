package fr.uge.splendor.displayer;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.List;

import fr.uge.splendor.action.Action;
import fr.uge.splendor.action.ActionType;
import fr.uge.splendor.board.Board;
import fr.uge.splendor.card.Coordinate;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.deck.TokenPurse;
import fr.uge.splendor.level.Level;
import fr.uge.splendor.player.Player;
import fr.umlv.zen5.ApplicationContext;

public final class GraphicDisplayer implements Displayer{

	@Override
	public void display(Player[] players, CardDeck[] cardDecks, Board noblesCards, TokenPurse tokenDecks, Board gameBoard,
			List<Color> colors) {
	}

	@Override
	public void displayActionError(String message) {

	}

	@Override
	public void displayWinner(Player[] players, int winnerID) {

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
	public ActionType getPlayerAction(HashMap<ActionType, Action> actions, String name) {
		return null;
	}
	
	private void draw(Graphics2D graphics) {
		graphics.setColor(java.awt.Color.BLUE);
		graphics.fill(new Rectangle2D.Float(0, 0, 50, 50));
	}
	
	public static void draw(ApplicationContext context, GraphicDisplayer view) {
		context.renderFrame(graphics -> view.draw(graphics));
	}

}
