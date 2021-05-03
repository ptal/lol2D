package lol.client.ai;

import lol.game.*;

public class RandomAI extends AIBase {

  public Team teamComposition() {
    if(team.color() == Nexus.Color.BLUE) {
      team.addChampion(Champion.makeWarrior());
    }
    else {
      team.addChampion(Champion.makeArcher());
    }
    return team;
  }
}
