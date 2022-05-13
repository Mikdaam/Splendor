package fr.uge.splendor.player;

import java.util.HashMap;

import fr.uge.splendor.card.Card;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.token.Token;

public sealed interface Player permits HumanPlayer {
  
  
  boolean takeToken(Token token);
  
  //boolean reserveCard();
  
  boolean canBuyCard(Card card);
  
  HashMap<Color, Integer> buyCard(Card card);
  
  boolean doAction();
  
  String toString();
  
  void display(boolean isASCII);
  
}
