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
    arena.teamOf(teamID).forEachChampion((champion, id) ->
      traversal.visitAdjacent(champion.x(), champion.y(), champion.attackRange(), new TileVisitor(){
        public void visitNexus(Nexus nexus) {
          if(nexus.teamOfNexus() != teamID) {
            turn.registerAction(new Attack(teamID, id, nexus.x(), nexus.y()));
          }
        }
      }));
  }

  protected void tryMove(Turn turn) {
    arena.teamOf(teamID).forEachChampion((champion, id) ->
      traversal.visitAdjacent(champion.x(), champion.y(), champion.walkSpeed(), new TileVisitor(){
        public void visitGrass(int x, int y) {
          if(random.nextInt() % 3 == 0) {
            turn.registerAction(new Move(teamID, id, x, y));
          }
        }
      }));
  }

  protected void tryAttackMonster(Turn turn) {
    arena.teamOf(teamID).forEachChampion((champion, id) ->
      traversal.visitAdjacent(champion.x(), champion.y(), champion.attackRange(), new TileVisitor(){
        public void visitMonster(Monster monster) {
          turn.registerAction(new Attack(teamID, id, monster.x(), monster.y()));
        }
      }));
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
