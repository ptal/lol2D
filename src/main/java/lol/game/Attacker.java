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

  public boolean attack(Destructible d, Battlefield battlefield) {
    if(canAttack(d.x(), d.y(), battlefield)) {
        d.hit(damages);
        return true;
    }
    return false;
  }

  protected int distanceFrom(int toX, int toY) {
    return Math.max(Math.abs(toX - x()), Math.abs(toY - y()));
  }


  public boolean canAttack(int toX, int toY, Battlefield battlefield) {
    return distanceFrom(toX, toY) <= rangeOfAttack && noObstacles(toX,toY, battlefield);
  }

  public boolean noObstacles(int toX, int toY, Battlefield battlefield) {
    int dx = Math.abs(toX - x());
    int dy = Math.abs(toY - y());
    if(dy <= 1 || dx <= 1){
      return true;
    }
    else if(dy == dx){
      if(!diagonalCheck(x(), y(), toX, toY, battlefield)) {
        System.out.println("Obstacle on the way to the target of pos "+ toX +" "+ toY +" ! Can't shoot.");
        return false;
      }
    }
    else if(y() == toY){
      if(!verticalCheck(x(), y(), toX, toY, battlefield)) {
        System.out.println("Obstacle on the way to the target of pos "+ toX +" "+ toY +" ! Can't shoot.");
        return false;
      }
    }
    else if(x() == toX){
      if(!horizontalCheck(x(), y(), toX, toY, battlefield)) {
        System.out.println("Obstacle on the way to the target of pos "+ toX +" "+ toY +" ! Can't shoot.");
        return false;
      }
    }
    return true;
  }


  public boolean diagonalCheck(int x, int y, int toX, int toY, Battlefield battlefield) {
    boolean emptyTile = battlefield.canPlaceAt(x, y);
    System.out.println(x);
    System.out.println(y);
    System.out.println("------------------------");
    if(x < toX && y < toY) {
      for(int i = x; i < toX; i++) {
        for(int j = y; j < toY; j++) {
          if(!emptyTile) {
            return false;
          }
        }
      }
    }
    else if(x < toX && y > toY) {
      for(int i = x; i < toX; i++) {
        for(int j = y; j > toY; j--) {
          if(!emptyTile) {
            return false;
          }
        }
      }
    }
    else if(x > toX && y > toY) {
      for(int i = x; i > toX; i--) {
        for(int j = y; j > toY; j--) {
          if(!emptyTile) {
            return false;
          }
        }
      }
    }
    else if(x > toX && y < toY) {
      for(int i = x; i > toX; i--) {
        for(int j = y; j < toY; j++) {
          if(!emptyTile) {
            return false;
          }
        }
      }
    }
    return true;
  }

  // - Case when y = toY
  public boolean verticalCheck(int x, int y, int toX, int toY, Battlefield battlefield) {
    boolean emptyTile = battlefield.canPlaceAt(x, y);
    System.out.println(x);
    System.out.println(y);
    System.out.println("------------------------");
    if( x>toX ) {
      for(int i = x; i > toX; i--){
        if(!emptyTile){
          return false;
        }
      }
    }
    else if( x < toX) {
      for(int i = x; i > toX; i++){
        if(!emptyTile){
          return false;
        }
      }
    }
    return true;
  }

  // - Case when x = toX
  public boolean horizontalCheck(int x, int y, int toX, int toY, Battlefield battlefield) {
    boolean emptyTile = battlefield.canPlaceAt(x, y);
    System.out.println(x);
    System.out.println(y);
    System.out.println("------------------------");
    if( y>toY ) {
      for(int i = y; i > toY; i--){
        if(!emptyTile){
          return false;
        }
      }
    }
    else if( y < toY) {
      for(int i = y; i < toY; i++){
        if(!emptyTile){
          return false;
        }
      }
    }
    return true;
  }
}
