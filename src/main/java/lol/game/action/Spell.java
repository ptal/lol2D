package lol.game.action;

import lol.game.*;
import java.io.Serializable;

public abstract class Spell extends ChampionAction implements Serializable {

  public Spell(int teamID, int championID, int x, int y) {
    super(teamID,championID,x,y);
  }
}