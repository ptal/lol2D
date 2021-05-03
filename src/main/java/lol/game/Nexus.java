package lol.game;

import java.io.Serializable;

public class Nexus extends Destructible implements Serializable {
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
