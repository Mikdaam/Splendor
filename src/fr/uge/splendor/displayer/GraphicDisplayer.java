package fr.uge.splendor.displayer;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import fr.uge.splendor.action.GameAction;
import fr.uge.splendor.action.ActionType;
import fr.uge.splendor.board.Board;
import fr.uge.splendor.card.Coordinate;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.deck.TokenPurse;
import fr.uge.splendor.game.GameData;
import fr.uge.splendor.level.Level;
import fr.uge.splendor.player.Player;
import fr.uge.splendor.utils.Utils;
import fr.umlv.zen5.ApplicationContext;

public final class GraphicDisplayer implements Displayer{

	/**
   * This method displays an array of Players on the console.
   * 
   * @param players
   * @param colors - the list of cards colors allowed in the game.
   */
  private void displayPlayers(ArrayList<Player> players, List<Color> colors) {
  	
  }
  
  /**
   * This method displays an array of CardDecks on the console.
   * 
   * @implNote: we will add a better version for this method using a map...
   * @param decks - the CardDecks to display.
   */
  private void displayCardDecks(CardDeck[] decks) {
    Arrays.stream(decks).forEach(deck -> System.out.print(deck));
  }
  
  /**
   * This method displays an array of CardDecks on the console.
   * 
   * @implNote: we will add a better version for this method using a map...
   * @param decks - the CardDecks to display.
   */
  private String cardDecksToString(EnumMap<Level, CardDeck> decks) {
    var sb = new StringBuilder();
    var decksLevelsList = decks.keySet().stream().sorted().toList();
    
    for (var i = decksLevelsList.size()-1; i >= 0; i--) {
		  	 sb.append(decks.get(decksLevelsList.get(i)));
		  }
    
    return sb.toString();
  }
  
  /**
   * This method displays a TokenDeck on the console.
   * 
   * @param tokens - the TokenDeck to display.
   */
  private void displayTokenDecks(TokenPurse tokens) {
    System.out.println(tokens);
  }
  
  /**
   * This method displays a Board on the console.
   * 
   * @param board - the Board to display
   */
  private void displayBoard(Board board) {
  	
  }
  
  private void displayerDecksAndBoard(EnumMap<Level, CardDeck> decks, Board board) {
  	
  }
  
  /**
   * This method displays the different actions with an assigned number on the console.
   * 
   * @param actions - the Actions to display.
   */
  private void displayActions(EnumMap<ActionType, GameAction> actions) {
  	Objects.requireNonNull(actions, "Actions can't be null");
  	
  	int i = 0;
    for (var key : actions.keySet()) {
    	System.out.println((i + 1) + ": " + actions.get(key));
    	i++;
		}
  }
  
  
  @Override
  public void display(GameData gameData, List<Color> colors) {
    displayPlayers(gameData.players(), colors);
    displayBoard(gameData.noblesCards());
    displayerDecksAndBoard(gameData.decks(), gameData.board());
    displayTokenDecks(gameData.tokens());
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
		context.renderFrame(graphics -> view.draw(graphics));
	}

}
