package fr.uge.splendor.displayer;

import java.util.Arrays;

import fr.uge.splendor.board.Board;
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
        "- Prendre 3 jetons pierre précieuse de couleur différente.",
        "- Prendre 2 jetons pierre précieuse de la même couleur.",
        "- Réserver 1 carte développement et prendre 1 or (joker).",
        "- Acheter 1 carte développement face visible au centre de la table ou préalablement réservée."
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
  
  public void getUserAction() {
    
  }
  
}
