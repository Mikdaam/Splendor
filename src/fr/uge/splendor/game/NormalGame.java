/**
 * 
 */
package fr.uge.splendor.game;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;

import fr.uge.splendor.board.Board;
import fr.uge.splendor.card.Coordinate;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.deck.TokenPurse;
import fr.uge.splendor.displayer.ConsoleDisplayer;
import fr.uge.splendor.displayer.Displayer;
import fr.uge.splendor.level.Level;
import fr.uge.splendor.player.HumanPlayer;
import fr.uge.splendor.player.Player;
import fr.uge.splendor.action.Action;
import fr.uge.splendor.action.ActionType;
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
	
	private static final Level[] levels = {Level.LEVEL_1, Level.LEVEL_2, Level.LEVEL_3};
	private final int nbOfPlayers;
	
	/* Model */
	/*private final Board board;
	private final CardDeck[] decks;
	private TokenPurse tokens;
	private final Player[] players;
	private final Action[] actions;*/
	private final EnumMap<ActionType, Action> actions;
	
	private GameData gameData;
	
	private final Displayer displayer; /* View */
	
  public NormalGame(int numberOfPlayers) {
		if(numberOfPlayers < 2 && numberOfPlayers > 4) {
			throw new IllegalArgumentException("Number of player should be greater than 2.");
		}
		
		/*board = new Board(3, 4);
    decks = new CardDeck[3];
    tokens = new TokenPurse();
    players = new Player[numberOfPlayers];
    actions = new Action[6];*/
		nbOfPlayers = numberOfPlayers;
		displayer = new ConsoleDisplayer();
    actions = new EnumMap<>(ActionType.class);
    
    gameData = new GameData(new Board(3, 4), new EnumMap<>(Level.class), new Board(1, numberOfPlayers + 1), new TokenPurse(), new ArrayList<Player>(), new ConsoleDisplayer(), false);
    
    if (numberOfPlayers == 2) {
    	NUMBER_OF_TOKEN_MISSING_BY_NUM_OF_PLAYER = 3;
    } else if (numberOfPlayers == 3) {
    	NUMBER_OF_TOKEN_MISSING_BY_NUM_OF_PLAYER = 2;
    } else {
    	NUMBER_OF_TOKEN_MISSING_BY_NUM_OF_PLAYER = 0;
    }
	}
	
	private void initBoard() {
    for (int i = 1; i <= gameData.board().rows(); i++) {
      for (int j = 1; j <= gameData.board().colums(); j++) {      	
      	gameData.board().add(gameData.decks().get(Level.getLevel(i)).removeFirstCard(), new Coordinate(i, j));
      }
    }
  }
  
  private void initPlayers() {
    for (int i = 0; i < nbOfPlayers; i++) {
    	gameData.players().add(new HumanPlayer(i + 1, "Player "+(i+1)));
    }
  }
  
  private void initCardDecks() throws IOException {
    Path pathOfFile = Path.of("res").resolve("normal_game_cards.csv");
    var cardDeckByLevel = Game.setupCards(pathOfFile).groupByLevel();
    
    /* TODO: Think different !!! */
    for (var level : levels) {
			gameData.decks().put(level, cardDeckByLevel.get(level));
		}
    
    initNobles(cardDeckByLevel.get(Level.LEVEL_NOBLE));
  }
  
  private void initNobles(CardDeck nobles) {
  	nobles.shuffleCardDeck();
  	var len = gameData.noblesCards().colums();
  	for (int i = 0; i < len; i++) {
  		gameData.noblesCards().add(nobles.removeFirstCard(), new Coordinate(0, i));
		}
  }
  
  private void initTokens() {
  	var numberOfTokenByColor = NUMBER_OF_TOKEN_PER_COLOR - NUMBER_OF_TOKEN_MISSING_BY_NUM_OF_PLAYER;
  	
    /*tokens = tokens.addToken(Color.DIAMOND, numberOfTokenByColor);
    tokens = tokens.addToken(Color.EMERALD, numberOfTokenByColor);
    tokens = tokens.addToken(Color.ONYX, numberOfTokenByColor);
    tokens = tokens.addToken(Color.RUBY, numberOfTokenByColor);
    tokens = tokens.addToken(Color.SAPPHIRE, numberOfTokenByColor);
    tokens = tokens.addToken(Color.GOLD, NUMBER_OF_GOLD_TOKEN);*/
    
    var tokens = gameData.tokens().addToken(Color.DIAMOND, numberOfTokenByColor);
    tokens = tokens.addToken(Color.EMERALD, numberOfTokenByColor);
    tokens = tokens.addToken(Color.ONYX, numberOfTokenByColor);
    tokens = tokens.addToken(Color.RUBY, numberOfTokenByColor);
    tokens = tokens.addToken(Color.SAPPHIRE, numberOfTokenByColor);
    tokens = tokens.addToken(Color.GOLD, NUMBER_OF_GOLD_TOKEN);
    
    gameData = new GameData(gameData.board(), gameData.decks(), gameData.noblesCards(), tokens, gameData.players(), displayer, gameData.actionSucceed());
  }
  
  /**
   * This method initializes the three Actions possible for a SimpleGame.
   */
  private void initActions() {    
    actions.put(ActionType.THREE_TOKENS, new ThreeTokensAction());
    actions.put(ActionType.TWO_TOKENS, new TwoTokensAction());
    actions.put(ActionType.BUY_CARD_BOARD, new BuyCardBoardAction());
    actions.put(ActionType.BUY_RESERVED_CARD, new BuyReservedCardAction());
    actions.put(ActionType.RESERVE_CARD_BOARD, new ReserveCardBoardAction());
    actions.put(ActionType.RESERVE_CARD_DECK, new ReserveCardDeckAction());
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
		  displayer.display(gameData, cardsColorsList());
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
    var coordinates = displayer.getCoordinates();
    
    if(!checkCoordinates(board, coordinates)) {
      return false;
    }
    
    var level = coordinates.row();
    var card = board.remove(coordinates);
    
    if (players[playerID].canBuyCard(card)) {
      tokens = tokens.add(players[playerID].buyCard(card, cardsColorsList()));
      board.add(decks[level].removeFirstCard(), coordinates);
    } else {
      board.add(card, coordinates);
      displayer.displayActionError("You are not able to buy the Card.");
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
  /*private boolean buyReservedCard(int playerID) {
    var coordinate = displayer.getCoordinates();
    
    /*!IMPORTANT: maybe not efficient ...*
    var newReservedCards = new Board(1, players[playerID].numberOfReservedCards());
    
    if(!checkCoordinates(newReservedCards, coordinate)) {
      return false;
    }
    
    var card = players[playerID].removeFromReserved(coordinate);
    
    if (players[playerID].canBuyCard(card)) {
      tokens = tokens.add(players[playerID].buyCard(card, cardsColorsList()));
    } else {
      players[playerID].pushToReserved(card);
      displayer.displayActionError("You are not able to buy the Card.");
      return false;
    }
    
    return true;
  }*/
  
  /*private void givePlayerGoldToken(int playerID) {
    if (tokens.getColorNumber(Color.GOLD) > 0) {
      players[playerID].takeToken(Color.GOLD);
      tokens = tokens.remove((new TokenPurse()).addToken(Color.GOLD, 1));
    }
  }*/
  
  /*private boolean reserveCardBoard(int playerID) {
    var coordinate = displayer.getCoordinates();
    
    if(!checkCoordinates(board, coordinate)) {
      return false;
    }
    
    var level = coordinate.row();
    var card = board.remove(coordinate);
    
    if (players[playerID].numberOfReservedCards() < 3) {
      players[playerID].pushToReserved(card);
      givePlayerGoldToken(playerID);
      board.add(decks[level].removeFirstCard(), coordinate);
    } else {
      board.add(card, coordinate);
      displayer.displayActionError("You cannot reserve any more Card.");
      return false;
    }
    
    return true;
  }*/
  
  
  /*private boolean checkDeckLevel(Level level) {
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
  
  private void visitOfNobles(int playerID) {
  	for (int i = 0; i < gameData.noblesCards().colums(); i++) {
  		var coordinate = new Coordinate(0, i);
    	var noble = gameData.noblesCards().remove(coordinate);
    	if (gameData.players().get(playerID).canGetNoble(noble)) {
    		gameData.players().get(playerID).buyCard(noble, Color.getCardsColorsList());
    		break;
    	} else {
    		gameData.noblesCards().add(noble, coordinate);
    	}
		}
	}
  
  /**
   * This method allows the player, described by its ID, to choose an Action.
   * 
   * @param playerID - The player's ID.
   */
  private void chooseAction(int playerID) {    
    /*while(!actionSucceed) {
      actionSucceed = switch(displayer.getPlayerAction(actions, players[playerID].name())) {
        case THREE_TOKENS -> takeThreeTokens(playerID);
        case TWO_TOKENS -> takeTwoTokens(playerID);
        case BUY_CARD_BOARD -> buyCard(playerID);
        case BUY_RESERVED_CARD -> buyReservedCard(playerID);
        case RESERVE_CARD_BOARD -> reserveCardBoard(playerID);
        case RESERVE_CARD_DECK -> reserveCardDeck(playerID);
        default -> {displayer.displayActionError("Unkown Action."); yield false;}
      };*/
      
      
    while (!gameData.actionSucceed()) {
    	var choosenActionType = displayer.getPlayerAction(actions, gameData.players().get(playerID).name());
	    
      if(choosenActionType == ActionType.UNKNOWN) {
      	displayer.displayActionError("Uknown Action");
      } else {
      	gameData = actions.get(choosenActionType).apply(playerID, gameData);
      }
		}
    gameData = new GameData(gameData.board(), gameData.decks(), gameData.noblesCards(), gameData.tokens(), gameData.players(), displayer, false);
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
                 .sorted(Comparator.comparingInt(player -> player.numberOfDevelopmentCards(cardsColorsList()))).limit(1)
                 .map(Player::id)
                 .reduce(0, Integer::sum);
  }
  
  /**
   * This method runs the SimpleGame until one player reaches 15 prestige points.
   */
  public void run() {
    var playerID = 0;    
    var nbOfPlayers = gameData.players().size();
    
    System.out.println("Players : " + gameData.players());
    System.out.println("Tokens : " + gameData.tokens());
    
    while (getMaxPrestige() < 15) {
    	displayGame();
      chooseAction(playerID);
      visitOfNobles(playerID);
      playerID = (playerID + 1) % nbOfPlayers;
    }
    
    /*LAST RUN*/
    for(int i = 0; i < nbOfPlayers; i++) {
      displayGame();
      chooseAction(playerID);
      visitOfNobles(playerID);
      playerID = (playerID + 1) % nbOfPlayers;
    }
    
    System.out.println(getWinner());
    displayer.displayWinner(gameData.players(), getWinner());
  }
}
