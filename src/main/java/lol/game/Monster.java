package lol.game;

public class Monster extends Attacker {
  private String name;
  private int monsterID;
  public static final int DRAGON = 0;

  private Monster(int monsterID, String name, int hp, int rangeOfAttack, int damages) {
    super(hp, rangeOfAttack, damages);
    this.name = name;
    this.monsterID = monsterID;
  }

  public static Monster makeDragon() {
    return new Monster(DRAGON, "Dragon", 30, 5, 5);
  }

  public static Monster make(String name) {
    switch(name) {
      case "Dragon": return makeDragon();
      default: throw new RuntimeException("The Monster " + name + " does not exist.");
    }
  }

  public String name() {
    return name;
  }

  @Override public String toString() {
    return name();
  }

  @Override public void accept(TileVisitor visitor) {
    visitor.visitMonster(this);
  }
}