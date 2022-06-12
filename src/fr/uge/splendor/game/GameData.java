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

/**
 * This record represents a game's data. In particular, we can find the main board,
 * the nobles' board, the cards decks, the tokens (the bank), the players,
 * the displayer for the game, a LeveL/Integer converter and a boolean describing
 * an action's success.
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 *
 */
public record GameData(Board board,
                       EnumMap<Level, CardDeck> decks,
                       Board noblesCards,
                       TokenPurse tokens,
                       ArrayList<Player> players,
                       Displayer displayer,
                       EnumMap<Level, Integer> levelToInteger,
                       boolean actionSucceed) {
  /**
   * Creates a GameData with all the needed parameters.
   * @param board - the main board with the development cards to buy
   * @param decks - the different cards decks
   * @param noblesCards - the board containing the nobles' cards
   * @param tokens - the game's TokenPurse (the bank)
   * @param players - the game's players
   * @param displayer - the game's displayer
   * @param levelToInteger - the game's Level/Integer converter
   * @param actionSucceed - the boolean describing an action's success.
   */
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
