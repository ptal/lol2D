package lol.game;

import lol.config.Config;

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
    return new Champion(teamID, "Archer", Config.HP_ARCHER, Config.RANGE_OF_ATTACK_ARCHER, Config.DAMAGE_ARCHER, Config.SPEED_ARCHER);
  }

  public static Champion makeWarrior(int teamID) {
    return new Champion(teamID, "Warrior", Config.HP_WARRIOR, Config.RANGE_OF_ATTACK_WARRIOR, Config.DAMAGE_WARRIOR, Config.SPEED_WARRIOR);
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