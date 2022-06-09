package fr.uge.splendor.game;

import java.util.ArrayList;
import java.util.Objects;

import fr.uge.splendor.action.Action;
import fr.uge.splendor.board.Board;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.deck.TokenDeck;
import fr.uge.splendor.displayer.Displayer;
import fr.uge.splendor.player.Player;

// TODO: Change array to ArrayList here
public record GameData(Board board, CardDeck[] decks, TokenDeck tokens, Player[] players, Action[] actions, Displayer displayer) {
  public GameData {
    Objects.requireNonNull(board, "Board can't be null");
  }
}
