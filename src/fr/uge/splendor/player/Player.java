package fr.uge.splendor.player;

import java.util.HashMap;

import fr.uge.splendor.card.Card;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.token.Token;

public sealed interface Player permits HumanPlayer {
  
  /*Check if we need bool*/
  void takeToken(Token token);
  
  //boolean reserveCard();
  
  boolean canBuyCard(Card card);
  
  int id();
  
  String name();
  
  int prestigePoints();
  
  HashMap<Color, Integer> buyCard(Card card);
  
  String toString();

  int getNumberOfTokens();

  void removeTokensColor(Color gold);

}
