package fr.uge.splendor.game;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import fr.uge.splendor.board.Board;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.deck.TokenPurse;
import fr.uge.splendor.displayer.Displayer;
import fr.uge.splendor.level.Level;
import fr.uge.splendor.player.HumanPlayer;
import fr.uge.splendor.player.Player;
import fr.uge.splendor.action.GameAction;
import fr.uge.splendor.action.ActionType;
import fr.uge.splendor.action.BuyCardBoardAction;
import fr.uge.splendor.action.BuyReservedCardAction;
import fr.uge.splendor.action.ReserveCardBoardAction;
import fr.uge.splendor.action.ReserveCardDeckAction;
import fr.uge.splendor.action.ThreeTokensAction;
import fr.uge.splendor.action.TwoTokensAction;

/**
 * This class represents a Game in Normal mode.
 * This is the complete reproduction of the Splendor Game.
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
 	private final EnumMap<ActionType, GameAction> actions;
 	private GameData gameData;
	
 	
	 /**
	  * Creates a NormalGame with a given number of players.
	  * @param numberOfPlayers - the number of players for the NormalGame
	  */
  public NormalGame(Displayer displayer, int numberOfPlayers) {
  		if(numberOfPlayers < 2 || numberOfPlayers > 4) {
  		  throw new IllegalArgumentException("Number of player should be 2, 3 or 4.");
  		}
  		
  		nbOfPlayers = numberOfPlayers;
    actions = new EnumMap<>(ActionType.class);
    gameData = new GameData(new Board(3, 4), new EnumMap<>(Level.class), new Board(1, numberOfPlayers + 1), new TokenPurse(), new ArrayList<Player>(), displayer, new EnumMap<>(Level.class), false);
    
    if (numberOfPlayers == 2) {
    	 NUMBER_OF_TOKEN_MISSING_BY_NUM_OF_PLAYER = 3;
    } else if (numberOfPlayers == 3) {
    	 NUMBER_OF_TOKEN_MISSING_BY_NUM_OF_PLAYER = 2;
    } else {
    	 NUMBER_OF_TOKEN_MISSING_BY_NUM_OF_PLAYER = 0;
    }
  }
  
  
  /**
   * Returns the game's data.
   * @return The game's data.
   */
  @Override
  public GameData getGameState() {
    return gameData;
  }
  
  /**
   * Returns the list of colors possible for the development cards.
   * @return The list of colors possible for the development cards.
   */
  public List<Color> cardsColorsList() {
    return Color.getCardsColorsList();
  }
  
  
  
  /* --- INITIALIZATIONS --- */

  /**
   * Initializes the Nobles for the NormalGame
   */
  private void initNobles(CardDeck nobles) {
    nobles.shuffleCardDeck();
    
    var len = gameData.noblesCards().colums();
    
    for (var i = 0; i < len; i++) {
      gameData.noblesCards().push(nobles.removeFirstCard(), 0);
    }
  }
   
  /**
   * Initializes the CardDecks for the NormalGame
   * @throws IOException - The file containing the cards for the NormalGame has not been found.
   */
  @Override
  public void initCardDecks() throws IOException {
    Path pathOfFile = Path.of("res").resolve("normal_game_cards.csv");
    var cardDeckByLevel = Game.setupCards(pathOfFile).groupByLevel();
    
    for (var level : levels) {
      cardDeckByLevel.get(level).shuffleCardDeck();
      gameData.decks().put(level, cardDeckByLevel.get(level));
    }
    
    initNobles(cardDeckByLevel.get(Level.LEVEL_NOBLE));
  }
  
  /**
   * Initializes the Level Converter for the NormalGame
   */
  @Override
  public void initLevelConverter() {
    gameData.levelToInteger().put(Level.LEVEL_1, 2);
    gameData.levelToInteger().put(Level.LEVEL_2, 1);
    gameData.levelToInteger().put(Level.LEVEL_3, 0);
  }
	
  /**
   * Initializes the Players for the NormalGame
   */
  @Override
  public void initPlayers() {
    for (int i = 0; i < nbOfPlayers; i++) {
     gameData.players().add(new HumanPlayer(i + 1, "Player "+(i+1)));
    }
  }

  /**
   * Initializes the Board for the NormalGame
   */
  @Override
	 public void initBoard() {
    for (int i = 1; i <= gameData.board().rows(); i++) {
      for (int j = 1; j <= gameData.board().colums(); j++) {
      	 gameData.board().push(gameData.decks().get(Level.getLevel(i)).removeFirstCard(), gameData.board().rows()-i);
      }
    }
  }

  /**
   * Initializes the TokenPurse (bank) for the NormalGame
   */@Override
  public void initTokens() {
  	 var numberOfTokenByColor = NUMBER_OF_TOKEN_PER_COLOR - NUMBER_OF_TOKEN_MISSING_BY_NUM_OF_PLAYER;    
    var tokens = gameData.tokens().addToken(Color.DIAMOND, numberOfTokenByColor);
    tokens = tokens.addToken(Color.EMERALD, numberOfTokenByColor);
    tokens = tokens.addToken(Color.ONYX, numberOfTokenByColor);
    tokens = tokens.addToken(Color.RUBY, numberOfTokenByColor);
    tokens = tokens.addToken(Color.SAPPHIRE, numberOfTokenByColor);
    tokens = tokens.addToken(Color.GOLD, NUMBER_OF_GOLD_TOKEN);
    
    gameData = new GameData(gameData.board(), gameData.decks(), gameData.noblesCards(), tokens, gameData.players(), gameData.displayer(), gameData.levelToInteger(), gameData.actionSucceed());
  }
  
  /**
   * Initializes the Actions for the NormalGame
   */
   @Override
  public void initActions() {    
    actions.put(ActionType.THREE_TOKENS, new ThreeTokensAction());
    actions.put(ActionType.TWO_TOKENS, new TwoTokensAction());
    actions.put(ActionType.BUY_CARD_BOARD, new BuyCardBoardAction());
    actions.put(ActionType.BUY_RESERVED_CARD, new BuyReservedCardAction());
    actions.put(ActionType.RESERVE_CARD_BOARD, new ReserveCardBoardAction());
    actions.put(ActionType.RESERVE_CARD_DECK, new ReserveCardDeckAction());
  }



  /* -- RUNNING THE GAME -- */
  
  /**
   * This method runs the NormalGame until one player reaches 15 prestige points.
   */
  public void run() {
    var playerID = 0;    
    var nbOfPlayers = gameData.players().size();
    
    while (getMaxPrestige() < 15) {
    	 displayGame();
    	 gameData = chooseAction(playerID, actions);
      visitOfNobles(playerID);
      playerID = (playerID + 1) % nbOfPlayers;
    }
    
    /*LAST RUN*/
    for(int i = 0; i < nbOfPlayers; i++) {
      displayGame();
      gameData = chooseAction(playerID, actions);
      visitOfNobles(playerID);
      playerID = (playerID + 1) % nbOfPlayers;
    }
    
    gameData.displayer().displayWinner(gameData.players(), getWinner(cardsColorsList()));
  }
}
