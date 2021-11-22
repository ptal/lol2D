package lol.client.ai;

import java.util.*;
import lol.game.*;
import lol.game.action.*;

public class RandomAI extends AIBase {
  protected Random random;
  protected BattlefieldTraversal traversal;

  public RandomAI(Arena arena, Battlefield battlefield) {
    super(arena, battlefield);
    traversal = new BattlefieldTraversal(battlefield);
    random = new Random();
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
    turn.registerAction(new ChampionSelect(teamID, "Warrior"));
    turn.registerAction(new ChampionSelect(teamID, "Archer"));
    return turn;
  }

  public Turn turn() {
    Turn turn = new Turn();
    // Try to attack the Nexus first.
    tryAttackNexus(turn);
    //
    tryAttackMonster(turn);
    tryAttackTower(turn);
    // Add a move action in case we could not attack the Nexus.
    tryMove(turn);
    return turn;
  }

  protected void tryAttackNexus(Turn turn) {

  }

  protected void tryMove(Turn turn) {

  }

  protected void tryAttackMonster(Turn turn) {

  }

  private void tryAttackTower(Turn turn) {

  }
}
