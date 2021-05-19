package lol.client.ai;

import java.util.*;
import lol.game.*;
import lol.game.action.*;

public class RandomAISpell extends RandomAITower {

  public RandomAISpell(Arena arena, Battlefield battlefield) {
    super(arena, battlefield);
  }

  public Turn turn() {
    Turn turn = new Turn();
    //try to attack with a spell first
    tryDoubleDamageOnNexus(turn);
    // Try to attack the Nexus
    tryAttackNexus(turn);

    tryDoubleDamageOnTower(turn);
    // Try to attack a Tower.
    // Add a move action in case we could not attack the Nexus.
    tryAttackTower(turn);
    tryMove(turn);
    return turn;
  }

  protected void tryDoubleDamageOnNexus(Turn turn) {
    arena.teamOf(teamID).forEachChampion((champion, id) ->
      battlefield.visitAdjacent(champion.x(), champion.y(), champion.attackRange(), new TileVisitor(){
        public void visitNexus(Nexus nexus) {
        if(nexus.teamOfNexus() != teamID) {
          turn.registerAction(new DoubleDamage(teamID, id, nexus.x(), nexus.y()));
        }
        }
      }));
  }

  protected void tryDoubleDamageOnTower(Turn turn) {
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
