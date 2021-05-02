package lol.client.ai;

import lol.game.*;

public class RandomAI implements AIBase {
  private Team team;
  private int uid;

  public RandomAI(boolean fighterTeam) {
    team = new Team();
    if(fighterTeam) {
      team.addChampion(Champion.makeWarrior());
    }
    else {
      team.addChampion(Champion.makeArcher());
    }
  }

  public Team teamComposition() {
    return team;
  }

  public void setUID(int uid) {
    this.uid = uid;
  }
}
