package lol.game;

public class Champion extends Attacker {
  private String name;
  private int speed;

  private Champion(String name, int hp, int rangeOfAttack, int damages, int speed) {
    super(hp, rangeOfAttack, damages);
    this.name = name;
    this.speed = speed;
  }

  public static Champion makeArcher() {
    return new Champion("Archer", 10, 3, 2, 2);
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
    return distanceFrom(toX, toY) <= speed;
  }

  @Override public String toString() {
    return name();
  }

  @Override public void accept(TileVisitor visitor) {
    visitor.visitChampion(this);
  }
}