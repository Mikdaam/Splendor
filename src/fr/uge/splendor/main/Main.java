package fr.uge.splendor.main;

import java.util.HashMap;

import fr.uge.splendor.card.*;
import fr.uge.splendor.game.level.*;
import fr.uge.splendor.token.*;
import fr.uge.splendor.color.*;
import fr.uge.splendor.deck.*;

public class Main {
  public static void main(String[] args) {
     var price = new HashMap<Color, Integer>();
     price.put(Color.SAPPHIRE, 5);
     price.put(Color.RUBY, 7);
     price.put(Color.ONYX, 6);
     
     var card = new DevelopmentCard(3, price, Level.LEVEL_3, Color.DIAMOND);
     System.out.println(card);
     
     System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
     
     var card2 = new DevelopmentCard(0, new HashMap<Color, Integer>(), Level.LEVEL_1, Color.RUBY);
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
     deck.add(new DevelopmentCard(0, new HashMap<Color, Integer>(), Level.LEVEL_1, Color.RUBY));
     deck.add(new DevelopmentCard(0, new HashMap<Color, Integer>(), Level.LEVEL_1, Color.RUBY));
     deck.add(new DevelopmentCard(0, new HashMap<Color, Integer>(), Level.LEVEL_1, Color.EMERALD));
     deck.add(new DevelopmentCard(0, new HashMap<Color, Integer>(), Level.LEVEL_1, Color.EMERALD));
     deck.add(new DevelopmentCard(0, new HashMap<Color, Integer>(), Level.LEVEL_1, Color.SAPPHIRE));
     
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
  }
}
