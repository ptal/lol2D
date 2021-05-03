package lol.game;

public class Nexus extends Destructible {
  public enum Color {
    BLUE,
    RED;
  }

  private Color colorTeam;

  public Nexus(Color colorTeam) {
    this.colorTeam = colorTeam;
  }

  public Color color() {
    return colorTeam;
  }

  @Override public void accept(TileVisitor visitor, int x, int y) {
    visitor.visitNexus(this, x, y);
  }
}
