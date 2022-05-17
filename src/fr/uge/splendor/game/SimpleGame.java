package fr.uge.splendor.game;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

import fr.uge.splendor.board.Board;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.deck.TokenDeck;
import fr.uge.splendor.displayer.AsciiDisplayer;
import fr.uge.splendor.displayer.Displayer;
import fr.uge.splendor.utils.*;
import fr.uge.splendor.level.Level;
import fr.uge.splendor.player.Player;

public class SimpleGame implements Game {
  private final Board board;
  private final HashMap<Level, CardDeck> decks;
  private final TokenDeck tokens;
  private final Player[] players;
  private final Displayer displayer;
  
  public SimpleGame() {
    this.board = null;
    this.decks = null;
    this.tokens = null;
    this.players = null;
    this.displayer = new AsciiDisplayer();
  }
  
  
  public void shuffleCard() {
    
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
  
  @Override
  public CardDeck setupCards() throws IOException {
    return FileLoader.createCards(Path.of("res").resolve("base_game_cards.csv"));
  }
}
