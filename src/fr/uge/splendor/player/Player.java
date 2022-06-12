package fr.uge.splendor.player;

import java.util.List;
import java.util.Objects;

import fr.uge.splendor.board.Board;
import fr.uge.splendor.card.Card;
import fr.uge.splendor.card.Coordinate;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.CardDeck;
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
  
  /**
   * Returns the player's id number.
   * @return The player's id number.
   */
  int id();
  
  /**
   * Returns the player's name.
   * @return The player's name.
   */
  String name();
  
  /**
   * Returns the player's amount of prestige points.
   * @return The player's amount of prestige points.
   */
  int prestigePoints();
  
  /**
   * Returns the reserved cards a player has.
   * @return The reserved cards a player has.
   */
  public Board reservedCards();
  
  
  
  /**
   * Removes a type (color) of tokens from a player's TokenPurse.
   * @param color - the color to remove from the player's TokenPurse.
   */
  void removeTokensColor(Color color);
  
  /**
   * A player takes one token of a given color.
   * @param color - the color of the token to take.
   */
  void takeToken(Color color);
  
  /**
   * Removes a given amount of tokens of a certain color from a player's TokenPurse.
   * @param color - the color of the tokens to remove.
   * @param amount - the amount of tokens to remove.
   * @return The real amount of tokens removed.
   */
  int removeTokens(Color color, int amount);
  
  /**
   * Returns the total amount of tokens a player has.
   * @return The total amount of tokens a player has.
   */
  int getAmountOfTokens();
  
  
  
  /**
   * Returns the amount of development cards a player has.
   * @param colors - the list of possible card colors for the current game.
   * @return The amount of development cards a player has.
   */
  int amountOfDevelopmentCards(List<Color> colors);
  
  /**
   * Checks if a player can get a given noble.
   * 
   * @param noble - the noble the player wants to get.
   * @return true if the player can get the given noble, false otherwise.
   */
  boolean canGetNoble(Card noble);
  
  /**
   * Checks if a player can buy a given card.
   * 
   * @param card - the card the player wants to buy.
   * @return true if the player can buy the given card, false otherwise.
   */
  boolean canBuyCard(Card card);
  

  /**
   * Executes the action of buying a given card for the player.
   * @param card - the card to buy.
   * @param colors - the list of possible card colors for the current game.
   * @return A TokenPurse containing the amount of tokens the player gives back to game.
   */
  TokenPurse buyCard(Card card, List<Color> colors);
  
  
  
  /**
   * Performs the action of removing a card of given coordinates from the player's
   * reserved cards board.
   * @param coordinate - the reserved card's coordinates on the player's board.
   * @return The removed reserved card.
   */
  Card removeFromReserved(Coordinate coordinate);
  
  /**
   * Performs the action of pushing a card on the player's reserved cards board.
   * @param card - the reserved card to push.
   */
  void pushToReserved(Card card);
  
  /**
   * Returns the amount of reserved cards a player has.
   * @return The amount of reserved cards a player has.
   */
  int amountOfReservedCards();
  
  
  
  /**
   * Returns a String describing the player's ID and amount of prestige points
   * @return A String describing the player's ID and amount of prestige points
   */
  private String firstRowToString(int id, int prestigePoints) {
    var sb = new StringBuilder("┌───────────────────────────────────┐\n");
    
    sb.append("│  PLAYER ").append(id)
      .append("  │  ").append(String.format("%02d", prestigePoints))
      .append(" PRESTIGE POINTS  │\n")
      .append("└───────────────────────────────────┘\n");
    
    return sb.toString();
  }
  
  /**
   * Returns a String describing the player's TokenPurse.
   * @return A String describing the player's TokenPurse.
   */
  private String tokensToString(TokenPurse tokens) {
    var sb = new StringBuilder(" TOKENS:\n");
    sb.append(tokens.toString())
      .append("\n");
    
    return sb.toString();
  }
  
  /**
   * Returns a String describing the player's owned cards.
   * @return A String describing the player's owned cards.
   */
  private String ownedCardsToString(CardDeck cards, List<Color> colors) {
    var sb = new StringBuilder();
    
    sb.append(" CARDS:\n")
      .append(cards.deckSummaryToString(colors))
      .append("\n");
    
    return sb.toString();
  }
  
  /**
   * Returns a String describing the player's reserved cards.
   * @return A String describing the player's reserved cards.
   */
  private String reservedCardsToString(Board reservedCards) {
    if(amountOfReservedCards() == 0) {
      return "";
    }
    
    var sb = new StringBuilder();
    sb.append(" RESERVED CARDS:\n")
      .append(reservedCards)
      .append("\n");
    
    return sb.toString();
  }
  
  /**
   * Returns a String describing a player with their id, amount of prestige points,
   * owned cards, owned tokens and potentially their owned reserved cards if they have some.
   * @param id - the player's id
   * @param prestigePoints - the player's amount of prestige points
   * @param tokens - the player's TokenPurse
   * @param cards - the player's owned cards
   * @param reservedCards - the player's reserved cards
   * @param colors - the list of possible colors for the development cards.
   * @return A String describing a player.
   */
  default String playerToString(int id, int prestigePoints, TokenPurse tokens, CardDeck cards, Board reservedCards, List<Color> colors) {
    Objects.requireNonNull(tokens);
    Objects.requireNonNull(cards);
    Objects.requireNonNull(reservedCards);
    Objects.requireNonNull(colors);
    
    var sb = new StringBuilder();
    sb.append(firstRowToString(id, prestigePoints))
      .append(tokensToString(tokens))
      .append(ownedCardsToString(cards, colors))
      .append(reservedCardsToString(reservedCards))
      .append("\n");
    
    return sb.toString();
  }
  
  /**
   * Returns a String describing a player with their id, amount of prestige points,
   * owned cards, owned tokens and potentially their owned reserved cards if they have some.
   * @param colors - the list of possible card colors for the current game.
   * @return A String describing a player
   */
  String toString(List<Color> colors);
}
