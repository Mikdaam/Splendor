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
import fr.uge.splendor.player.HumanPlayer;
import fr.uge.splendor.player.Player;
import fr.uge.splendor.token.BaseToken;
import fr.uge.splendor.action.Action;
import fr.uge.splendor.action.BuyCardBoardAction;
import fr.uge.splendor.action.ThreeTokensAction;
import fr.uge.splendor.action.TwoTokensAction;


/**
 * This class represents a simple game mode. It implements the mechanisms required to run a simplified version of the 'Splendor' game.
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
 *
 */
public class SimpleGame implements Game {
	/*TODO: Add an HashMap to the player deck*/
  private final Board board;	 /* Model */
  private final CardDeck[] decks;
  private final TokenDeck tokens;
  private final Player[] players;
  private final Action[] actions;
  private final Displayer displayer; /* View */
  
  
  /**
   * This constructor creates a SimpleGame. It must be initialized with {@code initGame}.
   */
  public SimpleGame() {
    board = new Board(1, 4);
    decks = new CardDeck[1];
    tokens = new TokenDeck();
    players = new Player[2];
    displayer = new ConsoleDisplayer();
    actions = new Action[3];
  }
  
  
  
  
  /* -- INITIALIZATIONS -- */
  
  /**
   * This method initializes the unique CardDeck for a SimpleGame.
   * 
   * @throws IOException - The file containing the cards for the SimpleGame has not been found.
   */
  private void initCardDecks() throws IOException {
    Path pathOfFile = Path.of("res").resolve("base_game_cards.csv");
    decks[0] = Game.setupCards(pathOfFile);
  }
  
  /**
   * This method initializes a Board for a SimpleGame.
   */
  private void initBoard() {
    for (int i = 0; i < board.rows(); i++) {
      for (int j = 0; j < board.colums(); j++) {
        decks[0].shuffleCardDeck();
        board.add(decks[0].removeFirstCard(), i, j);
      }
    }
  }
  
  /**
   * This method initializes the two HumanPlayers for a SimpleGame.
   */
  private void initPlayers() {
    for (int i = 0; i < players.length; i++) {
      players[i] = new HumanPlayer(i + 1, "Player "+(i+1));
      players[i].removeTokensColor(Color.GOLD);
    }
  }
    
  /**
   * This method initializes the TokenDeck for a SimpleGame.
   */
  private void initTokenDeck() {
    tokens.add(Map.of(Color.DIAMOND, 4, Color.EMERALD, 4, Color.ONYX, 4, Color.RUBY, 4, Color.SAPPHIRE, 4));
    tokens.removeColor(Color.GOLD);
  }
  
  /**
   * This method initializes the three Actions possible for a SimpleGame.
   */
  private void initActions() {
    actions[0] = new ThreeTokensAction();
    actions[1] = new TwoTokensAction();
    actions[2] = new BuyCardBoardAction();    
  }
  
  /**
   * This method initializes the game completely for it to be playable.
   * 
   * @throws IOException - The file containing the cards for the SimpleGame has not been found.
   */
  public void initGame() throws IOException {
  	initCardDecks();
	  initBoard();
	  initPlayers();
	  initTokenDeck();
	  initActions();
  }
  
  
  
  
  /* -- MECHANISMS -- */
  
  /**
   * This method calls the SimpleGame's Displayer to display it.
   */
  private void displayGame() {
		  displayer.display(players, decks, tokens, board, cardsColorsList());
	 }
  
  /**
   * This method returns the list of the cards colors authorized in the SimpleGame.
   * 
   * @return the list of the cards colors authorized in the SimpleGame.
   */
  private static List<Color> cardsColorsList() {
    return Color.getCardsColorsList().stream() 
                                     .filter(color -> color != Color.NOBLE)
                                     .toList();
  }
  
  
  
  
  /* -- ACTIONS -- */
  
  /**
   * This method executes the action of buying a Card for a player, described by its ID.
   * 
   * @param playerID - The player's ID.
   * @return true if the action has been performed correctly, false otherwise.
   */
  private boolean buyCard(int playerID) {
    var coordinates = displayer.getCoordinates();
    
    if (coordinates[0] != 0) {
      displayer.displayActionError("You've entered a wrong row number");
      return false;
    } else if (coordinates[1] < 0 || coordinates[1] >= 4) {
      displayer.displayActionError("You've entered a wrong column number");
      return false;
    }
    
    var card = board.remove(coordinates[0], coordinates[1]);
    if (players[playerID].canBuyCard(card)) {
      tokens.add(players[playerID].buyCard(card, cardsColorsList()));
      board.add(decks[0].removeFirstCard(), coordinates[0], coordinates[1]);
    } else {
      board.add(card, coordinates[0], coordinates[1]);
      displayer.displayActionError("You are not able to buy the Card.");
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
    
    while (getMaxPrestige() < 1) {
    	 displayGame();
      chooseAction(playerID);
      playerID = (playerID + 1) % players.length;
    }
    
    /*LAST RUN*/
    for(var i = 0; i < players.length; i++) {
      displayGame();
      chooseAction(playerID);
      playerID = (playerID + 1) % players.length;
    }
    
    System.out.println(getWinner());
    displayer.displayWinner(players, getWinner());
  } 
}
