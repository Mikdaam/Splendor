package fr.uge.splendor.displayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import fr.uge.splendor.action.Action;
import fr.uge.splendor.action.ActionType;
import fr.uge.splendor.board.Board;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.deck.TokenDeck;
import fr.uge.splendor.player.Player;

/**
 * This class represents a Displayer meant to display a Game on the console.
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
 */
public final class ConsoleDisplayer implements Displayer {
  /* -- DISPLAY METHODS -- */
  
  /**
   * This method displays an array of Players on the console.
   * 
   * @param players
   * @param colors - the list of cards colors allowed in the game.
   */
  private void displayPlayers(Player[] players, List<Color> colors) {
    Arrays.stream(players).forEach(player -> System.out.println(player.toString(colors)));
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
   * This method displays a TokenDeck on the console.
   * 
   * @param tokens - the TokenDeck to display.
   */
  private void displayTokenDecks(TokenDeck tokens) {
    System.out.println(tokens);
  }
  
  /**
   * This method displays a Board on the console.
   * 
   * @param board - the Board to display
   */
  private void displayBoard(Board board) {
    System.out.println(board.toString());
  }
  
  /**
   * This method displays the different actions with an assigned number on the console.
   * 
   * @param actions - the Actions to display.
   */
  private void displayActions(Action[] actions) {
    for (var i = 0; i < actions.length; i++) {
      System.out.println((i + 1) + ": " + actions[i]);
    }
  }
  
  /**
   * This method displays a Game on the console, with its players, its CardDecks, it TokenDeck and its Board.
   * 
   * @param players - the array of Players to display.
   * @param cardDecks - the array of CardDecks to display.
   * @param tokenDecks - the TokenDeck to display.
   * @param gameBoard - the Board to display.
   * @param colors - the list of cards colors allowed in the game.
   */
  @Override
  public void display(Player[] players, CardDeck[] cardDecks, Board noblesCards, TokenDeck tokenDecks, Board gameBoard, List<Color> colors) {
    displayPlayers(players, colors);
    displayCardDecks(cardDecks);
    displayBoard(noblesCards);
    displayBoard(gameBoard);
    displayTokenDecks(tokenDecks);
  }
  
  /**
   * This method displays the error for an action on the console, mostly caused by a player's mistake.
   * 
   * @param message - the message to display to the player.
   */
  public void displayActionError(String message) {
    System.out.println("Error while doing the action: " + message);
  }
  
  
  public void displayWinner(Player[] players, int winnerID) {
    if (winnerID > players.length) {
      throw new IllegalArgumentException("The winner doesn't exist");
    }
    
    System.out.println(players[winnerID - 1].name() + " has won the game! Congratulations!");
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
  public int[] getCoordinates() {
    var coordinates = new int[2];
    var scanner = new Scanner(System.in);
    
    System.out.println("Enter your card's row number (starting from zero, from bottom to top): ");
    var row = scanner.nextInt();
    System.out.println("Enter your card's column number (starting from zero, from left to right): ");
    var col = scanner.nextInt();
    
    coordinates[0] = row;
    coordinates[1] = col;
    
    return coordinates;
  }
  
  /**
   * This method gets and returns the player's input for a unique Color.
   * 
   * @return the Color chosen by the user.
   */
  @Override
  public Color getUniqueColor() {
    var scanner = new Scanner(System.in);
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
   * This method asks the player for their action and returns the ActionType for the Action they've chosen.
   * 
   * @param actions - the array of Actions possible.
   * @param name - the player's name.
   */
  @Override
  public ActionType getPlayerAction(Action[] actions, String name) {
    displayActions(actions);
    System.out.print(name + ", enter your action: ");
    
    var scanner = new Scanner(System.in);
    var opt = scanner.next();
    
    return switch(opt) {
             case "1" -> ActionType.THREE_TOKENS;
             case "2" -> ActionType.TWO_TOKENS;
             case "3" -> ActionType.BUY_CARD_BOARD;
             //case "4" -> ActionType.RESERVE_CARD_BOARD;
             default -> ActionType.UNKNOWN;
    };
  }  
}
