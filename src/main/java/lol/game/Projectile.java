package lol.game;

public class Projectile extends Destructible {
  public static final int HP_PROJECTILE = 9999;
  public static final int ARROW = 0;
  private int typeID;

  public Projectile(int typeID) {
    super(HP_PROJECTILE);
    this.typeID = typeID;
  }

  public int typeOfProjectile() {
    return typeID;
  }

  @Override public void accept(TileVisitor visitor) {
    visitor.visitProjectile(this);
  }

  @Override public void hit(int hp) {}
  @Override public void reviveAt(int x, int y) {}

  @Override public boolean isAlive() {
    return true;
  }

  @Override public boolean isDead() {
    return false;
  }
}