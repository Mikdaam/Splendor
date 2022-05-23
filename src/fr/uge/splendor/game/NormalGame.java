/**
 * 
 */
package fr.uge.splendor.game;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
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
import fr.uge.splendor.action.Action;
import fr.uge.splendor.action.BuyCardBoardAction;
import fr.uge.splendor.action.BuyReservedCardAction;
import fr.uge.splendor.action.ReserveCardBoardAction;
import fr.uge.splendor.action.ReserveCardDeckAction;
import fr.uge.splendor.action.ThreeTokensAction;
import fr.uge.splendor.action.TwoTokensAction;

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
	
	/* Model */
	private final Board board;
	private final Board noblesCards;
	private final CardDeck[] decks;
	private final TokenDeck tokens;
	private final Player[] players;
	private final Action[] actions;
	
	private final Displayer displayer; /* View */
	
	public NormalGame(int numberOfPlayers) {
		if(numberOfPlayers < 2 && numberOfPlayers > 4) {
			throw new IllegalArgumentException("Number of player should be greater than 2.");
		}
		
		this.board = new Board(3, 4);
		this.noblesCards = new Board(1, numberOfPlayers + 1);
    this.decks = new CardDeck[3];
    this.tokens = new TokenDeck();
    this.players = new Player[numberOfPlayers];
    this.displayer = new ConsoleDisplayer();
    actions = new Action[6];
    
    if (numberOfPlayers == 2) {
    	NUMBER_OF_TOKEN_MISSING_BY_NUM_OF_PLAYER = 3;
    } else if (numberOfPlayers == 3) {
    	NUMBER_OF_TOKEN_MISSING_BY_NUM_OF_PLAYER = 2;
    } else {
    	NUMBER_OF_TOKEN_MISSING_BY_NUM_OF_PLAYER = 0;
    }
	}
	
	private void initBoard() {
    for (int i = 0; i < board.rows(); i++) {
      for (int j = 0; j < board.colums(); j++) {
        decks[i].shuffleCardDeck();
        board.add(decks[i].removeFirstCard(), i, j);
      }
    }
  }
  
  private void initPlayers() {
    for (int i = 0; i < players.length; i++) {
      players[i] = new HumanPlayer(i + 1, "Player "+(i+1));
    }
  }
  
  private void initCardDecks() throws IOException {
    Path pathOfFile = Path.of("res").resolve("normal_game_cards.csv");
    var cardDeckByLevel = Game.setupCards(pathOfFile).groupByLevel();
    
    /*TODO: change the array to a map*/
    decks[0] = cardDeckByLevel.get(Level.LEVEL_3);
    decks[1] = cardDeckByLevel.get(Level.LEVEL_2);
    decks[2] = cardDeckByLevel.get(Level.LEVEL_1);
    initNobles(cardDeckByLevel.get(Level.LEVEL_NOBLE));
  }
  
  private void initNobles(CardDeck nobles) {
  	nobles.shuffleCardDeck();
  	for (int i = 0; i < noblesCards.colums(); i++) {
  		noblesCards.add(nobles.removeFirstCard(), 0, i);
		}
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
  
  /**
   * This method initializes the three Actions possible for a SimpleGame.
   */
  private void initActions() {
    actions[0] = new ThreeTokensAction();
    actions[1] = new TwoTokensAction();
    actions[2] = new BuyCardBoardAction();
    actions[3] = new BuyReservedCardAction();
    actions[4] = new ReserveCardBoardAction();
    actions[5] = new ReserveCardDeckAction();
  }
  
  public void initGame() throws IOException {
  	initCardDecks();
	  initBoard();
	  initPlayers();
	  initTokens();
	  initActions();
  }
  
 /* -- MECHANISMS -- */
  
  /**
   * This method calls the SimpleGame's Displayer to display it.
   */
  private void displayGame() {
		  displayer.display(players, decks, noblesCards, tokens, board, cardsColorsList());
	 }
  
  /**
   * This method returns the list of the cards colors authorized in the SimpleGame.
   * 
   * @return the list of the cards colors authorized in the SimpleGame.
   */
  private static List<Color> cardsColorsList() {
    return Color.getCardsColorsList();
  }
  
  
  
  
  /* -- ACTIONS -- */
  
  /**
   * This method checks if the given coordinates are correct in the context of a 2D array defined by
   * its number of rows and columns.
   * 
   * @param coordinates - the coordinates to check.
   * @param rows - the rows of the array we want to use the coordinates on.
   * @param cols - the columns of the array we want to use the coordinates on.
   * @return true if the coordinates are correct, false otherwise
   */
  private boolean checkCoordinates(int[] coordinates, int rows, int cols) {
    if (coordinates[0] < 0 || coordinates[0] >= rows) {
      displayer.displayActionError("You've entered a wrong row number");
      return false;
    } else if (coordinates[1] < 0 || coordinates[1] >= cols) {
      displayer.displayActionError("You've entered a wrong column number");
      return false;
    }
    
    return true;
  }
  
  
  /**
   * This method executes the action of buying a Card for a player, described by its ID.
   * 
   * @param playerID - The player's ID.
   * @return true if the action has been performed correctly, false otherwise.
   */
  private boolean buyCard(int playerID) {
    var coordinates = displayer.getCoordinates();
    
    if(!checkCoordinates(coordinates, board.rows(), board.colums())) {
      return false;
    }
    
    var card = board.remove(coordinates[0], coordinates[1]);
    
    if (players[playerID].canBuyCard(card)) {
      tokens.add(players[playerID].buyCard(card, cardsColorsList()));
      board.add(decks[coordinates[0]].removeFirstCard(), coordinates[0], coordinates[1]);
    } else {
      board.add(card, coordinates[0], coordinates[1]);
      displayer.displayActionError("You are not able to buy the Card.");
      return false;
    }
    
    return true;
  }
  
  /**
   * This method executes the action of buying a Card for a player, described by its ID.
   * 
   * @param playerID - The player's ID.
   * @return true if the action has been performed correctly, false otherwise.
   */
  private boolean buyReservedCard(int playerID) {
    var coordinates = displayer.getCoordinates();
    
    if(!checkCoordinates(coordinates, 1, players[playerID].numberOfReservedCards())) {
      return false;
    }
    
    var card = players[playerID].removeFromReserved(coordinates[0], coordinates[1]);
    
    if (players[playerID].canBuyCard(card)) {
      tokens.add(players[playerID].buyCard(card, cardsColorsList()));
    } else {
      players[playerID].addToReserved(card, coordinates[0], coordinates[1]);
      displayer.displayActionError("You are not able to buy the Card.");
      return false;
    }
    
    return true;
  }
  
  private void givePlayerGoldToken(int playerID) {
    if (tokens.getColorNumber(Color.GOLD) > 0) {
      players[playerID].takeToken(new BaseToken(Color.GOLD));
      tokens.remove(Map.of(Color.GOLD, 1));
    }
  }
  
  private boolean reserveCardBoard(int playerID) {
    var coordinates = displayer.getCoordinates();
    
    if(!checkCoordinates(coordinates, board.rows(), board.colums())) {
      return false;
    }
    
    var card = board.remove(coordinates[0], coordinates[1]);
    
    if (players[playerID].numberOfReservedCards() < 3) {
      players[playerID].pushToReserved(card);
      givePlayerGoldToken(playerID);
      board.add(decks[coordinates[0]].removeFirstCard(), coordinates[0], coordinates[1]);
    } else {
      board.add(card, coordinates[0], coordinates[1]);
      displayer.displayActionError("You cannot reserve any more Card.");
      return false;
    }
    
    return true;
  }
  
  
  private boolean checkDeckLevel(Level level) {
    if (level != Level.LEVEL_1 && level != Level.LEVEL_2 && level != Level.LEVEL_3) {
      displayer.displayActionError("The level you have chosen doesn't exist.");
      return false;
    }
    
    return true;
  }
  
  private int convertLevelToIndex(Level level) {
    if (level == Level.LEVEL_1) {
      return 2;
    } else if (level == Level.LEVEL_2) {
      return 1;
    } 
    
    return 0;
  }
  
  private boolean reserveCardDeck(int playerID) {
    var level = displayer.getDeckLevel();
    
    if(!checkDeckLevel(level)) {
      return false;
    }
    
    var card = decks[convertLevelToIndex(level)].removeFirstCard();
    
    if (players[playerID].numberOfReservedCards() < 3) {
      players[playerID].pushToReserved(card);
      givePlayerGoldToken(playerID);
    } else {
      decks[convertLevelToIndex(level)].add(card);
      displayer.displayActionError("You cannot reserve any more Card.");
      return false;
    }
    
    return true;
  }
  
  /**
   * This method checks if a player has 10 tokens or less.
   * 
   * @param playerID - the player's ID
   * @return true if the player has less than 10 tokens, false otherwise.
   */
  private boolean checkPlayersTokensNumber(int playerID) {
    if (players[playerID].getNumberOfTokens() == 10) {
      /*Exchange the tokens maybe?*/
      displayer.displayActionError("You already have 10 tokens. You cannot get more than that.");
      return false;
    }
    
    return true;
  }
  
  /**
   * This method checks if there is at least 1 token of the given Color in the TokenDeck.
   * 
   * @param color - the color of the token we want to check on.
   * @return true if the color has at least 1 token, false otherwise.
   */
  private boolean checkTokensNumber(Color color) {
    if (tokens.getColorNumber(color) <= 0) {
      displayer.displayActionError("Not enough tokens to perform the action.");
      return false;
    }
    
    return true;
  }
  
  /**
   * This method checks if the color isn't an UNKNOWN Color.
   * 
   * @param color - the Color to check.
   * @return true if the color is not UNKNOWN, false otherwise.
   */
  private boolean checkTokenColor(Color color) {
    if (color == Color.UNKNOWN || color == Color.GOLD) {
      displayer.displayActionError("Unknown Color.");
      return false;
    }
    
    return true;
  }
  
  /**
   * This method checks, for a list of tokens' colors, if the color is well defined
   * and if there is at least 1 token of this color in the TokenDeck.
   * 
   * @param colors - the list of colors to check
   * @return true if the list is correct, false otherwise.
   */
  private boolean checkTokensColorsList(List<Color> colors) {
    for (var color : colors) {
      if (!checkTokenColor(color) || !checkTokensNumber(color)) {
        return false;
      }
    }
    
    return true;
  }
  
  /**
   * This method checks if a list of colors effectively contains 3 distinct colors.
   * 
   * @param colors - the list of colors to check.
   * @return true if there is three distinct colors in the list, false otherwise.
   */
  private boolean checkThreeDistinctColors(List<Color> colors) {
    if(colors.stream().distinct().count() != 3) {
      displayer.displayActionError("You have not entered three distinct colors");
      return false;
    }
    
    return true;
  }
  
  /**
   * This method executes the action of taking two tokens of the same color for a player, described by its ID.
   * 
   * @param playerID - The player's ID.
   * @return true if the action has been performed correctly, false otherwise.
   */
  private boolean takeThreeTokens(int playerID) {
    var colors = displayer.getThreeColor();
    if (!checkPlayersTokensNumber(playerID) || !checkThreeDistinctColors(colors) || !checkTokensColorsList(colors)) {
      return false;
    }
    
    for (var color : colors) {
     	if(players[playerID].getNumberOfTokens() < 10) {
      		players[playerID].takeToken(new BaseToken(color));
      		tokens.remove(Map.of(color, 1));
     	}			
		  }
    
    return true;
  }
  
  /**
   * This method executes the action of taking three tokens fo different colors each for a player, described by its ID.
   * 
   * @param playerID - The player's ID.
   * @return true if the action has been performed correctly, false otherwise.
   */
  private boolean takeTwoTokens(int playerID) {
    var color = displayer.getUniqueColor();
    if (!checkPlayersTokensNumber(playerID) || !checkTokenColor(color) || !checkTokensNumber(color)) {
      return false;
    }
    
    var toTake = 2;
    if (tokens.getColorNumber(color) < 4) { /*Not enough tokens to take two of them*/
      toTake = 1;
    }
    
    var given = 0; /*To check how much we gave*/
    while(given < toTake && players[playerID].getNumberOfTokens() < 10) {
      players[playerID].takeToken(new BaseToken(color));
      given++;
    }
    
    tokens.remove(Map.of(color, given));
    
    if (given == 0) {
      return false;
    }
    
    return true;
  }
  
  private void visitOfNobles(int playerID) {
  	for (int i = 0; i < noblesCards.colums(); i++) {
    	var noble = noblesCards.remove(0, i);
    	if (players[playerID].canGetNoble(noble)) {
    		players[playerID].buyCard(noble, Color.getCardsColorsList());
    		break;
    	} else {
    		noblesCards.add(noble, 0, i);
    	}
		}
	}
  
  /**
   * This method allows the player, described by its ID, to choose an Action.
   * 
   * @param playerID - The player's ID.
   */
  private void chooseAction(int playerID) {
    var actionSucceed = false;
    
    while(!actionSucceed) {
      actionSucceed = switch(displayer.getPlayerAction(actions, players[playerID].name())) {
        case THREE_TOKENS -> takeThreeTokens(playerID);
        case TWO_TOKENS -> takeTwoTokens(playerID);
        case BUY_CARD_BOARD -> buyCard(playerID);
        case BUY_RESERVED_CARD -> buyReservedCard(playerID);
        case RESERVE_CARD_BOARD -> reserveCardBoard(playerID);
        case RESERVE_CARD_DECK -> reserveCardDeck(playerID);
        default -> {displayer.displayActionError("Unkown Action."); yield false;}
      };
    }
  }
  
  
  
  
  /* -- RUNNING THE GAME -- */
  
  /**
   * This method calculates and returns the maximum of the players' prestige points.
   * 
   * @return The maximum of the players' prestige points.
   */
  private int getMaxPrestige() {
    return Arrays.stream(players)
                 .map(Player::prestigePoints)
                 .max(Integer::compare)
                 .orElse(0);
  }
  
  /**
   * This method returns the ID of the player who has won the game.
   * 
   * @return the winner's ID.
   */
  private int getWinner() {    
    return Arrays.stream(players)
                 .filter(player -> player.id() == getMaxPrestige())
                 .sorted(Comparator.comparingInt(player -> player.numberOfDevelopmentCards(cardsColorsList()))).limit(1)
                 .map(Player::id)
                 .reduce(0, Integer::sum);
  }
  
  /**
   * This method runs the SimpleGame until one player reaches 15 prestige points.
   */
  public void run() {
    var playerID = 0;    
    
    while (getMaxPrestige() < 15) {
    	displayGame();
      chooseAction(playerID);
      visitOfNobles(playerID);
      playerID = (playerID + 1) % players.length;
    }
    
    /*LAST RUN*/
    for(var i = 0; i < players.length; i++) {
      displayGame();
      chooseAction(playerID);
      visitOfNobles(playerID);
      playerID = (playerID + 1) % players.length;
    }
    
    System.out.println(getWinner());
    displayer.displayWinner(players, getWinner());
  }
}
