package fr.uge.splendor.board;

import java.util.Objects;

import fr.uge.splendor.card.Card;
import fr.uge.splendor.utils.Utils;

public class Board {
  private final Card[][] board;
  private final int rows;
  private final int columns;
  
  public Board(int rows, int columns) {
    if (rows <= 0 || columns <= 0) {
      throw new IllegalArgumentException("Your rows and columns number must be strictly positive");
    }
    
    this.rows = rows;
    this.columns = columns;
    this.board = new Card[rows][columns];
  }
  
  public void add(Card card, int row, int column) {
    Objects.requireNonNull(card);
    
    if (board[row][column] == null) {
      board[row][column] = card;
    } else {
      throw new IllegalArgumentException("There is already a Card at (" + row + ", " + column +")");
    }
    
  }
  
  public Card remove(int row, int column) {
    if (row < 0 || row >= board.length) {
      throw new IllegalArgumentException("Your row's index is out of scope");
    }
    if (column < 0 || column >= board[0].length) {
      throw new IllegalArgumentException("Your column's index is out of scope");
    }
    
    Objects.requireNonNull(board[row][column]);
    
    var card = board[row][column];
    board[row][column] = null; /*To flag an empty cell*/
    
    return card;
  }
  
  public int rows() {
	  return rows;
  }
  
  public int colums() {
	  return columns;
  }
  
  /*TODO: Refactor this method*/
  
  @Override
  public String toString() {
    /*return Arrays.stream(board)
    		.flatMap(rowCard -> rowCard.)*/
	var cardString = new StringBuilder();
	for (Card[] cards : board) {
		String tab[] = new String[cards.length];
		for (int i = 0; i < cards.length; i++) {
			tab[i] = (cards[i] == null) ? Card.emptyCardToString() : cards[i].toString();
		}
		cardString.append(Utils.computeStringsToLine(tab));
	}
	
	return cardString.toString();
  }


}