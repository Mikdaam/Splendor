package fr.uge.splendor.board;

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Objects;

import fr.uge.splendor.card.Card;
import fr.uge.splendor.card.Coordinate;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.utils.Utils;

public class Board {
	private final ArrayList<ArrayList<Card>> board;
  private final int rows;
  private final int columns;
  
  public Board(int rows, int columns) {
    if (rows < 0 || columns < 0) {
      throw new IllegalArgumentException("Your rows and columns number must be positive");
    }
    
    this.rows = rows;
    this.columns = columns;
    board = new ArrayList<>();
    
    initBoard();
  }
  
  private void initBoard() {
    for (var i = 0; i < rows; i++) {
      board.add(new ArrayList<Card>());
    }
  }
  
  public boolean add(Card card, Coordinate coordinate) {
    Objects.requireNonNull(card);
    
    var row = coordinate.row();
    var column = coordinate.column();
    
    if (row < 0 || row >= rows) {
      //throw new IllegalArgumentException("Your row's index is out of scope");
      return false;
    }
    if (column < 0 || column >= columns) {
      //throw new IllegalArgumentException("Your column's index is out of scope");
      return false;
    }
    
    if (board.get(row).size() <= column) {
      board.get(row).add(column, card);
    } else {
      return false;
    }
    
    return true;    
  }
  
  public boolean push(Card card) {
    Objects.requireNonNull(card);
    
    for (var i = 0; i < rows; i++) {
      for (var j = 0; j < columns; j++) {
        if (board.get(i).get(j).color() == Color.EMPTY) {
        	board.get(i).add(j, card);
          return true;
        }
      }
    }
    
    return false;
  }
  
  public boolean push(Card card, int row) {
    if (row < 0 || row >= rows) {
      return false;
    }
    Objects.requireNonNull(card);

    board.get(row).add(card);
    
    return true;
  }
  
  public boolean canRemove(Coordinate coordinate) {
  	
  	 var row = coordinate.row();
     var column = coordinate.column();
  	
    if (row < 0 || row >= rows) {
      return false;
    }
    if (column < 0 || column >= columns) {
      return false;
    }
    
    if (column >= board.get(row).size()) {
      return false;
    }
    
    return true;
  }
  
  public Card remove(Coordinate coordinate) {
  	
  	 var row = coordinate.row();
     var column = coordinate.column();
  	
    return board.get(row).remove(column);
  }
  
  public int numberOfCards() {
    var res = 0;
    
    for (var i = 0; i < rows; i++) {
      for (var j = 0; j < columns; j++) {
        res += board.get(i).size();
      }
    }
    
    return res;
  }
  

  public boolean rowIsInBoard(Coordinate coordinate) {    
    return coordinate.row() > 0 || coordinate.row() < rows;
  }
  
  public boolean colIsInBoard(Coordinate coordinate) {    
    return coordinate.column() > 0 || coordinate.column() < columns;
  }
  
  public int rows() {
	  return rows;
  }
  
  public int colums() {
	  return columns;
  }
    
  @Override
  public String toString() {
   	var cardString = new StringBuilder();
   	
   	for (var cards : board) {
    		String tab[] = new String[cards.size()];
    		
    		for (int i = 0; i < cards.size(); i++) {
    			  tab[i] = cards.get(i).toString();
    		}
    		
    		cardString.append(Utils.computeStringsToLine(tab));
   	}
   	
   	return cardString.toString();
  }
}