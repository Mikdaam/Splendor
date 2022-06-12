package fr.uge.splendor.displayer;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import fr.uge.splendor.action.GameAction;
import fr.uge.splendor.action.ActionType;
import fr.uge.splendor.card.Coordinate;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.game.GameData;
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
   * This method displays a game's data.
   * 
   * @param gameData - the game's data to display
   * @param colors - the list of cards colors allowed in the game.
   */
  void display(GameData gameData, List<Color> colors);
  
  /**
   * This method displays the error for an action, mostly caused by a player's mistake.
   * 
   * @param message - the message to display to the player.
   */
  public void displayActionError(String message);
  
  /**
   * This method displays the winner of the game.
   * 
   * @param players - the list of players of the Game.
   * @param winnerID - the winner's ID.
   */
  void displayWinner(ArrayList<Player> players, int winnerID);
  
  /**
   * This method clears the output.
   */
  void clear();
  
  
  /**
   * This method gets and returns the player's inputs for a card's coordinates.
   * 
   * @return A couple of coordinates stored into an int array.
   */
  Coordinate getCoordinates();
  
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
   * @param actions - the map of Actions possible.
   * @param name - the player's name.
   */
  ActionType getPlayerAction(EnumMap<ActionType, GameAction> actions, String name);
  
  /**
   * This method closes the resources before quitting the program
   */
  void close();
}
