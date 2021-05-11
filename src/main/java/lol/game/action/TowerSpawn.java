package lol.game.action;

import java.io.Serializable;

public class TowerSpawn extends Action implements Serializable {
  protected int x;
  protected int y;

  public TowerSpawn(int teamID, int x, int y) {
    super(teamID);
    this.x = x;
    this.y = y;
  }

  public void accept(ActionVisitor visitor) {
    visitor.visitTowerSpawn(teamID, x, y);
  }
}
