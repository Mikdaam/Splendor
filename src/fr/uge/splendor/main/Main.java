package fr.uge.splendor.main;

import java.io.IOException;
import java.util.Scanner;

import fr.uge.splendor.game.*;

public class Main {
  public static void main(String[] args) throws IOException {
     /*var price = new HashMap<Color, Integer>();
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
     var tokenMap = new HashMap<Color, Integer>();
     
     tokenMap.put(diamondToken.color(), 3);
     tokenMap.put(emeraldToken.color(), 1);
     tokenMap.put(goldToken.color(), 5);
     
     tokenDeck.add(tokenMap);
     
     System.out.println(tokenDeck);
     
     
     var tokentoRemove = new HashMap<Color, Integer>();
     tokentoRemove.put(Color.DIAMOND, 2);
     tokentoRemove.put(Color.EMERALD, 1);
     tokentoRemove.put(Color.GOLD, 1);
     
     tokenDeck.remove(tokentoRemove);
     System.out.println(tokenDeck);
     
     var game = new SimpleGame();
     Path pathOfFile = Path.of("res").resolve("base_game_cards.csv");
     var deckCardsGame = Game.setupCards(pathOfFile);
     System.out.println(deckCardsGame);
     
     deckCardsGame.displayCards();
     
     System.out.println("=====================================================");
     
     var deck2 = new CardDeck();
     deck2.add(card);
     deck2.add(card2);
     deck2.add(card);
     deck2.displayCards();
     
     System.out.println(deck2.deckSummaryToString());*/
     
     /*var player = new HumanPlayer(0, "Yunen");
     
     System.out.println(player);
     System.out.println("Player can buy free card? " + player.canBuyCard(card2));
     System.out.println("Player can buy expensive card? " + player.canBuyCard(card));
     player.buyCard(card2);
     System.out.println(player);
     
     player.takeToken(new BaseToken(Color.SAPPHIRE));
     player.takeToken(new BaseToken(Color.SAPPHIRE));
     player.takeToken(new BaseToken(Color.SAPPHIRE));
     player.takeToken(new BaseToken(Color.SAPPHIRE));
     player.takeToken(new BaseToken(Color.SAPPHIRE));
     
     player.takeToken(new BaseToken(Color.ONYX));
     player.takeToken(new BaseToken(Color.ONYX));
     player.takeToken(new BaseToken(Color.ONYX));
     player.takeToken(new BaseToken(Color.ONYX));
     player.takeToken(new BaseToken(Color.ONYX));
     player.takeToken(new BaseToken(Color.ONYX));
     
     player.takeToken(new BaseToken(Color.RUBY));
     player.takeToken(new BaseToken(Color.RUBY));
     player.takeToken(new BaseToken(Color.RUBY));
     player.takeToken(new BaseToken(Color.RUBY));
     player.takeToken(new BaseToken(Color.RUBY));
     player.takeToken(new BaseToken(Color.RUBY));
     player.takeToken(new BaseToken(Color.RUBY));
     
     System.out.println(player);
     System.out.println("Player can buy expensive card? " + player.canBuyCard(card));
     player.buyCard(card);
     System.out.println(player);*/
     
     
     /*var board = new Board(3, 4);
     board.add(deckCardsGame.removeFirstCard(), 0, 0);
     board.add(deckCardsGame.removeFirstCard(), 0, 1);
     board.add(deckCardsGame.removeFirstCard(), 0, 2);
     board.add(deckCardsGame.removeFirstCard(), 0, 3);
     board.add(deckCardsGame.removeFirstCard(), 1, 0);
     board.add(deckCardsGame.removeFirstCard(), 1, 1);
     board.add(deckCardsGame.removeFirstCard(), 1, 2);
     board.add(deckCardsGame.removeFirstCard(), 1, 3);
     board.add(deckCardsGame.removeFirstCard(), 2, 0);
     board.add(deckCardsGame.removeFirstCard(), 2, 1);
     board.add(deckCardsGame.removeFirstCard(), 2, 2);
     board.add(deckCardsGame.removeFirstCard(), 2, 3);
     
     System.out.println(board.toString());*/
  	
  	Game game;
  	Scanner mainInput = new Scanner(System.in);
  	
  	System.out.println("1. SimpleGame");
  	System.out.println("2. NormalGame");
  	System.out.println("Choose the version of the game: ");
    var gameChoice = mainInput.nextInt();
  	
    if(gameChoice == 1) {
    	game = new SimpleGame();
    } else {
    	game = new NormalGame(2);
    }
    
    game.initGame();
    game.run();
  }
}
