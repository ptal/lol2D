package lol.client.ai;

import lol.game.*;

public interface AIBase {
  public Team teamComposition();
  public void setUID(int uid);
}
