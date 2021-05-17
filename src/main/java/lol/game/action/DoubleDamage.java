package lol.game.action;

import lol.game.*;
import java.io.Serializable;

public abstract class DoubleDamage extends Spell implements Serializable {

  public Spell(int teamID, int championID) {
    super(teamID,championID);
  }
}