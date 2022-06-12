package fr.uge.splendor.action;

import java.util.Objects;

import fr.uge.splendor.card.Coordinate;
import fr.uge.splendor.game.GameData;
import fr.uge.splendor.level.Level;
import fr.uge.splendor.utils.Utils;

/**
 * This record represents the action of buying a card from the board where the game takes place.
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 */
public record BuyCardBoardAction() implements GameAction {  
  
  /**
   * Performs the real action of buying a card from the board.
   * @param playerId - the player's ID
   * @param gameData - the game's data
   * @param cardPosition - the card's position on the board
   * @param level - the level corresponding to the row
   * @return the game's data updated after buying the card
   */
  private static GameData buyCard(int playerId, GameData gameData, Coordinate cardPosition, Level level) {
    if (!gameData.board().canRemove(cardPosition)) {
      gameData.displayer().displayActionError("There is no card to buy here...");
      return gameData;
    }
    
    var card = gameData.board().remove(cardPosition);
    var tokens = gameData.tokens();
    
    if(gameData.players().get(playerId).canBuyCard(card)) {
      tokens = tokens.add(gameData.players().get(playerId).buyCard(card, Utils.cardsColorsList())); /*We add the tokens the player is giving back to the game*/
      if (gameData.decks().get(level).size() > 0) { /*Check if we can pull another card from the deck of same level*/
        gameData.board().push(gameData.decks().get(level).removeFirstCard(), cardPosition.row());
      }
    } else {
      gameData.board().push(card, cardPosition.row()); /*We put the card back on the row*/
      gameData.displayer().displayActionError("You are not able to buy the Card.");
      return gameData;
    }
    
    return new GameData(gameData.board(), gameData.decks(), gameData.noblesCards(), tokens, gameData.players(), gameData.displayer(), gameData.levelToInteger(), true);
  }
  
  /**
   * Performs the action of a player buying a card from the board.
   * @param playerId - the player's ID
   * @param gameData - the game's Data
   * @return the updated game's data after the action
   */
  @Override
  public GameData apply(int playerId, GameData gameData) {
    Objects.requireNonNull(gameData, "Game can't be null");
    checkPlayerID(gameData, playerId);
    
    var cardPosition = gameData.displayer().getCoordinates();
    var level = Level.getLevel(cardPosition.row());
    
    if (!checkLevel(gameData, level) || !gameData.decks().containsKey(level)) { 
      gameData.displayer().displayActionError("You have entered a wrong level...");
      return gameData;
    }
    
    /*Creates a new coordinates with the level corresponding to the good row*/
    cardPosition = new Coordinate(gameData.levelToInteger().get(level), cardPosition.column());
    
    return buyCard(playerId, gameData, cardPosition, level);
  }
  
  /**
   * This method returns a String describing the action of buying a card from the board.
   * 
   * @return - The String describing the action of buying a card from the board.
   */
  @Override
  public String toString() {
    return "Buy a card from the board.";
  }
}
