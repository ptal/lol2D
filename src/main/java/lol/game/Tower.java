package lol.game;

public class Tower extends Destructible{
    public static final int HP_TOWER = 5;
    public static final int RANGE_OF_ATTACK = 2;
    public static final int DAMAGE_TOWER = 4;
    public static final int BLUE = 0;
    public static final int RED = 1;
    private int teamID;
    private int rangeOfAttack;
    private int damages;

    // we let the attributes be constants for now
    public Tower(int teamID) {
        super(HP_TOWER);
        this.teamID = teamID;
        this.rangeOfAttack = RANGE_OF_ATTACK;
        this.damages = DAMAGE_TOWER;
    }

    public int teamOfTower() {
        return teamID;
    }

    public int attackRange() {
        return rangeOfAttack;
    }

    private int distanceFrom(int toX, int toY) {
        return Math.max(Math.abs(toX - x()), Math.abs(toY - y()));
    }

    public boolean canAttack(int toX, int toY) {
        return distanceFrom(toX, toY) <= rangeOfAttack;
    }

    public boolean attack(Destructible d) {
        if(canAttack(d.x(), d.y())) {
            d.hit(damages);
            return true;
        }
        return false;
    }

    public String name() {
        return name();
    }

    @Override public void accept(TileVisitor visitor) {
        visitor.visitTower(this);
    }

    @Override public void hit(int damage) {
        super.hit(damage);
        System.out.println(this);
    }

    @Override public String toString() {
        return (teamID == BLUE ? "Blue" : "Red") + " Tower: " + currentHP + "hp left";
    }
}
