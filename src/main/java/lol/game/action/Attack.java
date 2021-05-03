package lol.game.action;

import lol.game.*;
import java.io.Serializable;

public class Attack extends ChampionAction implements Serializable {
  public Attack(int teamID, int championID, int x, int y) {
    super(teamID, championID, x, y);
  }

  public void accept(ActionVisitor visitor) {
    visitor.visitAttack(teamID, championID, x, y);
  }
}
