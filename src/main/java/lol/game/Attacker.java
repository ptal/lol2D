package lol.game;


// class is abstract, since it should never be used alone
public abstract class Attacker extends Destructible {
  private int rangeOfAttack;
  private int damages;

  public Attacker(int hp, int rangeOfAttack, int damages) {
    super(hp);
    this.rangeOfAttack = rangeOfAttack;
    this.damages = damages;
  }

  public int attackRange() {
    return rangeOfAttack;
  }

  public boolean attack(Destructible d, Battlefield battlefield, BattlefieldTraversal traversal) {
    if(canAttack(d.x(), d.y(), battlefield, traversal)) {
        d.hit(damages);
        return true;
    }
    return false;
  }

  protected int distanceFrom(int toX, int toY) {
    return Math.max(Math.abs(toX - x()), Math.abs(toY - y()));
  }

  public boolean canAttack(int toX, int toY, Battlefield battlefield, BattlefieldTraversal traversal) {
    return distanceFrom(toX, toY) <= rangeOfAttack && traversal.noObstacles(x(), y(),toX,toY, battlefield);
  }
}