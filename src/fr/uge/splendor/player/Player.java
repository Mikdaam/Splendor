package fr.uge.splendor.player;

import java.util.List;

import fr.uge.splendor.board.Board;
import fr.uge.splendor.card.Card;
import fr.uge.splendor.card.Coordinate;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.TokenPurse;

/**
 * This interface represents a player and the methods it should have.
 * A Player is a human or a bot who can play and interact with the game's objects.
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 *
 */
public sealed interface Player permits HumanPlayer {
  
  int id();
  
  String name();
  
  int prestigePoints();
  
  /**
   * A player takes one token of a given color.
   * @param color - the color of the token to take.
   */
  void takeToken(Color color);
  
  /**
   * Checks if a player can buy a given card.
   * 
   * @param card - the card the player wants to buy.
   * @return true if the player can buy the given card, false otherwise.
   */
  boolean canBuyCard(Card card);
  
  /**
   * Checks if a player can get a given noble.
   * 
   * @param noble - the noble the player wants to get.
   * @return true if the player can get the given noble, false otherwise.
   */
  boolean canGetNoble(Card noble);

  
  TokenPurse buyCard(Card card, List<Color> colors);
  
  String toString(List<Color> colors);

  int getNumberOfTokens();
  
  public Board reservedCards();

  void removeTokensColor(Color color);
  
  int removeTokens(Color color, int number);
  
  int numberOfDevelopmentCards(List<Color> colors);
  
  Card removeFromReserved(Coordinate coordinate);
  
  void pushToReserved(Card card);
  
  int numberOfReservedCards();
}
