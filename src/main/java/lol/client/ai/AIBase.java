package lol.client.ai;

import lol.game.*;

public abstract class AIBase {
  protected Team team;
  protected int uid;
  protected Arena arena;

  final public void setTeam(Team team) {
    this.team = team;
  }

  final public void setUID(int uid) {
    this.uid = uid;
  }

  final public void setArena(Arena arena) {
    this.arena = arena;
    this.team = arena.teamOf(uid);
  }

  public abstract Team teamComposition();
}
