package lol.game;


public class Arrow extends Projectile {
    private int rotation;

    public Arrow(int rotation) {
        super();
        this.rotation = rotation;
    }

    @Override public void accept(TileVisitor visitor) {
        visitor.visitArrow(this);
    }
}
