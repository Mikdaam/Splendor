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
import fr.uge.splendor.action.GiveBackTokensAction;
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
