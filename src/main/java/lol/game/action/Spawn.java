package lol.game.action;

import lol.game.*;
import java.io.Serializable;

public class Spawn extends Action implements Serializable {
  private int championID;
  private int x;
  private int y;

  public Spawn(int teamID, int championID, int x, int y) {
    super(teamID);
    this.championID = championID;
    this.x = x;
    this.y = y;
  }

  public void accept(ActionVisitor visitor) {
    visitor.visitSpawn(teamID, championID, x, y);
  }
}
