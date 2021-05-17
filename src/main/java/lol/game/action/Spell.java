package lol.game.action;

import lol.game.*;
import java.io.Serializable;

public abstract class Spell extends Action implements Serializable {

  public Spell(int teamID) {
    super(teamID);
  }
}