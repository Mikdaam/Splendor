package fr.uge.splendor.main;

import java.io.IOException;
import java.util.Scanner;

import fr.uge.splendor.game.*;

public class Main {
  public static void main(String[] args) throws IOException {  	
   	Game game;
   	Scanner mainInput = new Scanner(System.in);
   	
   	System.out.println("1. SimpleGame");
   	System.out.println("2. NormalGame");
   	System.out.println("Choose the version of the game: ");
    var gameChoice = mainInput.nextInt();
  	
    if(gameChoice == 1) {
    	 game = new SimpleGame();
    } else {
    	 game = new NormalGame(2);
    }
    
    game.initGame();
    game.run();
    
    mainInput.close();
  }
}
