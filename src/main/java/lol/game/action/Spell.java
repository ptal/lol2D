package lol.game.action;

import lol.game.*;
import java.io.Serializable;

public abstract class Spell extends Action implements Serializable {
  protected int championID;

  public Spell(int teamID, int championID) {
    super(teamID);
    this.championID=championID;
  }
}