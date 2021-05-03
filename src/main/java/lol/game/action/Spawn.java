package lol.game.action;

import lol.game.*;
import java.io.Serializable;

public class Spawn extends ChampionAction implements Serializable {

  public Spawn(int teamID, int championID, int x, int y) {
    super(teamID, championID, x, y);
  }

  public void accept(ActionVisitor visitor) {
    visitor.visitSpawn(teamID, championID, x, y);
  }
}
