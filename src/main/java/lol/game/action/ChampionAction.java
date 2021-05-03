package lol.game.action;

import lol.game.*;
import java.io.Serializable;

public abstract class ChampionAction extends Action implements Serializable {
  protected int championID;
  protected int x;
  protected int y;

  public ChampionAction(int teamID, int championID, int x, int y) {
    super(teamID);
    this.championID = championID;
    this.x = x;
    this.y = y;
  }
}
