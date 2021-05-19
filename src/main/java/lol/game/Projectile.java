package lol.game;

public class Projectile extends Indestructible {
  public static final int ARROW = 0;
  private int typeID;

  public Projectile(int typeID) {
    super();
    this.typeID = typeID;
  }

  public int typeOfProjectile() {
    return typeID;
  }

  @Override public void accept(TileVisitor visitor) {
    visitor.visitProjectile(this);
  }
}