package lol.game;

import lol.config.Config;

public class Nexus extends Destructible {
  public static final int BLUE = 0;
  public static final int RED = 1;
  private int teamID;

  public Nexus(int teamID) {
    super(Config.HP_NEXUS);
    this.teamID = teamID;
  }

  public int teamOfNexus() {
    return teamID;
  }

  @Override public void boostTeam(Team team) {
  }

  @Override public void accept(TileVisitor visitor) {
    visitor.visitNexus(this);
  }

  @Override public void hit(int damage) {
    super.hit(damage);
    System.out.println(this);
  }

  @Override public String toString() {
    return (teamID == BLUE ? "Blue" : "Red") + " nexus: " + currentHP() + "hp left";
  }
}
