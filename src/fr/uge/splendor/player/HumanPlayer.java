package fr.uge.splendor.player;

import java.util.List;
import java.util.Objects;

import fr.uge.splendor.board.Board;
import fr.uge.splendor.card.*;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.deck.TokenPurse;

/**
 * This class represents a human player.
 * A HumanPlayer is a human who can play and interact with the game's objects.
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 *
 */
public final class HumanPlayer implements Player {
  private final int id;
  private final String name;
  private final CardDeck ownedCards;
  private TokenPurse ownedTokens;
  private final Board reservedCards;
  
  /**
   * Creates a HumanPlayer with a given id and name.
   * @param id - the human player's id
   * @param name - the human player's name
   */
  public HumanPlayer(int id, String name) {
    if (id < 0) {
      throw new IllegalArgumentException("The ID should be positive!");
    }
    Objects.requireNonNull(name, "The player's name cannot be null!");
    
    this.id = id;
    this.name = name;
    ownedCards = new CardDeck("PL. " + id);
    reservedCards = new Board(1, 3);
    ownedTokens = new TokenPurse();
    this.initTokens();
    
  }
  
  /**
   * Initializes the human player's TokenPurse with every color to 0.
   */
  private void initTokens() {
    ownedTokens = ownedTokens.addToken(Color.DIAMOND, 0);
    ownedTokens = ownedTokens.addToken(Color.EMERALD, 0);
    ownedTokens = ownedTokens.addToken(Color.ONYX, 0);
    ownedTokens = ownedTokens.addToken(Color.RUBY, 0);
    ownedTokens = ownedTokens.addToken(Color.SAPPHIRE, 0);
    ownedTokens = ownedTokens.addToken(Color.GOLD, 0);
  }
  
  /**
   * Returns the player's id number.
   * @return The player's id number.
   */
  @Override
  public int id() {
    return id;
  }
  
  /**
   * Returns the player's name.
   * @return The player's name.
   */
  @Override
  public String name() {
    return name;
  }
  
  /**
   * Returns the player's amount of prestige points.
   * @return The player's amount of prestige points.
   */
  @Override
  public int prestigePoints() {
    return ownedCards.getPrestigePoints();
  }
  
  /**
   * Returns the reserved cards a player has.
   * @return The reserved cards a player has.
   */
  @Override
  public Board reservedCards() {
    return reservedCards;
  }
  
  
  
  /**
   * Removes a type (color) of tokens from a player's TokenPurse.
   * @param color - the color to remove from the player's TokenPurse.
   */
  @Override
  public void removeTokensColor(Color color) {
    Objects.requireNonNull(color);    
    ownedTokens = ownedTokens.removeColor(color);
  }
  
  /**
   * A player takes one token of a given color.
   * @param color - the color of the token to take.
   */
  @Override
  public void takeToken(Color color) {
    Objects.requireNonNull(color);
    
    if (color.equals(Color.EMPTY)) {
      throw new IllegalArgumentException("You cannot take a Token of an Empty Color");
    }
    
    ownedTokens = ownedTokens.addToken(color, 1);
  }
  
  /**
   * Removes a given amount of tokens of a certain color from a player's TokenPurse.
   * @param color - the color of the tokens to remove.
   * @param amount - the amount of tokens to remove.
   * @return The real amount of tokens removed.
   */
  @Override
  public int removeTokens(Color color, int amount) {
    Objects.requireNonNull(color);
    
    if (color.equals(Color.EMPTY)) {
      throw new IllegalArgumentException("You cannot take a Token of an Empty Color");
    }
    
    if (amount < 0 || amount > ownedTokens.getColorAmount(color)) {
      return 0;
    }
    
    ownedTokens = ownedTokens.remove(new TokenPurse().addToken(color, amount));
    return amount;
  }
  
  /**
   * Returns the total amount of tokens a player has.
   * @return The total amount of tokens a player has.
   */
  @Override
  public int getAmountOfTokens() {
    return ownedTokens.amountOfTokens();
  }
  
  
  
  /**
   * Returns the amount of development cards a player has.
   * @param colors - the list of possible card colors for the current game.
   * @return The amount of development cards a player has.
   */
  @Override
  public int amountOfDevelopmentCards(List<Color> colors) {
    Objects.requireNonNull(colors);
    var summary = ownedCards.getDeckSummary(colors);
    var amount = 0;
    
    for (var color: summary.keySet()) {
      if (color != Color.NOBLE) {
        amount += summary.getOrDefault(color, 0);
      }
    }
    
    return amount;
  }
  
