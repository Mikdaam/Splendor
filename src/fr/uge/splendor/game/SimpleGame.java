package fr.uge.splendor.game;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;

import fr.uge.splendor.board.Board;
import fr.uge.splendor.card.Coordinate;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.TokenPurse;
import fr.uge.splendor.displayer.ConsoleDisplayer;
import fr.uge.splendor.displayer.Displayer;
import fr.uge.splendor.level.Level;
import fr.uge.splendor.player.HumanPlayer;
import fr.uge.splendor.player.Player;
import fr.uge.splendor.utils.Utils;
import fr.uge.splendor.action.Action;
import fr.uge.splendor.action.ActionType;
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
	private final int nbOfPlayers = 2;
  private final EnumMap<ActionType, Action> actions;
  
  private final Displayer displayer; /* View */
  
  private GameData gameData;
  
  /**
   * This constructor creates a SimpleGame. It must be initialized with {@code initGame}.
   */
  public SimpleGame() {
    actions = new EnumMap<>(ActionType.class);
    displayer = new ConsoleDisplayer();
    gameData = new GameData(new Board(1, 4), new EnumMap<>(Level.class), new Board(0, 0), new TokenPurse(), new ArrayList<Player>(), new ConsoleDisplayer(), new EnumMap<>(Level.class), false);
  }
  
  
  
  
  /* -- INITIALIZATIONS -- */
  
  private void initLevelConverter() {
    gameData.levelToInteger().put(Level.LEVEL_1, 0);
  }
  
  /**
   * This method initializes the unique CardDeck for a SimpleGame.
   * 
   * @throws IOException - The file containing the cards for the SimpleGame has not been found.
   */
  private void initCardDecks() throws IOException {
    Path pathOfFile = Path.of("res").resolve("base_game_cards.csv");
    
    gameData.decks().put(Level.LEVEL_1, Game.setupCards(pathOfFile));
    gameData.decks().get(Level.LEVEL_1).shuffleCardDeck();
  }
  
  /**
   * This method initializes a Board for a SimpleGame.
   */
  private void initBoard() {
    for (int i = 0; i < gameData.board().rows(); i++) {
      for (int j = 0; j < gameData.board().colums(); j++) {
        //board.add(decks[0].removeFirstCard(), new Coordinate(i, j));
        gameData.board().add(gameData.decks().get(Level.LEVEL_1).removeFirstCard(), new Coordinate(i, j));
      }
    }
  }
  
  /**
   * This method initializes the two HumanPlayers for a SimpleGame.
   */
  private void initPlayers() {
    for (int i = 0; i < nbOfPlayers; i++) {
      gameData.players().add(new HumanPlayer(i+1, "Player "+(i+1)));
      //System.out.println("Each Player : "+ gameData.players());
      gameData.players().get(i).removeTokensColor(Color.GOLD);
    }
  }
    
  /**
   * This method initializes the TokenDeck for a SimpleGame.
   */
  private void initTokenDeck() {     
    var tokens = gameData.tokens().addToken(Color.DIAMOND, 4);
    tokens = tokens.addToken(Color.EMERALD, 4);
    tokens = tokens.addToken(Color.ONYX, 4);
    tokens = tokens.addToken(Color.RUBY, 4);
    tokens = tokens.addToken(Color.SAPPHIRE, 4);
    
    gameData = new GameData(gameData.board(), gameData.decks(), gameData.noblesCards(), tokens, gameData.players(), displayer, gameData.levelToInteger(), gameData.actionSucceed());
    
  }
  
  /**
   * This method initializes the three Actions possible for a SimpleGame.
   */
  private void initActions() {
    actions.put(ActionType.THREE_TOKENS, new ThreeTokensAction());
    actions.put(ActionType.TWO_TOKENS, new TwoTokensAction());
    actions.put(ActionType.BUY_CARD_BOARD, new BuyCardBoardAction());
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
	  initLevelConverter();
  }
  
  
  
  
  /* -- MECHANISMS -- */
  
  /**
   * This method calls the SimpleGame's Displayer to display it.
   */
  private void displayGame() {
		  displayer.display(gameData, Utils.cardsColorsList());
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
  /*private boolean checkCoordinates(Board board, Coordinate coordinate) {    
    if (!board.rowIsInBoard(coordinate)) {
    	displayer.displayActionError("You've entered a wrong row number");
      return false;
    } else if (!board.colIsInBoard(coordinate)) {
    	displayer.displayActionError("You've entered a wrong column number");
      return false;
    }
    
    return true;
  }*/
  
  /**
   * This method executes the action of buying a Card for a player, described by its ID.
   * 
   * @param playerID - The player's ID.
   * @return true if the action has been performed correctly, false otherwise.
   */
  /*private boolean buyCard(int playerID) {
    var coordinate = displayer.getCoordinates();
    
    if (!checkCoordinates(board, coordinate)) {
      return false;
    }
    if (!board.canRemove(coordinate)) {
      displayer.displayActionError("There is no card to buy here...");
      return false;
    }
    
    var card = board.remove(coordinate);
    if (players[playerID].canBuyCard(card)) {
      tokens.add(players[playerID].buyCard(card, Utils.cardsColorsList()));
      board.add(decks[0].removeFirstCard(), coordinate);
    } else {
      board.add(card, coordinate);
      displayer.displayActionError("You are not able to buy the Card.");
      return false;
    }
    
    return true;
  }*/
  
  /**
   * This method checks if a player has 10 tokens or less.
   * 
   * @param playerID - the player's ID
   * @return true if the player has less than 10 tokens, false otherwise.
   */
  /*private boolean checkPlayersTokensNumber(int playerID) {
    if (players[playerID].getNumberOfTokens() == 10) {
      /*Exchange the tokens maybe?*
      displayer.displayActionError("You already have 10 tokens. You cannot get more than that.");
      return false;
    }
    
    return true;
  }*/
  
  /**
   * This method checks if there is at least 1 token of the given Color in the TokenDeck.
   * 
   * @param color - the color of the token we want to check on.
   * @return true if the color has at least 1 token, false otherwise.
   */
  /*private boolean checkTokensNumber(Color color) {
    if (tokens.getColorNumber(color) <= 0) {
      displayer.displayActionError("Not enough tokens to perform the action.");
      return false;
    }
    
    return true;
  }*/
  
  /**
   * This method checks if the color isn't an UNKNOWN Color.
   * 
   * @param color - the Color to check.
   * @return true if the color is not UNKNOWN, false otherwise.
   */
  /*private boolean checkTokenColor(Color color) {
    if (color == Color.UNKNOWN || color == Color.GOLD) {
      displayer.displayActionError("Unknown Color.");
      return false;
    }
    
    return true;
  }*/
  
  /**
   * This method checks, for a list of tokens' colors, if the color is well defined
   * and if there is at least 1 token of this color in the TokenDeck.
   * 
   * @param colors - the list of colors to check
   * @return true if the list is correct, false otherwise.
   */
  /*private boolean checkTokensColorsList(List<Color> colors) {
    for (var color : colors) {
      if (!checkTokenColor(color) || !checkTokensNumber(color)) {
        return false;
      }
    }
    
    return true;
  }*/
  
  /**
   * This method checks if a list of colors effectively contains 3 distinct colors.
   * 
   * @param colors - the list of colors to check.
   * @return true if there is three distinct colors in the list, false otherwise.
   */
  /*private boolean checkThreeDistinctColors(List<Color> colors) {
    if(colors.stream().distinct().count() != 3) {
      displayer.displayActionError("You have not entered three distinct colors");
      return false;
    }
    
    return true;
  }*/
  
  /**
   * This method executes the action of taking two tokens of the same color for a player, described by its ID.
   * 
   * @param playerID - The player's ID.
   * @return true if the action has been performed correctly, false otherwise.
   */
  /*private boolean takeThreeTokens(int playerID) {
    var colors = displayer.getThreeColor();
    if (!checkPlayersTokensNumber(playerID) || !checkThreeDistinctColors(colors) || !checkTokensColorsList(colors)) {
      return false;
    }
    
    for (var color : colors) {
     	if(players[playerID].getNumberOfTokens() < 10) {
      		players[playerID].takeToken(color);
          tokens = tokens.remove((new TokenPurse()).addToken(color, 1));
     	}			
		}
    
    return true;
  }*/
  
  /**
   * This method executes the action of taking three tokens fo different colors each for a player, described by its ID.
   * 
   * @param playerID - The player's ID.
   * @return true if the action has been performed correctly, false otherwise.
   */
  /*private boolean takeTwoTokens(int playerID) {
    var color = displayer.getUniqueColor();
    if (!checkPlayersTokensNumber(playerID) || !checkTokenColor(color) || !checkTokensNumber(color)) {
      return false;
    }
    
    var toTake = 2;
    if (tokens.getColorNumber(color) < 4) { /*Not enough tokens to take two of them*
      toTake = 1;
    }
    
    var given = 0; /*To check how much we gave*
    while(given < toTake && players[playerID].getNumberOfTokens() < 10) {
      players[playerID].takeToken(color);
      given++;
    }
    
    tokens = tokens.remove((new TokenPurse()).addToken(color, given));
    
    if (given == 0) {
      return false;
    }
    
    return true;
  }*/
  
  /**
   * This method allows the player, described by its ID, to choose an Action.
   * 
   * @param playerID - The player's ID.
   */
  private void chooseAction(int playerID) {
    
    /*while(!actionSucceed) {
      actionSucceed = switch(displayer.getPlayerAction(actions2, players[playerID].name())) {
        case THREE_TOKENS -> takeThreeTokens(playerID);
        case TWO_TOKENS -> takeTwoTokens(playerID);
        case BUY_CARD_BOARD -> buyCard(playerID);
        default -> {displayer.displayActionError("Unkown Action."); yield false;}
      };
    }*/
    
    while (!gameData.actionSucceed()) {
    	var choosenActionType = displayer.getPlayerAction(actions, gameData.players().get(playerID).name());
	    
      if(choosenActionType == ActionType.UNKNOWN) {
      	displayer.displayActionError("Uknown Action");
      } else {
      	gameData = actions.get(choosenActionType).apply(playerID, gameData);
      }
		}
    gameData = new GameData(gameData.board(), gameData.decks(), gameData.noblesCards(), gameData.tokens(), gameData.players(), displayer, gameData.levelToInteger(), false);
    
  }
  
  
  public GameData getGameState() {
    return gameData;
  }
  
  /* -- RUNNING THE GAME -- */
  
  /**
   * This method calculates and returns the maximum of the players' prestige points.
   * 
   * @return The maximum of the players' prestige points.
   */
  private int getMaxPrestige() {
    return gameData.players().stream()
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
    return gameData.players().stream()
                 .filter(player -> player.id() == getMaxPrestige())
                 .sorted(Comparator.comparingInt(player -> player.numberOfDevelopmentCards(Utils.cardsColorsList()))).limit(1)
                 .map(Player::id)
                 .reduce(0, Integer::sum);
  }
  
  /**
   * This method runs the SimpleGame until one player reaches 15 prestige points.
   */
  public void run() {
    var playerID = 0;    
    
    //System.out.println("Players : " + gameData.players());
    //System.out.println("Tokens : " + gameData.tokens() + "\n");
    
    while (getMaxPrestige() < 15) {
    	displayGame();
      chooseAction(playerID);
      playerID = (playerID + 1) % nbOfPlayers;
    }
    
    /*LAST RUN*/
    for(int i = 0; i < nbOfPlayers; i++) {
      displayGame();
      chooseAction(playerID);
      playerID = (playerID + 1) % nbOfPlayers;
    }
    
    System.out.println(getWinner());
    displayer.displayWinner(gameData.players(), getWinner());
  } 
}
