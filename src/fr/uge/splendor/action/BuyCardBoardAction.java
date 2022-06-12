package fr.uge.splendor.action;

import java.util.Objects;

import fr.uge.splendor.card.Coordinate;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.game.GameData;
import fr.uge.splendor.level.Level;
import fr.uge.splendor.utils.Utils;

/**
 * This record represents the action of buying a card from the board where the game takes place.
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
 */
public record BuyCardBoardAction() implements GameAction {  
  
  
  /**
   * TODO: find a better way to pass the player id;
   * TODO: Make Yunen change ..... (add variable which contains possible colors ) ????
   */
  @Override
  public GameData apply(int playerId, GameData gameData) {
    Objects.requireNonNull(gameData, "Game can't be null");
    
    var cardPosition = gameData.displayer().getCoordinates();
    var level = Level.getLevel(cardPosition.row());
    
    if (!checkLevel(gameData, level)) {
      return gameData;
    }
    
    cardPosition = new Coordinate(gameData.levelToInteger().get(level), cardPosition.column());
    
    if (!gameData.board().canRemove(cardPosition)) {
      gameData.displayer().displayActionError("There is no card to buy here...");
      return gameData;
    }
    
    var card = gameData.board().remove(cardPosition);
    var tokens = gameData.tokens();
    
    if(gameData.players().get(playerId).canBuyCard(card)) {
      tokens = tokens.add(gameData.players().get(playerId).buyCard(card, Utils.cardsColorsList()));
      var newCard = gameData.decks().get(level).removeFirstCard();
      if (!newCard.color().equals(Color.EMPTY)) {
        gameData.board().push(newCard, cardPosition.row());
      }
    } else {
      gameData.board().push(card, cardPosition.row());
      gameData.displayer().displayActionError("You are not able to buy the Card.");
      return gameData;
    }
    
    return new GameData(gameData.board(), gameData.decks(), gameData.noblesCards(), tokens, gameData.players(), gameData.displayer(), gameData.levelToInteger(), true);
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
