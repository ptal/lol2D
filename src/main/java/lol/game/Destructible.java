package lol.game;

import java.io.Serializable;

// The coordinates are the position of the destructible in the battlefield.
public abstract class Destructible implements Serializable {
  protected int xCoord;
  protected int yCoord;

  public void place(int xCoord, int yCoord) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
  }

  public int x() {
    return xCoord;
  }

  public int y() {
    return yCoord;
  }

  public abstract void accept(TileVisitor visitor, int x, int y);
}
