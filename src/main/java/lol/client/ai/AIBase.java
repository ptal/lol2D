package lol.client.ai;

import lol.game.*;
import lol.game.action.*;

public abstract class AIBase {
  protected int teamID;
  protected Arena arena;
  protected Battlefield battlefield;

  public AIBase(Arena arena, Battlefield battlefield) {
    this.arena = arena;
    this.battlefield = battlefield;
  }

  public void initTeamID(int teamID) {
    this.teamID = teamID;
  }

  // Apply the actions performed by the opponent or server.
  // You can override this method to inspect what the opponent did.
  // This method is also used for the spawning of champions (including ours).
  public void applyTurn(Turn turn) {
    arena.applyTurn(turn);
  }

  // This is the first turn where champions are selected.
  public abstract Turn championSelect();

  // The turn of our team.
  // This method is necessarily invoked after `championSelect`.
  public abstract Turn turn();
}
