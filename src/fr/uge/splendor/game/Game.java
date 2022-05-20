package fr.uge.splendor.game;

import java.io.IOException;
import java.nio.file.Path;

import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.utils.FileLoader;

public interface Game {
  
  /*NOt agree with Yu but still ..????*/
	static CardDeck setupCards() throws IOException {
	  return FileLoader.createCards(Path.of("res").resolve("base_game_cards.csv"));
	}
	
}
