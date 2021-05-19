package lol.game;

public class BattlefieldTraversal {
  private Battlefield battlefield;
  public BattlefieldTraversal(Battlefield battlefield) {
    this.battlefield = battlefield;
  }

  // Visit the battlefield map from top left to bottom right in order.
  // If a destructible is present on the map, we visit it, otherwise we visit the ground tile.
  public void visitFullMap(TileVisitor visitor) {
    for(int y = 0; y < battlefield.height(); ++y) {
      for(int x = 0; x < battlefield.width(); ++x) {
        battlefield.visit(x, y, visitor);
      }
    }
  }

  // Clockwise shifted coordinates, for each corner of a square with center of (0, 0).
  static int[] xDirection = {-1, 1, 1, -1};
  static int[] yDirection = {-1, -1, 1, 1};

  // Visit the tiles adjacent to (x, y).
  // It visits starting from the top left tile, and then proceeds clockwise.
  // We expand the radius by one after each round, until we reach the given max `radius`.
  public void visitAdjacent(int x, int y, int radius, TileVisitor visitor) {
    for(int r = 1; r <= radius; ++r) {
      int[] xCorner = new int[4];
      int[] yCorner = new int[4];
      for(int i = 0; i < 4; ++i) {
        xCorner[i] = x + (xDirection[i] * r);
        yCorner[i] = y + (yDirection[i] * r);
      }
      for(int i = 0; i < 4; ++i) {
        int xi = xCorner[i];
        int yi = yCorner[i];
        // While we do not reach the next corner.
        while(!(xi == xCorner[(i + 1) % 4] && yi == yCorner[(i + 1) % 4])) {
          battlefield.visit(xi, yi, visitor);
          int xShift = xDirection[(i + 1) % 4] - xDirection[i % 4];
          int yShift = yDirection[(i + 1) % 4] - yDirection[i % 4];
          xi += (xCorner[(i + 1) % 4] == xCorner[i % 4]) ? 0 : (xShift > 0) ? 1 : -1;
          yi += (yCorner[(i + 1) % 4] == yCorner[i % 4]) ? 0 : (yShift > 0) ? 1 : -1;
        }
      }
    }
  }
}