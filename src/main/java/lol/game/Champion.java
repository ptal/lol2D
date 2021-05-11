package lol.game;

public class Champion extends Destructible {
  private String name;
  private int rangeOfAttack;
  private int damages;
  private int speed;
  private int teamID;

  private Champion(int teamID, String name, int hp, int rangeOfAttack, int damages, int speed) {
    super(hp);
    this.teamID = teamID;
    this.name = name;
    this.rangeOfAttack = rangeOfAttack;
    this.damages = damages;
    this.speed = speed;
  }

  public static Champion makeArcher(int teamID) {
    return new Champion(teamID, "Archer", 10, 3, 2, 2);
  }

  public static Champion makeWarrior(int teamID) {
    return new Champion(teamID, "Warrior", 15, 1, 3, 2);
  }

  public static Champion make(int teamID, String name) {
    switch(name) {
      case "Archer": return makeArcher(teamID);
      case "Warrior": return makeWarrior(teamID);
      default: throw new RuntimeException("The champion " + name + " does not exist.");
    }
  }

  public int teamID() {
    return teamID;
  }

  public String name() {
    return name;
  }

  public int walkSpeed() {
    return speed;
  }

  public int attackRange() {
    return rangeOfAttack;
  }

  private int distanceFrom(int toX, int toY) {
    return Math.max(Math.abs(toX - x()), Math.abs(toY - y()));
  }

  public boolean canWalkTo(int toX, int toY) {
    return distanceFrom(toX, toY) <= speed;
  }

  public boolean canAttack(int toX, int toY) {
    return distanceFrom(toX, toY) <= rangeOfAttack;
  }

  public boolean attack(Destructible d) {
    if(canAttack(d.x(), d.y())) {
      d.hit(damages);
      return true;
    }
    return false;
  }

  @Override public String toString() {
    return name();
  }

  @Override public void accept(TileVisitor visitor) {
    visitor.visitChampion(this);
  }
}