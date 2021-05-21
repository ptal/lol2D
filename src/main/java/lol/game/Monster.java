package lol.game;

import lol.config.Config;

public class Monster extends Attacker {
  public static final int DRAGON = 0;
  public static final int NASHOR = 1;
  private String name;
  private int monsterID;

  private Monster(int monsterID, String name, int hp, int rangeOfAttack, int damages) {
    super(hp, rangeOfAttack, damages);
    this.name = name;
    this.monsterID = monsterID;
  }

  public static Monster makeDragon() {
    return new Monster(DRAGON, "Dragon", Config.HP_DRAGON, Config.RANGE_OF_ATTACK_DRAGON, Config.DAMAGE_DRAGON);
  }

  public static Monster makeNashor() {
    return new Monster(NASHOR, "Nashor", Config.HP_NASHOR, Config.RANGE_OF_ATTACK_NASHOR, Config.DAMAGE_NASHOR);
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

  @Override public void boostTeam(Team team) {
    if (isDead()) {
      if (monsterID == DRAGON) {
        team.forEachChampion((champion, id) -> champion.boostDamages(Config.BOOST_DAMAGES));
        System.out.println("Boost Damage team " + team.toString());
      }
      else if (monsterID == NASHOR) {
        team.forEachChampion((champion, id) -> champion.boostSpeed(Config.BOOST_SPEED));
        System.out.println("Boost Speed team " + team.toString());
      }
    }
  }

  @Override public void hit(int damage) {
    super.hit(damage);
    System.out.println(this);
  }

  @Override public String toString() {
    return name() + ": " + currentHP() + "hp left";
  }

  @Override public void accept(TileVisitor visitor) {
    visitor.visitMonster(this);
  }
}