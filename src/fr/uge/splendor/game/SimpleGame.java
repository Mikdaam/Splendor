package fr.uge.splendor.game;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

import fr.uge.splendor.board.Board;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.deck.TokenDeck;
import fr.uge.splendor.displayer.ConsoleDisplayer;
import fr.uge.splendor.displayer.Displayer;
import fr.uge.splendor.utils.*;
import fr.uge.splendor.level.Level;
import fr.uge.splendor.player.Player;

public class SimpleGame implements Game {
  private final Board board;
  private final CardDeck[] decks;
  private final TokenDeck tokens;
  private final Player[] players;
  
  private final Displayer displayer;
  
  
  public SimpleGame() {
    this.board = new Board(3, 4);
    this.decks = new CardDeck[3];
    this.tokens = new TokenDeck();
    this.players = new Player[2];
    this.displayer = new ConsoleDisplayer();
  }
  
  public void initGame() throws IOException {
    Game.setupCards();
  }
  
  public void buyCard() {
    
  }
  
  public void takeToken() {
    
  }
  
  public void doTour() {
    /*
     * Switch actions :
     * 
     * 
     * */
  }
  
  public void playersTour() {
    /*forEach sur player
     * 
     * -> doTour(player);
     * */
  }
 
}
