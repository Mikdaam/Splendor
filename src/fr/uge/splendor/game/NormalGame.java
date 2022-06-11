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
import fr.uge.splendor.card.Card;
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
import fr.uge.splendor.action.GiveBackTokensAction;
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
    
    gameData = new GameData(new Board(3, 4), new EnumMap<>(Level.class), new Board(1, numberOfPlayers + 1), new TokenPurse(), new ArrayList<Player>(), new ConsoleDisplayer(), new EnumMap<>(Level.class), false);
    
    if (numberOfPlayers == 2) {
    	 NUMBER_OF_TOKEN_MISSING_BY_NUM_OF_PLAYER = 3;
    } else if (numberOfPlayers == 3) {
    	 NUMBER_OF_TOKEN_MISSING_BY_NUM_OF_PLAYER = 2;
    } else {
    	 NUMBER_OF_TOKEN_MISSING_BY_NUM_OF_PLAYER = 0;
    }
  }
  
  private void initLevelConverter() {
    gameData.levelToInteger().put(Level.LEVEL_1, 2);
    gameData.levelToInteger().put(Level.LEVEL_2, 1);
    gameData.levelToInteger().put(Level.LEVEL_3, 0);
  }
	
	 private void initBoard() {
    for (int i = 1; i <= gameData.board().rows(); i++) {
      for (int j = 1; j <= gameData.board().colums(); j++) {
      	 gameData.board().push(gameData.decks().get(Level.getLevel(i)).removeFirstCard(), gameData.board().rows()-i);
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
    
    for (var level : levels) {
      cardDeckByLevel.get(level).shuffleCardDeck();
			   gameData.decks().put(level, cardDeckByLevel.get(level));
		  }
    
    initNobles(cardDeckByLevel.get(Level.LEVEL_NOBLE));
  }
  
  private void initNobles(CardDeck nobles) {
    nobles.shuffleCardDeck();
    
    var len = gameData.noblesCards().colums();
    
    for (var i = 0; i < len; i++) {
  		  gameData.noblesCards().push(nobles.removeFirstCard(), 0);
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
    
    gameData = new GameData(gameData.board(), gameData.decks(), gameData.noblesCards(), tokens, gameData.players(), displayer, gameData.levelToInteger(), gameData.actionSucceed());
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
  	initLevelConverter();
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

  
  private void visitOfNobles(int playerID) {
    var noblesList = new ArrayList<Card>();
    var numberOfNobles = gameData.noblesCards().numberOfCards();
    for (var i = 0; i < numberOfNobles; i++) {
      noblesList.add(gameData.noblesCards().remove(new Coordinate(0, 0)));
    }
    
    var hasNoble = false;
    for (var noble: noblesList) {
      if (!hasNoble && gameData.players().get(playerID).canGetNoble(noble)) {
        gameData.players().get(playerID).buyCard(noble, Color.getCardsColorsList());
        hasNoble = true; /*The player can only get one noble per turn, this will prevent them from getting another noble*/
      } else {
        gameData.noblesCards().push(noble, 0); /*We put all the other nobles back in the data*/
      }
    }
	 }
  
  /**
   * This method allows the player, described by its ID, to choose an Action.
   * 
   * @param playerID - The player's ID.
   */
  private void chooseAction(int playerID) {      
    while (!gameData.actionSucceed()) {
    	var choosenActionType = displayer.getPlayerAction(actions, gameData.players().get(playerID).name());
	    
      if(choosenActionType == ActionType.UNKNOWN) {
      	 displayer.displayActionError("Uknown Action");
      } else {
      	 gameData = actions.get(choosenActionType).apply(playerID, gameData);
      }
		  }
    
    gameData = (new GiveBackTokensAction()).apply(playerID, gameData);
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
