package fr.uge.splendor.player;

import java.util.List;

import fr.uge.splendor.board.Board;
import fr.uge.splendor.card.Card;
import fr.uge.splendor.card.Coordinate;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.TokenPurse;

public sealed interface Player permits HumanPlayer {
  
  /*Check if we need bool*/
  void takeToken(Color color);
  
  //boolean reserveCard();
  
  boolean canBuyCard(Card card);
  
  boolean canGetNoble(Card noble);
  
  int id();
  
  String name();
  
  int prestigePoints();
  
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
