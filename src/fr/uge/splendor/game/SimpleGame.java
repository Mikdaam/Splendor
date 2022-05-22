package fr.uge.splendor.game;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import fr.uge.splendor.board.Board;
import fr.uge.splendor.card.DevelopmentCard;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.deck.TokenDeck;
import fr.uge.splendor.displayer.ConsoleDisplayer;
import fr.uge.splendor.displayer.Displayer;
import fr.uge.splendor.level.Level;
import fr.uge.splendor.player.HumanPlayer;
import fr.uge.splendor.player.Player;
import fr.uge.splendor.token.BaseToken;
import fr.uge.splendor.action.ActionType;

public class SimpleGame implements Game {
	/*TODO: Add an HashMap to the player deck*/
  private final Board board;	 /* Model */
  private final CardDeck[] decks;
  private final TokenDeck tokens;
  private final Player[] players;
  
  private final Displayer displayer; /* View */
  
  public SimpleGame() {
	  /*TODO: Clean code for the constructor*/
    this.board = new Board(1, 4);
    this.decks = new CardDeck[1];
    this.tokens = new TokenDeck();
    this.players = new Player[2];
    this.displayer = new ConsoleDisplayer();
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
      players[i].removeTokensColor(Color.GOLD);
    }
  }
   
  private void initCardDecks() throws IOException {
    Path pathOfFile = Path.of("res").resolve("base_game_cards.csv");
    decks[0] = Game.setupCards(pathOfFile);
  }
   
  private void initTokens() {
    tokens.add(Map.of(Color.DIAMOND, 4, Color.EMERALD, 4, Color.ONYX, 4, Color.RUBY, 4, Color.SAPPHIRE, 4));
    tokens.removeColor(Color.GOLD);
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
  
  public Displayer getDisplayer() {
		return displayer;
	}
  
  public void buyCard(int playerID) {
    var coordinates = displayer.getCoordinates();
    
    if (coordinates[0] != 0) {
      throw new IllegalArgumentException("You've entered a wrong row number");
    }
    if (coordinates[1] < 0 || coordinates[1] >= 4) {
      throw new IllegalArgumentException("You've entered a wrong column number");
    }
    
    var card = board.remove(coordinates[0], coordinates[1]);
    if (players[playerID].canBuyCard(card)) {
      tokens.add(players[playerID].buyCard(card));
      board.add(decks[0].removeFirstCard(), coordinates[0], coordinates[1]);
    }
    else {
      board.add(card, coordinates[0], coordinates[1]);
      doTour(playerID);
    }
  }
  
  /*public void doActionA() {
		
	}*/
  
  public void takeThreeTokens(int playerID) {
    var colors = displayer.getThreeColor();
    
    for (var color : colors) {
    	if (color == Color.UNKNOWN || tokens.getColorNumber(color) <= 0) {
    		doTour(playerID);
    		return;
    	}
    }
    
    for (var color : colors) {
    	if(players[playerID].getNumberOfTokens() < 10) {
    		players[playerID].takeToken(new BaseToken(color));
    		tokens.remove(Map.of(color, 1));
    	}			
		}
  }
  
  public void takeTwoTokens(int playerID) {
    var color = displayer.getUniqueColor();
    
    if (color == Color.UNKNOWN) {
      doTour(playerID);
      return;
    }
    
    var toTake = 2;
    if (tokens.getColorNumber(color) < 4) {
      toTake = 1;
    }
    
    var given = 0;
    while(given < toTake && players[playerID].getNumberOfTokens() < 10) {
      players[playerID].takeToken(new BaseToken(color));
      given++;
    }
    
    tokens.remove(Map.of(color, given));
    
    if (given == 0) {
      doTour(playerID);
    }
  }
  
  private void doTour(int playerID) {
    //displayer.display(players, decks, tokens, board);
    /*Display which player's turn it is*/
    
    switch(displayer.getPlayerAction(players[playerID].name())) {
      case THREE_TOKENS -> takeThreeTokens(playerID);
      case TWO_TOKENS -> takeTwoTokens(playerID);
      case BUY_CARD -> buyCard(playerID);
      default -> this.doTour(playerID);
    }
  }
  
  private int getMaxPrestige() {
    return Arrays.stream(players)
                 .map(player -> player.prestigePoints())
                 .max(Integer::compare)
                 .orElse(0);
  }
  
  private int getWinner() {
    var maxPrestige = this.getMaxPrestige();
    
    return Arrays.stream(players)
                 .filter(player -> player.prestigePoints() == maxPrestige)
                 .map(player -> player.id())
                 .reduce(0, Integer::sum);
  }
  
  public int run() {
    var playerID = 0;    
    
    while (getMaxPrestige() < 15) {
    	displayer.display(players, decks, tokens, board);
      doTour(playerID);
      playerID = (playerID + 1) % players.length;
    }
    
    /*LAST RUN*/
    for(var i = 0; i < players.length; i++) {
    	displayer.display(players, decks, tokens, board);
      doTour(playerID);
      playerID = (playerID + 1) % players.length;
    }
    
    return getWinner();
  }
 
}
