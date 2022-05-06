package fr.uge.splendor.player;

import fr.uge.splendor.card.*;
import fr.uge.splendor.token.*;
import fr.uge.splendor.color.*;

import java.util.Objects;
import java.util.HashMap;
import java.util.ArrayList;

public final class HumanPlayer implements Player {
  
  private final int id;
  private final String name;
  private final HashMap<Color, ArrayList<Card>> ownedCards;
  //private final HashMap<Color, ArrayList<Card>> reservedCards;
  private final HashMap<Color, ArrayList<Token>> ownedTokens;
  
  
  public HumanPlayer(int id, String name) {
    Objects.requireNonNull(name, "The player's name cannot be null!");
    
    if (id < 0) {
      throw new IllegalArgumentException("The ID should be zero or positive!");
    }
    
    this.id = id;
    this.name = name;
    this.ownedCards = new HashMap<Color, ArrayList<Card>>();
    //this.reservedCards = new HashMap<Color, ArrayList<Card>>();
    this.ownedTokens = new HashMap<Color, ArrayList<Token>>();
  }
  
  @Override
  public boolean takeToken() {
    return true;
  }
  
  /*@Override
  public boolean reserveCard() {
    return true;
  }*/
  
  @Override
  public boolean buyCard() {
    return true;
  }
  
  @Override
  public boolean doAction() {
    return true;
  }
  
  @Override
  public String toString() {
    return "";
  }
  
  private String displayASCII() {
    return "";
  }
  
  @Override
  public void display(boolean isASCII) {
    
  }
}
