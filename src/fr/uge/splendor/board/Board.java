package fr.uge.splendor.board;

import java.util.Objects;

import fr.uge.splendor.card.Card;

public class Board {
  private final Card[][] board;
  
  public Board(int rows, int columns) {
    if (rows <= 0 || columns <= 0) {
      throw new IllegalArgumentException("Your rows and columns number must be strictly positive");
    }
    
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
  
  
  /*Méthode pour afficher une ligne de cartes*/
  
  @Override
  public String toString() {
    return "Board";
  }


}