package lol.game.action;

import lol.game.*;
import java.io.Serializable;

public abstract class Action implements Serializable {
  protected int teamID;

  public Action(int teamID) {
    this.teamID = teamID;
  }

  public abstract void accept(ActionVisitor visitor);
}
