package lol.game.action;

import lol.game.*;
import java.io.Serializable;

public class Move extends ChampionAction implements Serializable {
  public Move(int teamID, int championID, int x, int y) {
    super(teamID, championID, x, y);
  }

  public void accept(ActionVisitor visitor) {
    visitor.visitMove(teamID, championID, x, y);
  }
}
