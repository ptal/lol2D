package lol.game;

import lol.config.Config;

public class Tower extends Attacker{
    private int teamID;

    // we let the attributes be constants for now
    public Tower(int teamID) {
        super(Config.HP_TOWER, Config.RANGE_OF_ATTACK_TOWER, Config.DAMAGE_TOWER);
        this.teamID = teamID;
    }

    public int teamOfTower() {
        return teamID;
    }

    public String name() {
        return name();
    }

    @Override public void accept(TileVisitor visitor) {
            visitor.visitTower(this);
    }

    @Override public void boostTeam(Team team) {
  }

    @Override public void hit(int damage) {
        super.hit(damage);
        System.out.println(this);
    }

    @Override public String toString() {
        return (teamID == Nexus.BLUE ? "Blue" : "Red") + " Tower: " + currentHP() + "hp left";
    }
}
