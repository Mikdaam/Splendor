package fr.uge.splendor.main;

import java.util.HashMap;

import fr.uge.splendor.card.*;
import fr.uge.splendor.game.level.*;
import fr.uge.splendor.color.*;

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
  }
}
