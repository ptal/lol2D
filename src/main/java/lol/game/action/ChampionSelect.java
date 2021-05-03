package lol.game.action;

import lol.game.*;
import java.io.Serializable;

public class ChampionSelect extends Action implements Serializable {
  private String championName;

  public ChampionSelect(int teamID, String championName) {
    super(teamID);
    this.championName = championName;
  }

  public void accept(ActionVisitor visitor) {
    visitor.visitChampionSelect(teamID, championName);
  }
}
