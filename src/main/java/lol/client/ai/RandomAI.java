package lol.client.ai;

import java.util.*;
import lol.game.*;
import lol.game.action.*;

public class RandomAI extends AIBase {
  private Random random;

  public RandomAI(Arena arena, Battlefield battlefield) {
    super(arena, battlefield);
    random = new Random();
  }

  public Turn championSelect() {
    Turn turn = new Turn();
    String championName;
    //TODO Champion diversity
    switch(teamID) {
      case Nexus.BLUE: championName = "Warrior"; break;
      case Nexus.RED: championName = "Scientist"; break;
      default: throw new RuntimeException("Unknown team color.");
    }
    turn.registerAction(new ChampionSelect(teamID, championName));
    turn.registerAction(new ChampionSelect(teamID, "Warrior"));
    turn.registerAction(new ChampionSelect(teamID, "Scientist"));
    return turn;
  }

  public Turn turn() {
    Turn turn = new Turn();
    // Try to attack the Nexus first.
    arena.teamOf(teamID).forEachChampion((champion, id) ->
            battlefield.visitAdjacent(champion.x(), champion.y(), champion.attackRange(), new TileVisitor(){
              public void visitNexus(Nexus nexus) {
                if(nexus.teamOfNexus() != teamID) {
                  turn.registerAction(new Attack(teamID, id, nexus.x(), nexus.y()));
                }
              }
            }));
    // Add a move action in case we could not attack the Nexus.
    arena.teamOf(teamID).forEachChampion((champion, id) ->
            battlefield.visitAdjacent(champion.x(), champion.y(), champion.walkSpeed(), new TileVisitor(){
              public void visitGrass(int x, int y) {
                if(random.nextInt() % 3 == 0) {
                  turn.registerAction(new Move(teamID, id, x, y));
                }
              }
            }));
    return turn;
  }
}
