package fr.uge.splendor.displayer;

import java.util.Arrays;

import fr.uge.splendor.board.Board;
import fr.uge.splendor.card.Card;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.deck.TokenDeck;
import fr.uge.splendor.game.Game;
import fr.uge.splendor.player.Player;

public final class ConsoleDisplayer implements Displayer {
  
  public void display(Game game) {
    System.out.println("I'm printing the game");
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
  private void displayTokenDecks(TokenDeck[] tokens) {
    Arrays.stream(tokens).forEach(token -> System.out.println(token));
  }
  
  private void displayBord(Board gameBord) {
    System.out.println(gameBord.toString());
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
    
    String subactions[] = {
        "- Prendre les jetons : ",
        "- Choisir les colonnes de la carte : "
    };
    
    /**
     * TODO: Must be finishied this nigth 
     */
    Arrays.stream(actions).forEach(action -> System.out.println(action));
  }
  
  public void getUserAction() {
    
  }
  
}
