package fr.uge.splendor.displayer;

import fr.uge.splendor.action.ActionType;
import fr.uge.splendor.board.Board;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.deck.TokenDeck;
import fr.uge.splendor.player.Player;

public sealed interface Displayer permits ConsoleDisplayer {
  public void display(Player[] players, CardDeck[] cardDecks, TokenDeck tokenDecks, Board gameBoard);
  
  void clear();
  
  public ActionType getUserAction();
  
  public int[] getCoordinates();

  public Color getUniqueColor();
  
}