  /**
   * Checks if a player can get a given noble.
   * 
   * @param noble - the noble the player wants to get.
   * @return true if the player can get the given noble, false otherwise.
   */
  @Override
  public boolean canGetNoble(Card noble) {
   Objects.requireNonNull(noble);
    
    for (var color: noble.price().keySet()) {
      var price = noble.price().get(color);
      if (ownedCards.getColorAmount(color) < price) {
        return false;
      }
    }
    
    return true;
  }
  
  /**
   * Checks if a player can buy a given card.
   * 
   * @param card - the card the player wants to buy.
   * @return true if the player can buy the given card, false otherwise.
   */
  @Override
  public boolean canBuyCard(Card card) {
    Objects.requireNonNull(card);
    
    var jokers =  ownedTokens.getColorAmount(Color.GOLD); /*The amount of jokers if the player has some*/
    
    for (var color: card.price().keySet()) {
      var price = card.price().get(color);
      if (ownedCards.getColorAmount(color) + ownedTokens.getColorAmount(color) < price) {
        if (ownedCards.getColorAmount(color) + ownedTokens.getColorAmount(color) + jokers >= price) {
          jokers -= price - ownedCards.getColorAmount(color) - ownedTokens.getColorAmount(color); /*If we have to use some jokers*/
        } else {
          return false;
        }
      }
    }
    
    return true;
  }
  
  /**
   * Executes the action of buying a given card for the player.
   * @param card - the card to buy.
   * @param colors - the list of possible card colors for the current game.
   * @return A TokenPurse containing the amount of tokens the player gives back to game.
   */
  @Override
  public TokenPurse buyCard(Card card, List<Color> colors) {
    Objects.requireNonNull(card);
    Objects.requireNonNull(colors);
    
    var tokensToGiveBack = new TokenPurse();
    var deckSummary = ownedCards.getDeckSummary(colors);
    var jokers = 0;
    
    for (var color: card.price().keySet()) {
      var price = card.price().get(color);
     
      if (deckSummary.getOrDefault(color, 0) < price) { /*Check if there is enough bonus or not*/
        var potentialJoker = 0; /*Potential gold to add*/
        if ((ownedTokens.getColorAmount(color) + deckSummary.getOrDefault(color, 0)) < price) { /*if not enough with tokens + bonuses*/
          potentialJoker = price - ownedTokens.getColorAmount(color) - deckSummary.getOrDefault(color, 0); /*Calculating the jokers to use there*/
        }
        
        tokensToGiveBack = tokensToGiveBack.addToken(color, price - deckSummary.getOrDefault(color, 0) - potentialJoker); /*tokens = price - bonus - potentialJoker*/
        jokers += potentialJoker;
      }
    }
    
    if (jokers != 0) {
      tokensToGiveBack = tokensToGiveBack.addToken(Color.GOLD, jokers);
    }
    
    ownedCards.add(card);
    ownedTokens = ownedTokens.remove(tokensToGiveBack);
    return tokensToGiveBack;
  }
  
  
  /**
   * Performs the action of removing a card of given coordinates from the player's
   * reserved cards board.
   * @param coordinate - the reserved card's coordinates on the player's board.
   * @return The removed reserved card.
   */
  @Override
  public Card removeFromReserved(Coordinate coordinate) {
    Objects.requireNonNull(coordinate);
    return reservedCards.remove(coordinate);
  }
  
  /**
   * Performs the action of pushing a card on the player's reserved cards board.
   * @param card - the reserved card to push.
   */
  @Override
  public void pushToReserved(Card card) {
    Objects.requireNonNull(card);
    reservedCards.push(card, 0);
  }
  
  /**
   * Returns the amount of reserved cards a player has.
   * @return The amount of reserved cards a player has.
   */
  @Override
  public int amountOfReservedCards() {
    return reservedCards.amountOfCards();
  }
  
  
   
  /**
   * Returns a String describing a player with their id, amount of prestige points,
   * owned cards, owned tokens and potentially their owned reserved cards if they have some.
   * @param colors - the list of possible card colors for the current game.
   * @return A String describing a player
   */
  @Override
  public String toString(List<Color> colors) {
    Objects.requireNonNull(colors);
    return this.playerToString(id, this.prestigePoints(), ownedTokens, ownedCards, reservedCards, colors);
  }
}
