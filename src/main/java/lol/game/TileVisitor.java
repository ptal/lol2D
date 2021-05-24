package lol.game;

public interface TileVisitor {
  default void visitGrass(int x, int y) {}
  default void visitRock(int x, int y) {}
  default void visitTree(int x, int y) {}

  default void visitGround(Battlefield.GroundTile groundTile, int x, int y) {
    switch(groundTile) {
      case GRASS: visitGrass(x, y); break;
      case ROCK: visitRock(x, y); break;
      case TREE: visitTree(x,y); break;
      default: throw new RuntimeException("Missing GroundTile case in visitGround.");
    }
  }

  default void visitDestructible(Destructible d) {}
  default void visitChampion(Champion c) {
    visitDestructible(c);
  }
  default void visitNexus(Nexus n) {
    visitDestructible(n);
  }
  default void visitTower(Tower t){
    visitDestructible(t);
  }

  default void visitMonster(Monster monster) {
    visitDestructible(monster);
  }
}
