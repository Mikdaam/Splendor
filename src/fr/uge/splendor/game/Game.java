package fr.uge.splendor.game;

import java.io.IOException;
import java.nio.file.Path;

import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.utils.FileLoader;

public interface Game {
  
  /*NOt agree with Yu but still ..????*/
	static CardDeck setupCards(Path pathOfFile) throws IOException {
	  return FileLoader.createCards(pathOfFile);
	}
	
	void initGame() throws IOException;
	
	void run();
}
