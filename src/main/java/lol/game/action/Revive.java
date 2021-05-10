package lol.game.action;

import lol.game.*;
import java.io.Serializable;

public class Revive extends ChampionAction implements Serializable {

  public Revive(int teamID, int championID, int x, int y) {
    super(teamID, championID, x, y);
  }

  public void accept(ActionVisitor visitor) {
    visitor.visitRevive(teamID, championID, x, y);
  }
}