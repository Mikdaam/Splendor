package fr.uge.splendor.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;

import fr.uge.splendor.action.ActionType;
import fr.uge.splendor.action.GameAction;
import fr.uge.splendor.action.GiveBackTokensAction;
import fr.uge.splendor.card.Card;
import fr.uge.splendor.card.Coordinate;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.player.Player;
import fr.uge.splendor.utils.FileLoader;

/**
 * This interface represents a Game. It must be able to initialize itself and
 * then run itself.
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 *
 */
public interface Game {
  
  /**
   * Returns the game's data.
   * @return The game's data.
   */
  GameData getGameState();
  
  /**
   * Returns the list of colors possible for the development cards.
   * @return The list of colors possible for the development cards.
   */
  List<Color> cardsColorsList();
  
  
  /**
   * Initializes the CardDecks for the Game
   * @throws IOException - The file containing the cards for the NormalGame has not been found.
   */
  void initCardDecks() throws IOException;
  
  /**
   * Initializes the Level Converter for the Game
   */
  void initLevelConverter();
  
  /**
   * Initializes the Players for the Game
   */
  void initPlayers();
  
  /**
   * Initializes the Board for the Game
   */
  void initBoard();
  
  /**
   * Initializes the TokenPurse (bank) for the Game
   */
  void initTokens();
  
  /**
   * Initializes the Actions for the Game
   */
  void initActions();
  
  
 	/**
 	 * Runs the game until there is a winner.
 	 */
 	void run();
 	
 	
 	
 	/**
   * Returns a CardDeck parsed from a given file, described by a String.
   * @param pathOfFile - the path to the file containing the cards information.
   * @return The CardDeck obtained by parsing the cards' file.
   * @throws IOException - if the file has not been found.
   */
  static CardDeck setupCards(String pathOfFile) throws IOException {
    Objects.requireNonNull(pathOfFile);
    return FileLoader.createCards(pathOfFile);
  }
  
  /**
   * Initializes the game's data for a new game.
   * @throws IOException - if the file containing the cards has not been found.
   */
  default void initGame() throws IOException {
    initCardDecks();
    initLevelConverter();
    initBoard();
    initPlayers();
    initTokens();
    initActions();
  }
  
  /**
   * This method calls the Displayer to display it.
   */
  default void displayGame() {
    getGameState().displayer().display(getGameState(), cardsColorsList());
  }
  
  /**
   * This method calculates and returns the maximum of the players' prestige points.
   * 
   * @return The maximum of the players' prestige points.
   */
  default int getMaxPrestige() {
    return getGameState().players().stream()
                                   .map(Player::prestigePoints)
                                   .max(Integer::compare)
                                   .orElse(0);
  }
  
  /**
   * This method returns the ID of the player who has won the game.
   * 
   * @return the winner's ID.
   */
  default int getWinner(List<Color> colors) {    
    Objects.requireNonNull(colors);
    return getGameState().players().stream()
                                   .filter(player -> player.id() == getMaxPrestige())
                                   .sorted(Comparator.comparingInt(player -> player.amountOfDevelopmentCards(colors))).limit(1)
                                   .map(Player::id)
                                   .reduce(0, Integer::sum);
  }

  /**
   * Allows the player, described by its ID, to choose an Action.
   * @param playerID - The player's ID.
   */
  default GameData chooseAction(int playerID, EnumMap<ActionType, GameAction> actions) { 
    Objects.requireNonNull(actions);
    var gameData = getGameState();
    
    if (playerID < 0 || playerID >= gameData.players().size()) {
      throw new IllegalArgumentException("Player doesn't exist");
    }
    
    while (!gameData.actionSucceed()) {
     var chosenActionType = gameData.displayer().getPlayerAction(actions, gameData.players().get(playerID).name());
     
      if(chosenActionType.equals(ActionType.UNKNOWN) || !actions.containsKey(chosenActionType)) {
        gameData.displayer().displayActionError("Uknown Action");
      } else if (chosenActionType.equals(ActionType.QUIT)) {
      	System.out.println("Bye bye, see you soon!!");
      	System.exit(1);
      } else {
        gameData = actions.get(chosenActionType).apply(playerID, gameData);
      }
    }
    
    gameData = (new GiveBackTokensAction()).apply(playerID, gameData);
    return new GameData(gameData.board(), gameData.decks(), gameData.noblesCards(), gameData.tokens(), gameData.players(), gameData.displayer(), gameData.levelToInteger(), false);
  }

  /**
   * Performs the action of nobles visiting a player
   * @param gameData - the game's data
   * @param playerID - the player's OD
   * @param noblesList - the nobles' list
   * @return the game's data after the visit
   */
  private static GameData noblesVisitingPlayer(GameData gameData, int playerID, List<Card> noblesList) {
    var hasNoble = false;
    for (var noble: noblesList) {
      if (!hasNoble && gameData.players().get(playerID).canGetNoble(noble)) {
        gameData.players().get(playerID).buyCard(noble, Color.getCardsColorsList());
        hasNoble = true; /*The player can only get one noble per turn, this will prevent them from getting another noble*/
      } else {
        gameData.noblesCards().push(noble, 0); /*We put all the other nobles back in the data*/
      }
    }
    
    return new GameData(gameData.board(), gameData.decks(), gameData.noblesCards(), gameData.tokens(), gameData.players(), gameData.displayer(), gameData.levelToInteger(), gameData.actionSucceed());
  }
  
  /**
   * The nobles visit a player (described by their ID) to see if they can get them.
   * @param playerID - the player's ID
   */
  default GameData visitOfNobles(int playerID) {
    var gameData = getGameState();
    
    if (playerID < 0 || playerID >= gameData.players().size()) {
      throw new IllegalArgumentException("Player doesn't exist");
    }
    
    var noblesList = new ArrayList<Card>();
    var numberOfNobles = gameData.noblesCards().amountOfCards();
    
    for (var i = 0; i < numberOfNobles; i++) {
      noblesList.add(gameData.noblesCards().remove(new Coordinate(0, 0)));
    }
    
    return noblesVisitingPlayer(gameData, playerID, noblesList);
  }
}
