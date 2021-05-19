package lol.game;

public abstract class Indestructible {
  private int xCoord;
  private int yCoord;

  public Indestructible() {}

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

  public abstract void accept(TileVisitor visitor);
}