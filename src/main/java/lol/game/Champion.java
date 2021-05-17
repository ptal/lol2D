package lol.game;

public class Champion extends Destructible {
  private String name;
  private int rangeOfAttack;
  private int damages;
  private int speed;

  private Champion(String name, int hp, int rangeOfAttack, int damages, int speed) {
    super(hp);
    this.name = name;
    this.rangeOfAttack = rangeOfAttack;
    this.damages = damages;
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

  public int attackRange() {
    return rangeOfAttack;
  }

  private int distanceFrom(int toX, int toY) {
    return Math.max(Math.abs(toX - x()), Math.abs(toY - y()));
  }

  public boolean canWalkTo(int toX, int toY) {
    return distanceFrom(toX, toY) <= speed;
  }

  //for speed spells
  public boolean canWalkToEnhanced(int toX, int toY, int factor) {
    return distanceFrom(toX, toY) <= speed*factor;
  }

  //for healing spells
  public void heal(int hp) {
    super.currentHP = Math.min(super.currentHP + hp, super.initialHP);
  }

  public boolean canAttack(int toX, int toY) {
    return distanceFrom(toX, toY) <= rangeOfAttack;
  }

  //for changing the range of attack in spells
  public boolean canAttackEnhanced(int toX, int toY, int factor) {
    return distanceFrom(toX, toY) <= rangeOfAttack*factor;
  }

  public boolean attack(Destructible d) {
    if(canAttack(d.x(), d.y())) {
      d.hit(damages);
      return true;
    }
    return false;
  }

  //for damage spells
  public boolean attackEnhanced(Destructible d, int factor) {
    if(canAttack(d.x(), d.y())) {
      d.hit(damages*factor);
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