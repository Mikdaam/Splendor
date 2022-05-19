package fr.uge.splendor.main;

import java.io.IOException;
import java.util.HashMap;

import fr.uge.splendor.card.*;
import fr.uge.splendor.game.SimpleGame;
import fr.uge.splendor.level.*;
import fr.uge.splendor.token.*;
import fr.uge.splendor.utils.Utils;
import fr.uge.splendor.color.*;
import fr.uge.splendor.deck.*;

public class Main {
  public static void main(String[] args) throws IOException {
     var price = new HashMap<Color, Integer>();
     price.put(Color.SAPPHIRE, 5);
     price.put(Color.RUBY, 7);
     price.put(Color.ONYX, 6);
     
     var card = new DevelopmentCard(Level.LEVEL_3, Color.DIAMOND, 3, price);
     System.out.println(card);
     
     System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
     
     var card2 = new DevelopmentCard(Level.LEVEL_1, Color.RUBY, 0, new HashMap<Color, Integer>());
     System.out.println(card2);
     
     var diamondToken = new BaseToken(Color.DIAMOND);
     var emeraldToken = new BaseToken(Color.EMERALD);
     var goldToken = new BaseToken(Color.GOLD);
     
     System.out.println(diamondToken);
     System.out.println(emeraldToken);
     System.out.println(goldToken);
     
     var deck = new CardDeck();
     deck.add(card);
     deck.add(card2);
     deck.add(new DevelopmentCard(Level.LEVEL_1, Color.RUBY, 0, new HashMap<Color, Integer>()));
     deck.add(new DevelopmentCard(Level.LEVEL_1, Color.RUBY, 0, new HashMap<Color, Integer>()));
     deck.add(new DevelopmentCard(Level.LEVEL_1, Color.EMERALD, 0, new HashMap<Color, Integer>()));
     deck.add(new DevelopmentCard(Level.LEVEL_1, Color.EMERALD, 0, new HashMap<Color, Integer>()));
     deck.add(new DevelopmentCard(Level.LEVEL_1, Color.SAPPHIRE, 0, new HashMap<Color, Integer>()));
     
     System.out.println(deck);
     
     
     var tokenDeck = new TokenDeck();
     var tokenMap = new HashMap<Token, Integer>();
     
     tokenMap.put(diamondToken, 3);
     tokenMap.put(emeraldToken, 1);
     tokenMap.put(goldToken, 5);
     
     tokenDeck.add(tokenMap);
     
     System.out.println(tokenDeck);
     
     
     var tokentoRemove = new HashMap<Color, Integer>();
     tokentoRemove.put(Color.DIAMOND, 2);
     tokentoRemove.put(Color.EMERALD, 1);
     tokentoRemove.put(Color.GOLD, 1);
     
     tokenDeck.remove(tokentoRemove);
     System.out.println(tokenDeck);
     
     var game = new SimpleGame();
     var deckCardsGame = game.setupCards();
     System.out.println(deckCardsGame);
     
     deckCardsGame.displayCards();
     
     System.out.println("=====================================================");
     
     var deck2 = new CardDeck();
     deck2.add(card);
     deck2.add(card2);
     deck2.add(card);
     deck2.displayCards();
  }
}
