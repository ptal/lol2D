package lol.game;

public class Champion extends Attacker {
  private String name;
  private int speed;
  private int teamID;

  private Champion(int teamID, String name, int hp, int rangeOfAttack, int damages, int speed) {
    super(hp, rangeOfAttack, damages);
    this.teamID = teamID;
    this.name = name;
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

  public void boostSpeed(int boost) {
    this.speed += boost;
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

  public boolean canWalkTo(int toX, int toY) {
    return distanceFrom(toX, toY) <= speed;
  }

  @Override public String toString() {
    return name() + "  (speed: " + this.speed + " - damages: " + this.damages() + ")";
  }

  @Override public void boostTeam(Team team) {
  }

  @Override public void accept(TileVisitor visitor) {
    visitor.visitChampion(this);
  }
}