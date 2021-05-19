package lol.game;

// The coordinates are the position of the destructible in the battlefield.
public abstract class Destructible {
  private int xCoord;
  private int yCoord;
  private int initialHP;
  private int currentHP;

  public Destructible(int hp) {
    this.initialHP = hp;
    this.currentHP = hp;
  }

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

  public void hit(int hp) {
    this.currentHP = Math.max(0, this.currentHP - hp);
  }

  public boolean isAlive() {
    return currentHP > 0;
  }

  public boolean isDead() {
    return currentHP <= 0;
  }

  public void reviveAt(int x, int y) {
    currentHP = initialHP;
    place(x, y);
  }

  public int currentHP(){
    return currentHP;
  }

  public int initialHP(){
    return initialHP;
  }

  public abstract void boostTeam(Team team);

  public abstract void accept(TileVisitor visitor);
}
