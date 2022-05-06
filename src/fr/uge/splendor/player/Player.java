package fr.uge.splendor.player;

public sealed interface Player permits HumanPlayer {
  
  
  boolean takeToken();
  
  //boolean reserveCard();
  
  boolean buyCard();
  
  boolean doAction();
  
  String toString();
  
  void display(boolean isASCII);
  
}
