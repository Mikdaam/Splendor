package fr.uge.splendor.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;

import fr.uge.splendor.card.DevelopmentCard;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.deck.CardDeck;
import fr.uge.splendor.level.Level;

public class FileLoader {
  
  private static HashMap<Color, Integer> parsePrice(String priceString) {
    var price = new HashMap<Color, Integer>();
    var pricesType = priceString.split("\\+");
    
    Arrays.stream(pricesType)
          .forEach(element -> {
            var info = element.split("_");
            price.computeIfAbsent(Color.getColor(info[1]), val -> Integer.parseInt(info[0]));
          });
    
    return price;
  }
  
  public static CardDeck createCards(Path cardsFile) throws IOException {
    var gameCards = new CardDeck();
    
     try (var csvReader = Files.newBufferedReader(cardsFile)) {
       String line;
       while ((line = csvReader.readLine()) != null) {
         var cardComponents = line.split(",");
         int cardPrestige = Integer.parseInt(cardComponents[2]);
         var price = parsePrice(cardComponents[3]);
         gameCards.add(new DevelopmentCard(Level.getLevel(cardComponents[0]), Color.getColor(cardComponents[1]), cardPrestige, price));
       }
     }
     
     return gameCards;
  }
}
