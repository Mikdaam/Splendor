package fr.uge.splendor.displayer;

import fr.uge.splendor.game.*;

public sealed interface Displayer permits ConsoleDisplayer {
  public void display(Game game);
}
