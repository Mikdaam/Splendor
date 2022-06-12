package fr.uge.splendor.card;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;

import fr.uge.splendor.color.Color;
import fr.uge.splendor.level.Level;

public record NobleCard(int prestigePoint, 
    										HashMap<Color, Integer> price) 
												implements Card {

	@Override
	public Level level() {
		return Level.LEVEL_NOBLE;
	}

	@Override
	public Color color() {
		return Color.NOBLE;
	}
	
	private void drawOnePieceOfPrice(Graphics2D graphic, int x, int y, int w, int h, int nb, Color color) {
		graphic.setColor(Color.getScreenColor(color));
		graphic.fill(new Ellipse2D.Float(x + 2, y + 2, w - 2, h - 2));
		
		graphic.setColor(java.awt.Color.WHITE);
		graphic.drawString(nb + "", x + 5, h);
	}
	
	@Override
	public void render(Graphics2D graphic, int x, int y) {
		int width = 75, height = 100;
		// draw the board
		graphic.setColor(java.awt.Color.WHITE);
		graphic.drawRoundRect(x, y, width, height, 5, 5);
		
		// fill the rect with corresponding color
		graphic.fillRect(x + 1, y + 1, width - 1, height - 1);
		
		// draw the top of the card
		graphic.setColor(java.awt.Color.WHITE);
		graphic.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
		graphic.fillRect(x + 1, y + 1, width - 1, height/4);
		
		graphic.setColor(java.awt.Color.BLACK);
		graphic.setFont(new Font("Courgette", Font.ITALIC, 25));
		graphic.drawString(prestigePoint + "", x + 10, width/4 - 10);
		
		/* TODO: Draw color image on the top */
		
		price.forEach((color, nb) -> {
			drawOnePieceOfPrice(graphic, x, y, height / 4, width / 3, nb, color);
		});
	}
	
	/**
	 * 
	 * @return
	 */
	private String priceToCardString() {
	  var sb = new StringBuilder();
	  
	  price.forEach((color, price) -> {
	    var colorText = color.toString();
	    var maxLength = Integer.min(colorText.length(), 5);
	    if (price != 0) {
	      sb.append("│ ").append(colorText.substring(0, maxLength)).append(": ").append(price);
	      
	      /*To add the missing spaces*/
	      for (var i = 8 - maxLength - 3; i >= 0; i--) {
	        sb.append(" ");
	      }
	        sb.append(" ░│\n");
	    }
   });
	  
	  var nonNullPrices = price.keySet().stream().filter(color -> price.get(color) != 0) .count();
   for (var line = 4 - nonNullPrices; line > 0; line--) {
     sb.append("│           ░│\n");
   }
	  
	  return sb.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	private String colorToCardString() {
	  var sb = new StringBuilder("│ ");
	  var colorText = color().toString();
	  
	  sb.append(colorText);
	  
	  for (var i = 8 - colorText.length(); i > 0; i--) {
	    sb.append(" ");
	  }
	  
	  sb.append("  ░│\n");
	  return sb.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	private String prestigeToCardString() {
	 var sb = new StringBuilder("│         ");
	 
	 if (prestigePoint != 0) {
	   sb.append(prestigePoint);
	 } else {
	   sb.append(" ");
	 }
	 
	 sb.append(" ░│\n");
	 
	 
	 	return sb.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public static String emptyCardToString() {
	  var sb = new StringBuilder();
	  
	  for (var i = 0; i < 8; i++) {
	    sb.append("              \n");
	  }
	    
	  return sb.append("              ").toString();
	}
	
	/*
	 * 
	 */
	@Override
	public String toString() {
	  var sb = new StringBuilder("╭────────────╮\n");
	  
	  sb.append(this.colorToCardString())
	    .append(this.prestigeToCardString())
	    .append("├────────────┤\n")
	    .append(this.priceToCardString());
	  
	  return sb.append("╰────────────╯").toString();
	}

}
