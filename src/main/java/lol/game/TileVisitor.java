package lol.game;

public interface TileVisitor {
  default void visitGrass(int x, int y) {}
  default void visitRock(int x, int y) {}
  default void visitPath(int x, int y) {}
  default void visitWater(int x, int y) {}

  default void visitGround(Battlefield.GroundTile groundTile, int x, int y) {
    switch(groundTile) {
      case GRASS: visitGrass(x, y); break;
      case ROCK: visitRock(x, y); break;
      case PATH: visitPath(x, y); break;
      case WATER: visitWater(x, y); break;
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
}
