package fr.uge.splendor.game;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import fr.uge.splendor.board.Board;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.deck.TokenDeck;
import fr.uge.splendor.displayer.ConsoleDisplayer;
import fr.uge.splendor.displayer.Displayer;
import fr.uge.splendor.player.HumanPlayer;
import fr.uge.splendor.player.Player;

public class SimpleGame implements Game {
	/*TODO: Add an HashMap to the player deck*/
  private final Board board;
  private final CardDeck[] decks;
  private final TokenDeck tokens;
  private final Player[] players;
  
  private final Displayer displayer;
  
  public SimpleGame() {
	  /*TODO: Clean code for the constructor*/
    this.board = new Board(1, 4);
    this.decks = new CardDeck[1];
    this.tokens = new TokenDeck();
    this.players = new Player[2];
    this.displayer = new ConsoleDisplayer();
  }
  
  public void initGame() throws IOException {
  	initCardDecks();
	  initBoard();
	  initPlayers();
	  initTokens();
  }
  
  public void displayGame() {
		displayer.display(players, decks, tokens, board);
	}
  
  private void initBoard() {
	  for (int i = 0; i < board.rows(); i++) {
			for (int j = 0; j < board.colums(); j++) {
				decks[0].shuffleCardDeck();
				board.add(decks[0].removeFirstCard(), i, j);
			}
		}
  }
  
  private void initPlayers() {
		for (int i = 0; i < players.length; i++) {
			players[i] = new HumanPlayer(i + 1, "Player "+(i+1));
		}
	}
  
  private void initCardDecks() throws IOException {
  	Path pathOfFile = Path.of("res").resolve("base_game_cards.csv");
		decks[0] = Game.setupCards(pathOfFile);
	}
  
  private void initTokens() {
		tokens.add(Map.of(Color.DIAMOND, 4, Color.EMERALD, 4, Color.ONYX, 4, Color.NOBLE, 4, Color.RUBY, 4, Color.SAPPHIRE, 4));
		tokens.removeColor(Color.GOLD);
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
