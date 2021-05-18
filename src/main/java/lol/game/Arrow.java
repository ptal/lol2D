package lol.game;


public class Arrow extends Projectile {
    private int rotation;

    public Arrow(int xCoord, int yCoord, int rotation) {
        super(xCoord, yCoord);
        this.rotation = rotation;
    }

    @Override public void accept(TileVisitor visitor) {
        visitor.visitArrow(this);
    }
}
