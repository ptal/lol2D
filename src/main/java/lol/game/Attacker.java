package lol.game;


// class is abstract, since it should never be used alone
public abstract class Attacker extends Destructible {
  private int rangeOfAttack;
  private int damages;
  private Battlefield battlefield;

  public Attacker(int hp, int rangeOfAttack, int damages) {
    super(hp);
    this.rangeOfAttack = rangeOfAttack;
    this.damages = damages;
  }

  public int attackRange() {
    return rangeOfAttack;
  }

  public boolean attack(Destructible d) {
    if(canAttack(d.x(), d.y())) {
        d.hit(damages);
        return true;
    }
    return false;
  }

  protected int distanceFrom(int toX, int toY) {
    return Math.max(Math.abs(toX - x()), Math.abs(toY - y()));
  }


  public boolean canAttack(int toX, int toY) {
    return distanceFrom(toX, toY) <= rangeOfAttack && noObstacles(toX,toY);
  }

  public boolean noObstacles(int toX, int toY) {
    int dx = Math.abs(toX - x());
    int dy = Math.abs(toY - y());
    if(dy == dx){
      if(!battlefield.diagonalCheck(x(), y(), toX, toY)) {
        return false;
      }
    }
    return true;
  }
}
