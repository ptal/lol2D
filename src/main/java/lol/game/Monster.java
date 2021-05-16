package lol.game;

public class Monster extends Attacker {
  private String name;
  private int monsterID;
  public static final int DRAGON = 0;
  public static final int NASHOR = 1;


  private Monster(int monsterID, String name, int hp, int rangeOfAttack, int damages) {
    super(hp, rangeOfAttack, damages);
    this.name = name;
    this.monsterID = monsterID;
  }

  public static Monster makeDragon() {
    return new Monster(DRAGON, "Dragon", 10, 5, 5);
  }

  public static Monster makeNashor() {
    return new Monster(NASHOR, "Nashor", 10, 5, 5);
  }

  public static Monster make(int monsterID) {
    switch(monsterID) {
      case DRAGON: return makeDragon();
      case NASHOR: return makeNashor();
      default: throw new RuntimeException("The Monster Id " + monsterID + " does not exist.");
    }
  }

  public String name() {
    return name;
  }

  public int monsterID() {
    return monsterID;
  }

  @Override public void hit(int damage) {
    super.hit(damage);
    System.out.println(this);
  }

  @Override public String toString() {
    return name();
  }

  @Override public void accept(TileVisitor visitor) {
    visitor.visitMonster(this);
  }
}