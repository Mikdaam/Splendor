package fr.uge.splendor.game;

import java.io.IOException;

import fr.uge.splendor.deck.CardDeck;

public interface Game {
	CardDeck setupCards() throws IOException;
	
}
