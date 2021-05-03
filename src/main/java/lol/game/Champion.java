package lol.game;

public class Champion extends Destructible {
  private String name;
  private int attackRange;
  private int damages;
  private int speed;

  private Champion(String name, int hp, int attackRange, int damages, int speed) {
    super(hp);
    this.name = name;
    this.attackRange = attackRange;
    this.damages = damages;
    this.speed = speed;
  }

  public static Champion makeArcher() {
    return new Champion("Archer", 10, 2, 2, 2);
  }

  public static Champion makeWarrior() {
    return new Champion("Warrior", 15, 1, 3, 2);
  }

  public static Champion make(String name) {
    switch(name) {
      case "Archer": return makeArcher();
      case "Warrior": return makeWarrior();
      default: throw new RuntimeException("The champion " + name + " does not exist.");
    }
  }

  public String name() {
    return name;
  }

  public int walkSpeed() {
    return speed;
  }

  public boolean canWalkTo(int toX, int toY) {
    int distance = Math.max(Math.abs(toX - x()), Math.abs(toY - y()));
    return distance <= speed;
  }

  @Override public String toString() {
    return name();
  }

  @Override public void accept(TileVisitor visitor) {
    visitor.visitChampion(this);
  }
}