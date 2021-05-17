package lol.game.action;

import lol.game.*;
import java.io.Serializable;

public abstract class DoubleDamage extends Spell implements Serializable {
  protected int x;
  protected int y;

  public Spell(int teamID, int championID, int x, int y) {
    super(teamID,championID);
    this.x = x;
    this.y = y;
  }

  public void accept(ActionVisitor visitor) {
    visitor.visitDoubleDamage(teamID, championID, x, y);
  }
}