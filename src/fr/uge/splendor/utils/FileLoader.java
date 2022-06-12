package fr.uge.splendor.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Objects;

import fr.uge.splendor.card.DevelopmentCard;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.level.Level;

/**
 * This class represents a FileLoader.
 * It's used to load the game cards form a file.
 * Exemple of usage in {@link fr.uge.splendor.game.SimpleGame} class.
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 *
 */
public class FileLoader {
  
	/**
	 * Parse a given string into an EnumMap
	 * 
	 * @param priceString	the string represented the price
	 * @return 						a parsed price into an EnumMap
	 */
  private static EnumMap<Color, Integer> parsePrice(String priceString) {
    Objects.requireNonNull(priceString);
    
    var price = new EnumMap<Color, Integer>(Color.class);
    var pricesType = priceString.split("\\+");
    
    Arrays.stream(pricesType)
          .forEach(element -> {
            var info = element.split("_");
            price.computeIfAbsent(Color.getColor(info[1]), val -> Integer.parseInt(info[0]));
          });
    
    return price;
  }
  
  /**
   * Create a cardDeck from the given file. The file must be exist in certain
   * format in order to create the right cards.
   * 
   * @param cardsFile	the path of the file
   * @return 					the cardDeck of the file
   * @throws IOException raise an exception in case of error
   */
  public static CardDeck createCards(Path cardsFile) throws IOException {
    Objects.requireNonNull(cardsFile);
    
    var gameCards = new CardDeck("LV. ?");
    
     try (var csvReader = Files.newBufferedReader(cardsFile)) {	 
    	 csvReader.lines()
    	 				.skip(1) /* Skip the first line of the file */
    	 				.forEach(line -> {
    	 					var cardComponents = line.split(",");
    	          int cardPrestige = Integer.parseInt(cardComponents[2]);
    	          var price = parsePrice(cardComponents[3]);
    	          gameCards.add(new DevelopmentCard(Level.getLevel(cardComponents[0]), Color.getColor(cardComponents[1]), cardPrestige, price));
    	 				});
     }
     
     return gameCards;
  }
}
