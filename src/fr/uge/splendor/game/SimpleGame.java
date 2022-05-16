package fr.uge.splendor.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import fr.uge.splendor.deck.CardDeck;

public class SimpleGame implements Game {
  public void shuffleCard() {
    
  }
  
  public void buyCard() {
    
  }
  
  public void takeToken() {
    
  }

  @Override
  public CardDeck setupCards() {
    try(var csvReader = new BufferedReader(new Path("cards.csv"))) {
      var cards = new CardDeck();
      var line = csvReader.readLine();
      while(line != null) {
        var values = line.split(",");
        var card = new DevelopmentCard();
      }

	  return null;
  }
}
