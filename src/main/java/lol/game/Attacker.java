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

    public int damages() {
        return damages;
    }

    public void boostDamages(int boost) {
        this.damages += boost;
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
        return distanceFrom(toX, toY) <= rangeOfAttack;
    }
}
