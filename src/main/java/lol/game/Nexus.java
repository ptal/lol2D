package lol.game;

public class Nexus extends Destructible {
  @Override public void accept(TileVisitor visitor, int x, int y) {
    visitor.visitNexus(this, x, y);
  }
}