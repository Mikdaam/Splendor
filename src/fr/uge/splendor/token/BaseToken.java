package fr.uge.splendor.token;

import fr.uge.splendor.color.*;

public record BaseToken(Color color) implements Token {
  
  public String toString() {
    var sb = new StringBuilder();
    var line = new StringBuilder();
    var colorSize = color.toString().length();
    
    for (var i = 0; i < colorSize + 4; i++) {
      line.append("-");
    }
    
    sb.append(line).append("\n")
      .append("| ").append(color).append(" |\n")
      .append(line);
    
    return sb.toString();
  }
}
