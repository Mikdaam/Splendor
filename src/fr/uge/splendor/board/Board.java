package fr.uge.splendor.board;

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Objects;

import fr.uge.splendor.card.Card;
import fr.uge.splendor.card.EmptyCard;
import fr.uge.splendor.color.Color;
import fr.uge.splendor.utils.Utils;

public class Board {
  //private final Card[][] board;
	private final ArrayList<ArrayList<Card>> board;
  private final int rows;
  private final int columns;
  
  public Board(int rows, int columns) {
    if (rows <= 0 || columns <= 0) {
      throw new IllegalArgumentException("Your rows and columns number must be strictly positive");
    }
    
    this.rows = rows;
    this.columns = columns;
    //board = new Card[rows][columns];
    board = new ArrayList<>();
    
    initBoard();
  }
  
  private void initBoard() {
    for (var i = 0; i < rows; i++) {
      board.add(new ArrayList<Card>());
      for (var j = 0; j < columns; j++) {
        //board[i][j] = new EmptyCard();
        board.get(j).add(new EmptyCard());
      }
    }
  }
  
  public boolean add(Card card, int row, int column) {
    Objects.requireNonNull(card);
    
    if (row < 0 || row >= rows) {
      throw new IllegalArgumentException("Your row's index is out of scope");
    }
    if (column < 0 || column >= columns) {
      throw new IllegalArgumentException("Your column's index is out of scope");
    }
    
    //if (board[row][column].color() == Color.EMPTY) {
    if (board.get(row).get(column).color() == Color.EMPTY) {
      //board[row][column] = card;
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
      //if (board[i][j].color() == Color.EMPTY) {
        if (board.get(i).get(j).color() == Color.EMPTY) {
          //board[i][j] = card;
        	board.get(i).add(j, card);
          return true;
        }
      }
    }
    
    return false;
  }
  
  public Card remove(int row, int column) {
    if (row < 0 || row >= rows) {
      throw new IllegalArgumentException("Your row's index is out of scope");
    }
    if (column < 0 || column >= columns) {
      throw new IllegalArgumentException("Your column's index is out of scope");
    }
    
    //Objects.requireNonNull(board[row][column]);
    Objects.requireNonNull(board.get(row).get(column));
    
    //var card = board[row][column];
    var card = board.get(row).get(column);
    //board[row][column] = new EmptyCard();
    board.get(row).add(column, new EmptyCard());
    
    return card;
  }
  
  public int numberOfCards() {
    var res = 0;
    
    for (var i = 0; i < rows; i++) {
      for (var j = 0; j < columns; j++) {
        //if (board[i][j].color() != Color.EMPTY) {
        if (board.get(i).get(j).color() == Color.EMPTY) {
          res++;
        }
      }
    }
    
    return res;
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
   	
   	//for (Card[] cards : board) {
   	for (var cards : board) {
    		//String tab[] = new String[cards.length];
    		String tab[] = new String[cards.size()];
    		//for (int i = 0; i < cards.length; i++) {
    		for (int i = 0; i < cards.size(); i++) {
    			  //tab[i] = cards[i].toString();
    			  tab[i] = cards.get(i).toString();
    		}
    		cardString.append(Utils.computeStringsToLine(tab));
   	}
   	
   	return cardString.toString();
  }
}