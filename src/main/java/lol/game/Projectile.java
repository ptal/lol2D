package lol.game;

// The coordinates are the position of the projectile in the battlefield.
public abstract class Projectile {
  private int xCoord;
  private int yCoord;

  public Projectile() {}

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
