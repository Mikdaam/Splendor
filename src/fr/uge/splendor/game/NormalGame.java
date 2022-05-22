/**
 * 
 */
package fr.uge.splendor.game;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.uge.splendor.board.Board;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.deck.TokenDeck;
import fr.uge.splendor.displayer.ConsoleDisplayer;
import fr.uge.splendor.displayer.Displayer;
import fr.uge.splendor.level.Level;
import fr.uge.splendor.player.HumanPlayer;
import fr.uge.splendor.player.Player;
import fr.uge.splendor.token.BaseToken;

/**
 * 
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 */
public class NormalGame implements Game {
	private final static int NUMBER_OF_TOKEN_PER_COLOR = 7;
	private final static int NUMBER_OF_GOLD_TOKEN = 5;
	private static int NUMBER_OF_TOKEN_MISSING_BY_NUM_OF_PLAYER;
	
	private final Board board;	 /* Model */
	private final CardDeck[] decks;
	private final TokenDeck tokens;
	private final Player[] players;
	
	private final Displayer displayer; /* View */
	
	public NormalGame(int numberOfPlayer) {
		if(numberOfPlayer < 2) {
			throw new IllegalArgumentException("Number of player should be greater than 2.");
		}
		
		this.board = new Board(3, 4);
    this.decks = new CardDeck[3];
    this.tokens = new TokenDeck();
    this.players = new Player[numberOfPlayer];
    this.displayer = new ConsoleDisplayer();
    
    if (numberOfPlayer == 2) {
    	NUMBER_OF_TOKEN_MISSING_BY_NUM_OF_PLAYER = 3;
    } else if (numberOfPlayer == 3) {
    	NUMBER_OF_TOKEN_MISSING_BY_NUM_OF_PLAYER = 2;
    } else {
    	NUMBER_OF_TOKEN_MISSING_BY_NUM_OF_PLAYER = 0;
    }
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
    Path pathOfFile = Path.of("../").resolve("res").resolve("normal_game_cards.csv");
    var cardDeckByLevel = Game.setupCards(pathOfFile).groupByLevel();
    
    /*TODO: change the array to a map*/
    decks[0] = cardDeckByLevel.get(Level.LEVEL_1);
    decks[1] = cardDeckByLevel.get(Level.LEVEL_2);
    decks[2] = cardDeckByLevel.get(Level.LEVEL_3);
  }
  
  private void initTokens() {
  	var numberOfTokenByColor = NUMBER_OF_TOKEN_PER_COLOR - NUMBER_OF_TOKEN_MISSING_BY_NUM_OF_PLAYER;
  	
    tokens.add(Map.of(Color.DIAMOND, numberOfTokenByColor, 
    		Color.EMERALD, numberOfTokenByColor, 
    		Color.ONYX, numberOfTokenByColor, 
    		Color.RUBY, numberOfTokenByColor, 
    		Color.SAPPHIRE, numberOfTokenByColor,
    		Color.GOLD, NUMBER_OF_GOLD_TOKEN));
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
  
  public void takeThreeTokens(int playerID) {
    var colors = displayer.getThreeColor();
    
    if(colors.stream().distinct().count() != 3 || players[playerID].getNumberOfTokens() >= 10) {
    	doTour(playerID);
  		return;
    }
    
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
    
    if (color == Color.UNKNOWN || players[playerID].getNumberOfTokens() == 10) {
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
  
  private List<Integer> getWinner() {
    var maxPrestige = this.getMaxPrestige();
    
    return Arrays.stream(players)
                 .filter(player -> player.prestigePoints() == maxPrestige)
                 .map(player -> player.id())
                 .toList();
  }
  
  public List<Integer> run() {
    var playerID = 0;    
    
    while (getMaxPrestige() < 1) {
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
