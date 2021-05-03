package lol.game;

import java.util.*;
import java.io.*;
import java.net.*;
import lol.common.*;
import lol.game.action.*;

public class Team {
  private int teamID;
  private ArrayList<Champion> champions;
  private Nexus nexus;
  private Battlefield battlefield;

  public Team(int teamID, Battlefield battlefield) {
    this.teamID = teamID;
    this.champions = new ArrayList<>();
    this.nexus = battlefield.nexusOf(teamID);
    this.battlefield = battlefield;
  }

  public void addChampion(Champion c) {
    champions.add(c);
  }

  public void spawnChampion(int championID, int x, int y) {
    Champion champion = champions.get(championID);
    boolean placed = battlefield.placeAt(champion, x, y);
    if(!placed) {
      System.out.println("Invalid spawn position of champion " + champion.name());
    }
  }

  public void moveChampion(int championID, int x, int y) {
    Champion champion = champions.get(championID);
    boolean placed = false;
    if(champion.canWalkTo(x, y)) {
      placed = battlefield.moveTo(champion, x, y);
    }
    if(!placed) {
      System.out.println("Invalid move position of champion " + champion.name());
    }
  }

  // Simple int holder for `champIdx` in makeSpawnTurn ("local variables referenced from an inner class must be final").
  class MutableInt {
    public int v;
    MutableInt(int v) { this.v = v; }
  }

  public void makeSpawnTurn(final Turn turn) {
    MutableInt champIdx = new MutableInt(0);
    battlefield.visitAdjacent(nexus.x(), nexus.y(), 1, new TileVisitor(){
      public void visitGrass(int x, int y) {
        if(champIdx.v < champions.size()) {
          turn.registerAction(new Spawn(teamID, champIdx.v, x, y));
          champIdx.v = champIdx.v + 1;
        }
      }
    });
    if(champIdx.v != champions.size()) {
      throw new RuntimeException("Cannot place all champions because all spawned spots next to the Nexus are already taken.");
    }
  }

  @Override public String toString() {
    String data = "";
    for(Champion c : champions) {
      data += c.toString() + " ";
    }
    return data;
  }
}
