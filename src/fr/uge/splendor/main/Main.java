package fr.uge.splendor.main;

import java.io.IOException;
import java.util.Scanner;

import fr.uge.splendor.displayer.ConsoleDisplayer;
import fr.uge.splendor.displayer.Displayer;
import fr.uge.splendor.displayer.GraphicDisplayer;
import fr.uge.splendor.game.*;

/**
 * This is the Main class of the game. It parses the arguments given by the user
 * and launches the right game mode with the right parameters.
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 *
 */
public class Main {
  
  /**
   * This launches the game in graphical mode
   * @param game - the game to launch
   */
  private static void graphicLaunch(Game game) {
    /*Application.run(Color.BLACK, context -> {
      game.run();
    });*/
    System.out.println("This feature will be available soon... Please play in console mode for now.");
    System.out.println("Closing the game.");
    System.exit(1);
  }
  
  /**
   * This launches the game in console mode
   * @param game - the game to launch
   */
  private static void consoleLaunch(Game game) {
    game.run();
  }
  
  /**
   * Creates a Displayer depending on the given mode
   * @param displayMode - the display mode
   * @param mainInput - the scanner to be used for the ConsoleDisplayer
   * @return The Displayer created
   */
  private static Displayer createDisplayer(String displayMode, Scanner mainInput) {
    return switch(displayMode) {
      case "console" -> new ConsoleDisplayer(mainInput);
      case "graphical" -> new GraphicDisplayer();
      default -> throw new IllegalArgumentException("You have not entered a right displayer mode...");
    };
  }
  
  /**
   * Creates a Game with a mode, a number of players and a displayer
   * @param gameMode - the game mode
   * @param players - the number of players
   * @param displayer - the displayer
   * @return The Game created with the given parameters
   */
  private static Game createGame(String gameMode, int players, Displayer displayer) {
    return switch(gameMode) {
      case "simple" -> new SimpleGame(displayer);
      case "normal" -> new NormalGame(displayer, players);
      default -> throw new IllegalArgumentException("You have not entered a right game mode...");
    };
  }
  
  /**
   * Launches the game correctly
   * @param displayMode - the display mode
   * @param gameMode - the game mode
   * @param playersNumber - the number of players
   * @param mainInput - the main input scanner
   * @throws IOException - if the file for the cards has not been found
   */
  private static void launch(String displayMode, String gameMode, int playersNumber, Scanner mainInput) throws IOException {
    var displayer = createDisplayer(displayMode, mainInput);
    var game = createGame(gameMode, playersNumber, displayer);
    
    game.initGame();
    
    if (displayMode.equals("console")) {
      consoleLaunch(game);
    } else {
      graphicLaunch(game);
    }
    
    mainInput.close();
  }
  
  /**
   * Checks if the arguments given by the user are correct.
   * @param displayMode - the display mode argument
   * @param gameMode - the game mode argument
   * @param playersNumber - the players number argument
   * @param mainInput - the scanner to close in case an argument is not valid
   */
  private static void checkArguments(String displayMode, String gameMode, int playersNumber, Scanner mainInput) {
    if (displayMode.isEmpty()) {
      mainInput.close();
      throw new IllegalArgumentException("You have not entered a display mode... Closing the game.");
    } else if (gameMode.isEmpty()) {
      mainInput.close();
      throw new IllegalArgumentException("You have not entered a game mode... Closing the game.");
    } else if (gameMode.equals("normal") && playersNumber == 0) {
      mainInput.close();
      throw new IllegalArgumentException("You have not entered a right number of players for the normal mode... Closing the game.");
    }
  }
  
  /**
   * Parses the arguments and launches the right game mode with the right parameters.
   * 
   * @param args - the arguments given by the user on the command line.
   * @throws IOException - if the file containing the cards has not been found
   */
  public static void main(String[] args) throws IOException {
    var mainInput = new Scanner(System.in);
    var gameMode = "";
    var playersNumber = 0;
    var displayMode = "";
    
    for (int i = 0; i < args.length; i++) {
      if ((args[i].equals("--mode")) && (i+1 < args.length)) {
          gameMode = args[i+1];
      }
      
      if ((args[i].equals("--players")) && (i+1 < args.length)) {
          playersNumber = Integer.parseInt(args[i+1]);
      }
      
      if ((args[i].equals("--display")) && (i+1 < args.length)) {
          displayMode = args[i+1];
      }
    }
    
    checkArguments(displayMode, gameMode, playersNumber, mainInput);
    launch(displayMode, gameMode, playersNumber, mainInput);
  }
}
