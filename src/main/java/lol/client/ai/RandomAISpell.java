package lol.client.ai;

import java.util.*;
import lol.game.*;
import lol.game.action.*;

public class RandomAISpell extends RandomAI {

  public RandomAISpell(Arena arena, Battlefield battlefield) {
    super(arena, battlefield);
  }

  public Turn turn() {
    Turn turn = new Turn();
    // Try to attack the Nexus first.
    tryAttackNexus(turn);
    // Try to attack a Tower.
    tryDoubleAttack(turn);
    // Add a move action in case we could not attack the Nexus.
    tryMove(turn);
    return turn;
  }

  protected void tryDoubleDamageOnNexus(Turn turn) {
    arena.teamOf(teamID).forEachChampion((champion, id) ->
      battlefield.visitAdjacent(champion.x(), champion.y(), champion.attackRange(), new TileVisitor(){
        public void visitNexus(Nexus nexus) {
        if(nexus.teamOfNexus() != teamID) {
          turn.registerAction(new DoubleDamage(teamID, id, tower.x(), tower.y()));
        }
        }
      }));
  }

  private void tryDoubleDamageOnTower(Turn turn) {
    arena.teamOf(teamID).forEachChampion((champion, id) ->
      battlefield.visitAdjacent(champion.x(), champion.y(), champion.attackRange(), new TileVisitor(){
        public void visitTower(Tower tower) {
        if(tower.teamOfTower() != teamID) {
          turn.registerAction(new DoubleDamage(teamID, id, tower.x(), tower.y()));
        }
        }
      }));
  }

}
