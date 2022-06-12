package fr.uge.splendor.main;

import java.io.IOException;
import java.util.Scanner;

import fr.uge.splendor.displayer.ConsoleDisplayer;
import fr.uge.splendor.displayer.GraphicDisplayer;
import fr.uge.splendor.game.*;

public class Main {
  public static void main(String[] args) throws IOException {  	
   	Game game;
   	String gameMode, playersNumber, displayMode;
   	
   	for (int i = 0; i < args.length; i++) {
			if ((args[i].equals("--mode")) && (i+1 < args.length)) {
				gameMode = args[i+1];
			}
			
			if ((args[i].equals("--players")) && (i+1 < args.length)) {
				playersNumber = args[i+1];
			}
			
			if ((args[i].equals("--display")) && (i+1 < args.length)) {
				displayMode = args[i+1];
			}
		}
   	
   	Scanner mainInput = new Scanner(System.in);
   	
   	System.out.println("1. SimpleGame");
   	System.out.println("2. NormalGame");
   	System.out.println("Choose the version of the game: ");
    var gameChoice = mainInput.nextInt();
  	
    if(gameChoice == 1) {
    	 game = new SimpleGame(true);
    } else {
    	 game = new NormalGame(2);
    }
    
    game.initGame();
    game.run();
    
    mainInput.close();
  }
}
