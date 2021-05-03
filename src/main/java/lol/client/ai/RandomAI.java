package lol.client.ai;

import lol.game.*;
import lol.game.action.*;

public class RandomAI extends AIBase {
  public RandomAI(Arena arena) {
    super(arena);
  }

  public Turn championSelect() {
    Turn turn = new Turn();
    String championName;
    switch(teamID) {
      case Nexus.BLUE: championName = "Warrior"; break;
      case Nexus.RED: championName = "Archer"; break;
      default: throw new RuntimeException("Unknown team color.");
    }
    turn.registerAction(new ChampionSelect(teamID, championName));
    return turn;
  }

  public Turn turn() {
    return null;
  }
}
