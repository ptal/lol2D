package lol.client.ai;

import java.util.*;
import lol.game.*;
import lol.game.action.*;

public class RandomAITower extends RandomAI {

  public RandomAITower(Arena arena, Battlefield battlefield) {
    super(arena, battlefield);
  }

  public Turn turn() {
    Turn turn = new Turn();
    // Try to attack the Nexus first.
    tryAttackNexus(turn);
    // Try to attack a Tower.
    tryAttackTower(turn);
    // Add a move action in case we could not attack the Nexus.
    tryMove(turn);
    return turn;
  }

  private void tryAttackTower(Turn turn) {
    arena.teamOf(teamID).forEachChampion((champion, id) ->
      traversal.visitAdjacent(champion.x(), champion.y(), champion.attackRange(), new TileVisitor(){
        public void visitTower(Tower tower) {
          if(tower.teamOfTower() != teamID) {
            turn.registerAction(new Attack(teamID, id, tower.x(), tower.y()));
          }
        }
      }));
  }
}
