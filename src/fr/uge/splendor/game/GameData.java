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
public record GameData(Board board,
                       EnumMap<Level, CardDeck> decks,
                       Board noblesCards,
                       TokenPurse tokens,
                       ArrayList<Player> players,
                       Displayer displayer,
                       EnumMap<Level, Integer> levelToInteger,
                       boolean actionSucceed) {
  public GameData {
    Objects.requireNonNull(board, "board can't be null");
    Objects.requireNonNull(decks, "decks can't be null");
    Objects.requireNonNull(noblesCards, "noblesCards can't be null");
    Objects.requireNonNull(tokens, "tokens can't be null");
    Objects.requireNonNull(players, "players can't be null");
    Objects.requireNonNull(displayer, "displayer can't be null");
    Objects.requireNonNull(levelToInteger, "level converter can't be null");
    
    players.forEach(player -> Objects.requireNonNull(player, "player can't be null"));
    decks.keySet().forEach(level -> Objects.requireNonNull(decks.get(level), "deck lv. " + level + " can't be null"));
  }
}
