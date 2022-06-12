package fr.uge.splendor.board;

import java.util.ArrayList;
import java.util.Objects;

import fr.uge.splendor.card.Card;
import fr.uge.splendor.card.Coordinate;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.utils.Utils;

/**
 * This class represent a card board, with several rows and columns.
 * 
 * @author Mikdaam BADAROU
 * @author Yunen SNACEL
 *
 */
public class Board {
	 private final ArrayList<ArrayList<Card>> board;
  private final int rows;
  private final int columns;
  
  /**
   * Creates a board with valid values for its numbers of rows and columns.
   * @param rows - the board's number of rows
   * @param columns - the board's number of columns
   */
  public Board(int rows, int columns) {
    if (rows < 0 || columns < 0) {
      throw new IllegalArgumentException("Your rows and columns number must be positive");
    }
    
    this.rows = rows;
    this.columns = columns;
    board = new ArrayList<>();
    
    /* To initialize each row */
    for (var i = 0; i < rows; i++) {
      board.add(new ArrayList<Card>());
    }
  }  
  
  
  /**
   * Returns the board's number of rows.
   * @return The board's number of rows.
   */
  public int rows() {
    return rows;
   }
   
  /**
   * Returns the board's number of colums.
   * @return The board's number of colums.
   */
  public int colums() {
    return columns;
  }

  /**
   * Checks if the row in the given coordinate is in the board.
   * @param coordinate - the coordinate to check
   * @return true if the row is in the board, false otherwise
   */
  public boolean rowIsInBoard(Coordinate coordinate) {    
    Objects.requireNonNull(coordinate);
    return coordinate.row() > 0 || coordinate.row() < rows;
  }
  
  /**
   * Checks if the column in the given coordinate is in the board.
   * @param coordinate - the coordinate to check
   * @return true if the column is in the board, false otherwise
   */
  public boolean colIsInBoard(Coordinate coordinate) {    
    Objects.requireNonNull(coordinate);
    return coordinate.column() > 0 || coordinate.column() < columns;
  }
  
  /**
   * Checks if the given coordinates are pointing on a card and so, if it can remove the card from there.
   * @param coordinate - the coordinates to check
   * @return true if the card can be removed, false otherwise
   */
  public boolean canRemove(Coordinate coordinate) {
    Objects.requireNonNull(coordinate);
    var row = coordinate.row();
    var column = coordinate.column();
   
    if (row < 0 || row >= rows || column < 0 || column >= columns) {
      return false;
    }
    
    if (column >= board.get(row).size()) {
      return false;
    }
    
    return true;
  }
  
  
  
  /**
   * Pushes a card on a given row on the board
   * @param card - the card to add on the board
   * @param row - the row where to add the card
   * @return true if the card has been pushed successfully, false otherwise
   */
  public boolean push(Card card, int row) {
    Objects.requireNonNull(card);
    
    if (card.color().equals(Color.EMPTY) || row < 0 || row >= rows || board.get(row).size() >= columns) {
      return false;
    }
    
    board.get(row).add(card);
    return true;
  }
  
  /**
   * Removes a card from the board, with given coordinates.
   * @param coordinate - the coordinates where the card is located
   * @return the removed card.
   */
  public Card remove(Coordinate coordinate) {
    Objects.requireNonNull(coordinate);
    return board.get(coordinate.row()).remove(coordinate.column());
  }
  
  /**
   * Returns the total amount of cards on the board. 
   * @return The total amount of cards on the board. 
   */
  public int amountOfCards() {
    var amount = 0;
    
    for (var i = 0; i < rows; i++) {
      amount += board.get(i).size();
    }
    
    return amount;
  }
  
  
  
  /**
   * Returns a String describing a Board with all of its cards.
   * @return A String describing a Board with all of its cards.
   */
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