package fr.uge.splendor.game;


import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import fr.uge.splendor.board.Board;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.TokenPurse;
import fr.uge.splendor.displayer.Displayer;
import fr.uge.splendor.level.Level;
import fr.uge.splendor.player.HumanPlayer;
import fr.uge.splendor.player.Player;
import fr.uge.splendor.utils.Utils;
import fr.uge.splendor.action.GameAction;
import fr.uge.splendor.action.ActionType;
import fr.uge.splendor.action.BuyCardBoardAction;
import fr.uge.splendor.action.ThreeTokensAction;
import fr.uge.splendor.action.TwoTokensAction;


/**
 * This class represents a Game in Simple mode.
 * This mode is a simplified way to play Splendor, where nobles and reserved cards do not exist.
 * There is also a new set of cards, where each card is of level 1,
 * and each one costs 3 times their color and give 1 prestige point.
 * Only 2 human players can play this game mode.
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 */
public class SimpleGame implements Game {
	private final int nbOfPlayers = 2;
  private final EnumMap<ActionType, GameAction> actions;
  private GameData gameData;
  
  /**
   * Creates a SimpleGame.
   * @param isGraphicalMode - checks if the user asked for Graphical mode.
   */
  public SimpleGame(Displayer displayer) {
    actions = new EnumMap<>(ActionType.class);
    gameData = new GameData(new Board(1, 4), new EnumMap<>(Level.class), new Board(0, 0), new TokenPurse(), new ArrayList<Player>(), displayer, new EnumMap<>(Level.class), false);
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
  @Override
  public List<Color> cardsColorsList() {
    return Utils.cardsColorsList();
  }
  
  

  /* --- INITIALIZATIONS --- */
  
  /**
   * Initializes the CardDecks for the SimpleGame
   * @throws IOException - The file containing the cards for the SimpleGame has not been found.
   */
  @Override
  public void initCardDecks() throws IOException {
    Path pathOfFile = Path.of("res").resolve("base_game_cards.csv");
    var cardDeckByLevel = Game.setupCards(pathOfFile).groupByLevel();
    
    cardDeckByLevel.get(Level.LEVEL_1).shuffleCardDeck();
    gameData.decks().put(Level.LEVEL_1, cardDeckByLevel.get(Level.LEVEL_1));
  }
  
  /**
   * Initializes the Level Converter for the SimpleGame
   */
  @Override
  public void initLevelConverter() {
    gameData.levelToInteger().put(Level.LEVEL_1, 0);
  }
  
  /**
   * Initializes the Players for the SimpleGame
   */
  @Override
  public void initPlayers() {
    for (int i = 0; i < nbOfPlayers; i++) {
      gameData.players().add(new HumanPlayer(i+1, "Player "+(i+1)));
      gameData.players().get(i).removeTokensColor(Color.GOLD);
    }
  }
   
  /**
   * Initializes the Board for the SimpleGame
   */
  @Override
  public void initBoard() {
    for (int i = 0; i < gameData.board().rows(); i++) {
      for (int j = 0; j < gameData.board().colums(); j++) {
        gameData.board().push(gameData.decks().get(Level.LEVEL_1).removeFirstCard(), i);
      }
    }
  }
   
  /**
   * Initializes the TokenPurse (bank) for the SimpleGame
   */
  @Override
  public void initTokens() {     
    var tokens = gameData.tokens().addToken(Color.DIAMOND, 4);
    tokens = tokens.addToken(Color.EMERALD, 4);
    tokens = tokens.addToken(Color.ONYX, 4);
    tokens = tokens.addToken(Color.RUBY, 4);
    tokens = tokens.addToken(Color.SAPPHIRE, 4);
    
    gameData = new GameData(gameData.board(), gameData.decks(), gameData.noblesCards(), tokens, gameData.players(), gameData.displayer(), gameData.levelToInteger(), gameData.actionSucceed());
    
  }
  
  /**
   * Initializes the Actions for the SimpleGame
   */
  @Override
  public void initActions() {
    actions.put(ActionType.THREE_TOKENS, new ThreeTokensAction());
    actions.put(ActionType.TWO_TOKENS, new TwoTokensAction());
    actions.put(ActionType.BUY_CARD_BOARD, new BuyCardBoardAction());
  }

  
  
  /* -- RUNNING THE GAME -- */
 
  /**
   * This method runs the SimpleGame until one player reaches 15 prestige points.
   */
  public void run() {
    var playerID = 0;    
    var nbOfPlayers = gameData.players().size();
    
    while (getMaxPrestige() < 15) {
      displayGame();
      gameData = chooseAction(playerID, actions);
      playerID = (playerID + 1) % nbOfPlayers;
    }
    
    /*LAST RUN*/
    for(int i = 0; i < nbOfPlayers; i++) {
      displayGame();
      gameData = chooseAction(playerID, actions);
      playerID = (playerID + 1) % nbOfPlayers;
    }
    
    gameData.displayer().displayWinner(gameData.players(), getWinner(cardsColorsList()));
  } 
}
