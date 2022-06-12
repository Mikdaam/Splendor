package fr.uge.splendor.utils;

import java.util.List;
import java.util.Objects;

import fr.uge.splendor.color.Color;

/**
 * This class contains some utilitary functions used in different parts of the program.
 * 
 * @author Mikdaam Badarou
 * @author Yunen Snacel
 *
 */
public class Utils {
  
  /**
   * This method returns the list of the cards colors authorized in the SimpleGame.
   * 
   * @return the list of the cards colors authorized in the SimpleGame.
   */
  public static List<Color> cardsColorsList() {
    return Color.getCardsColorsList().stream() 
                                     .filter(color -> color != Color.NOBLE)
                                     .toList();
  }
  
  /**
   * This static method computes an array's strings into a single line.
   * The strings must be of same height or smaller than the first string in the array to have a correct result.
   * 
   * @param strings - The array containing the strings we want to compute into a single line.
   * @return The strings given in parameter aligned on one single line.
   */
  public static String computeStringsToLine (String[] strings) {
    Objects.requireNonNull(strings);
    
    var sb = new StringBuilder();
    var splittedStrings = new String[strings.length][];
    
    if (strings.length == 0) {
      return "";
    }
    
    for (var i = 0; i < strings.length; i++) {
      splittedStrings[i] = strings[i].split("\n");
    }
    
    for (var i = 0; i < splittedStrings[0].length; i++) {
      for (var j = 0; j < splittedStrings.length; j++) {
        sb.append(splittedStrings[j][i]).append("  ");
      }
      sb.append("\n");
    }
    
    return sb.toString();
  }
}
