package fr.uge.splendor.displayer;

import java.util.List;

import fr.uge.splendor.action.Action;
import fr.uge.splendor.action.ActionType;
import fr.uge.splendor.board.Board;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.deck.TokenPurse;
import fr.uge.splendor.level.Level;
import fr.uge.splendor.player.Player;


/**
 * This interface represents the Displayer used to display the Game. Each Displayer can implement it's own way to display a game.
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
 *
 */
public sealed interface Displayer permits ConsoleDisplayer, GraphicDisplayer {
  /**
   * This method displays a Game, with its players, its CardDecks, it TokenDeck and its Board.
   * 
   * @param players - the array of Players to display.
   * @param cardDecks - the array of CardDecks to display.
   * @param tokenDecks - the TokenDeck to display.
   * @param gameBoard - the Board to display.
   * @param colors - the list of cards colors allowed in the game.
   */
  void display(Player[] players, CardDeck[] cardDecks, Board noblesCards, TokenPurse tokenDecks, Board gameBoard, List<Color> colors);
  
  /**
   * This method displays the error for an action, mostly caused by a player's mistake.
   * 
   * @param message - the message to display to the player.
   */
  public void displayActionError(String message);
  
  /**
   * This method displays the winner of the game.
   * 
   * @param players - the array of players of the Game.
   * @param winnerID - the winner's ID.
   */
  void displayWinner(Player[] players, int winnerID);
  
  /**
   * This method clears the output.
   */
  void clear();
  
  /**
   * This method gets and returns the player's inputs for a card's coordinates.
   * 
   * @return A couple of coordinates stored into an int array.
   */
  int[] getCoordinates();
  
  /**
   * This method gets and returns the player's input for a unique Color.
   * 
   * @return the Color chosen by the user.
   */
  Color getUniqueColor();
  
  /**
   * This method gets and returns the user's input for a list of three Colors.
   * 
   * @return the List of Colors chosen by the user.
   */
  List<Color> getThreeColor();
  
  /**
   * This method gets and returns the user's input for a deck level.
   * 
   * @return the deck's level chose by the user.
   */
  Level getDeckLevel();
  
  /**
   * This method asks the player for their action and returns the ActionType for the Action they've chosen.
   * 
   * @param actions - the array of Actions possible.
   * @param name - the player's name.
   */
  ActionType getPlayerAction(Action[] actions, String name);
  
}
