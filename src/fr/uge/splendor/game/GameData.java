package fr.uge.splendor.game;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Objects;

import fr.uge.splendor.board.Board;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.deck.TokenPurse;
import fr.uge.splendor.displayer.Displayer;
import fr.uge.splendor.level.Level;
import fr.uge.splendor.player.Player;

// TODO: Change array to ArrayList here
public record GameData(Board board, EnumMap<Level, CardDeck> decks, Board noblesCards, TokenPurse tokens, ArrayList<Player> players, Displayer displayer, boolean actionSucceed) {
  public GameData {
    Objects.requireNonNull(board, "Board can't be null");
    /*TODO: Check the entry */
  }
}
