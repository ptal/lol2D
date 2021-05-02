package lol.game;

import java.util.*;

public class Team {
  private ArrayList<Champion> champions;

  public Team() {
    champions = new ArrayList<>();
  }

  public void addChampion(Champion c) {
    champions.add(c);
  }

  public String compositionPacket() {
    String networkPacket =
      "[composition]\n" +
      champions.size() + "\n";
    for(Champion c : champions) {
      networkPacket = networkPacket + c.name() + "\n";
    }
    return networkPacket;
  }
}
