package fr.uge.splendor.displayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import fr.uge.splendor.action.ActionType;
import fr.uge.splendor.board.Board;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.deck.TokenDeck;
import fr.uge.splendor.player.Player;

public final class ConsoleDisplayer implements Displayer {
  
	/*public ConsoleDisplayer(Player[] players, CardDeck[] cardDecks, TokenDeck[] tokenDecks, Board gameBoard) {
		display(players, cardDecks, tokenDecks, gameBoard);
	}*/
	
  public void display(Player[] players, CardDeck[] cardDecks, TokenDeck tokenDecks, Board gameBoard) {
    System.out.println("I'm printing the game");
    displayPlayers(players);
		displayCardDecks(cardDecks);
		displayTokenDecks(tokenDecks);
		displayBoard(gameBoard);
  }
  
  /* TODO: Add a clear to clean the console */
  public void clear() {
    System.out.print("\033[H\033[2J");  
    System.out.flush();
	 }
  
  private void displayPlayers(Player[] players) {
    Arrays.stream(players)
    .forEach(player -> System.out.println(player));
  }
  
  /**
   * @implNote: we will add a better version for this method using a map...
   * @param decks
   */
  private void displayCardDecks(CardDeck[] decks) {
    Arrays.stream(decks).forEach(deck -> System.out.print(deck));
  }
  
  /**
   * @implNote Same thing here
   * @param tokens
   */
  private void displayTokenDecks(TokenDeck tokens) {
    System.out.println(tokens);
  }
  
  private void displayBoard(Board gameBoard) {
    System.out.println(gameBoard.toString());
  }
  
  /* The above code is temporary i will refactor it later
   * TODO: Refactor this method
   */
  private void displayActions() {
    String actions[] = {
        "1. Prendre 3 jetons pierre précieuse de couleur différente.",
        "2. Prendre 2 jetons pierre précieuse de la même couleur.",
        "3. Acheter 1 carte développement face visible au centre de la table ou préalablement réservée.",
        "4. Réserver 1 carte développement et prendre 1 or (joker)."
    };
    
    String boughtActions[] = {
        "- Prendre les jetons : ",
        "- Carte FV ou FC",
        "- Carte reservé ou carte FV"
    }; 
    
    /**
     * TODO: Must be finished this night 
     */
    Arrays.stream(actions).forEach(action -> System.out.println(action));
  }
  
  
  public int[] getCoordinates() {
    var coordinates = new int[2];
    var scan = new Scanner(System.in);
    
    System.out.println("Enter your card's row number (starting from zero, from bottom to top): ");
    var row = scan.nextInt();
    System.out.println("Enter your card's column number (starting from zero, from left to right): ");
    var col = scan.nextInt();
    
    coordinates[0] = row;
    coordinates[1] = col;
    
    return coordinates;
  }
  
  public Color getUniqueColor() {
    var scan = new Scanner(System.in);
    System.out.println("Enter the color you want to take 2 tokens from: ");
    var colorText = scan.nextLine();
    
    return switch (colorText.toUpperCase()) {
           case "DIAMOND" -> Color.DIAMOND;
           case "EMERALD" -> Color.EMERALD;
           case "ONYX" -> Color.ONYX;
           case "RUBY" -> Color.RUBY;
           case "SAPPHIRE" -> Color.SAPPHIRE;
           default -> Color.UNKNOWN;
    };
    
  }
  
  public List<Color> getThreeColor() {
  	var colorList = new ArrayList<Color>();
  	var scan = new Scanner(System.in);
    var i = 0;
    
    while (i < 3) {
    	System.out.println("Enter the color N°" + (i + 1) + ": ");
      var colorText = scan.nextLine();
      switch (colorText.toUpperCase()) {
			      case "DIAMOND" -> colorList.add(Color.DIAMOND);
			      case "EMERALD" -> colorList.add(Color.EMERALD);
			      case "ONYX" -> colorList.add(Color.ONYX); 
			      case "RUBY" -> colorList.add(Color.RUBY);
			      case "SAPPHIRE" -> colorList.add(Color.SAPPHIRE);
			      default -> colorList.add(Color.UNKNOWN); /*We could raise an exception here ?*/
			};
			i++;
		}
    
    return colorList;
	}
  
  public ActionType getPlayerAction(String name) {
    
    this.displayActions();
    System.out.print(name + ", enter your action: ");
    
    var scanner = new Scanner(System.in);
    var opt = scanner.next();
    
    return switch(opt) {
             case "1" -> ActionType.THREE_TOKENS;
             case "2" -> ActionType.TWO_TOKENS;
             case "3" -> ActionType.BUY_CARD;
             case "4" -> ActionType.RESERVE_CARD;
             default -> ActionType.UNKNOWN;
    };
  }
  
}
