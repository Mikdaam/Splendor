package fr.uge.splendor.displayer;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

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

/**
 * This class represents a ConsoleDisplayer meant to display a Game on the console.
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
 */
public final class ConsoleDisplayer implements Displayer {
  private Scanner scanner;
  
  public ConsoleDisplayer(Scanner scanner) {
    Objects.requireNonNull(scanner);
    this.scanner = scanner;
  }
  
  /* -- DISPLAY METHODS -- */
  
  /**
   * This method displays a list of Players on the console.
   * 
   * @param players
   * @param colors - the list of cards colors allowed in the game.
   */
  private void displayPlayers(ArrayList<Player> players, List<Color> colors) {
    players.stream().forEach(player -> System.out.println(player.toString(colors)));
  }
  
  
  /**
   * This method displays a map of CardDecks on the console.
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
   * This method displays a TokenPurse on the console.
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
    if(board.amountOfCards() == 0) {
      return;
    }
    System.out.println(board.toString());
  }
  
  /**
   * This method displays the decks and the board of development cards
   * @param decks - the decks to display
   * @param board - the board to display
   */
  private void displayerDecksAndBoard(EnumMap<Level, CardDeck> decks, Board board) {
    var strings = new String[2];
    strings[0] = cardDecksToString(decks);
    strings[1] = board.toString();
    System.out.println(Utils.computeStringsToLine(strings));
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
  
  /**
   * This method displays a game's data on the console.
   * 
   * @param gameData - the game's data to display
   * @param colors - the list of cards colors allowed in the game.
   */
  @Override
  public void display(GameData gameData, List<Color> colors) {
    Objects.requireNonNull(gameData);
    Objects.requireNonNull(colors);
    
    displayPlayers(gameData.players(), colors);
    displayBoard(gameData.noblesCards());
    displayerDecksAndBoard(gameData.decks(), gameData.board());
    displayTokenDecks(gameData.tokens());
  }
  
  /**
   * This method displays the error for an action on the console, mostly caused by a player's mistake.
   * 
   * @param message - the message to display to the player.
   */
  @Override
  public void displayActionError(String message) {
    Objects.requireNonNull(message);
    System.out.println("Error while doing the action: " + message);
  }
  
  /**
   * This method displays the player who won the run.
   * @param players - the list of players
   * @param winnerID - the winner's ID
   */
  @Override
  public void displayWinner(ArrayList<Player> players, int winnerID) {
    Objects.requireNonNull(players);
    
    if (winnerID > players.size()) {
      throw new IllegalArgumentException("The winner doesn't exist");
    }
    
    System.out.println(players.get(winnerID - 1).name() + " has won the game! Congratulations!");
  }
  
  /**
   * This method clears the console.
   */
  @Override
  public void clear() {
    System.out.print("\033[H\033[2J");  
    System.out.flush();
  }
  
  
  
  /* -- GET USER'S INPUTS -- */
  
  /**
   * This method gets and returns the player's inputs for a card's coordinates.
   * 
   * @return A couple of coordinates stored into an int array.
   */
  @Override
  public Coordinate getCoordinates() {
    System.out.println("Enter your card's level (starting from one, from bottom to top): ");
    var row = scanner.nextInt();
    scanner.nextLine();
    System.out.println("Enter your card's column number (starting from zero, from left to right): ");
    var col = scanner.nextInt();
    scanner.nextLine();
   
    return new Coordinate(row, col);
  }
  
  /**
   * This method gets and returns the player's input for a unique Color.
   * 
   * @return the Color chosen by the user.
   */
  @Override
  public Color getUniqueColor() {
    System.out.println("Enter the color you want: ");
    var colorText = scanner.nextLine();
    return switch (colorText.toUpperCase()) {
           case "DIAMOND" -> Color.DIAMOND;
           case "EMERALD" -> Color.EMERALD;
           case "ONYX" -> Color.ONYX;
           case "RUBY" -> Color.RUBY;
           case "SAPPHIRE" -> Color.SAPPHIRE;
           default -> Color.UNKNOWN;
    };
  }
  
  /**
   * This method gets and returns the user's input for a list of three Colors.
   * 
   * @return the List of Colors chosen by the user.
   */
  @Override
  public List<Color> getThreeColor() {
   	var colorList = new ArrayList<Color>();
   	
    for (var i = 0; i < 3; i++) {
      colorList.add(getUniqueColor());
    }
     
    return colorList;
	 }
  
  /**
   * This method gets and returns the user's input for a deck level.
   * 
   * @return the deck's level chose by the user.
   */
  @Override
  public Level getDeckLevel() {
    System.out.println("Enter the level of the deck: ");
    var level = scanner.nextLine();
    
    return switch (level) {
           case "1" -> Level.LEVEL_1;
           case "2" -> Level.LEVEL_2;
           case "3" -> Level.LEVEL_3;
           default -> Level.UNKNOWN;
    };
  }
  
  /**
   * This method asks the player for their action and returns the ActionType for the Action they've chosen.
   * 
   * @param actions - the array of Actions possible.
   * @param name - the player's name.
   */
  @Override
  public ActionType getPlayerAction(EnumMap<ActionType, GameAction> actions, String name) {
  	 Objects.requireNonNull(actions, "Actions can't be null");
  	 Objects.requireNonNull(name);
  	 
    displayActions(actions);
    System.out.print(name + ", enter your action: ");
    
    var opt = scanner.nextLine();
    
    return switch(opt) {
             case "1" -> ActionType.THREE_TOKENS;
             case "2" -> ActionType.TWO_TOKENS;
             case "3" -> ActionType.BUY_CARD_BOARD;
             case "4" -> ActionType.BUY_RESERVED_CARD;
             case "5" -> ActionType.RESERVE_CARD_BOARD;
             case "6" -> ActionType.RESERVE_CARD_DECK;
             default -> ActionType.UNKNOWN;
    };
  } 
}
