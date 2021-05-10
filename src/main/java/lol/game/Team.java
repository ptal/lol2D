package lol.game;

import java.util.*;
import java.io.*;
import java.net.*;
import java.util.function.BiConsumer;
import lol.common.*;
import lol.game.action.*;

public class Team {
  private int teamID;
  private ArrayList<Champion> champions;
  private Nexus nexus;
  private Battlefield battlefield;
  private boolean logInvalidMove = false;

  public Team(int teamID, Battlefield battlefield) {
    this.teamID = teamID;
    this.champions = new ArrayList<>();
    this.nexus = battlefield.nexusOf(teamID);
    this.battlefield = battlefield;
  }

  public void addChampion(Champion c) {
    champions.add(c);
  }

  public void forEachChampion(BiConsumer<Champion, Integer> f) {
    for(int i = 0; i < champions.size(); ++i) {
      f.accept(champions.get(i), i);
    }
  }

  public boolean belongsToTeam(Champion champion) {
    return champions.contains(champion);
  }

  public boolean spawnChampion(int championID, int x, int y) {
    Champion champion = champions.get(championID);
    boolean placed = battlefield.placeAt(champion, x, y);
    if(!placed) {
      System.out.println("Invalid spawn position of champion " + champion.name());
    }
    return placed;
  }

  public void removeChampion(Destructible d ,int x ,int y) {
    battlefield.removeChampion(d, x, y);
  }

  public boolean reviveChampion(int championID ,int x ,int y) {
    Champion champion = champions.get(championID);
    boolean revived = false;
    if(champion.reachedTimer()) {
      revived = battlefield.placeAt(champion, x, y);
      champion.revive();
      champion.resetTimer();
    }
    else {
      champion.increaseTimer();
      revived = true;
    }
    if(!revived && logInvalidMove) {
      System.out.println("Invalid respawn position of champion " + champion.name());
    }
    return revived;
  }

  public boolean moveChampion(int championID, int x, int y) {
    Champion champion = champions.get(championID);
    boolean moved = false;
    if(champion.canWalkTo(x, y)) {
      moved = battlefield.moveTo(champion, x, y);
    }
    if(!moved && logInvalidMove) {
      System.out.println("Invalid move position of champion " + champion.name());
    }
    return moved;
  }

  public boolean championAttack(int championID, int x, int y) {
    Champion champion = champions.get(championID);
    boolean[] attacked = {false};
    battlefield.visit(x, y, new TileVisitor(){
      @Override public void visitDestructible(Destructible d) {
        attacked[0] = champion.attack(d);
        removeChampion(d, x ,y);
      }
    });
    if(!attacked[0]) {
      System.out.println("Invalid attack target of champion " + champion.name());
    }
    return attacked[0];
  }

  public void makeSpawnTurn(final Turn turn) {
    int[] champIdx = {0};
    battlefield.visitAdjacent(nexus.x(), nexus.y(), 1, new TileVisitor(){
      @Override public void visitGrass(int x, int y) {
        if(champIdx[0] < champions.size()) {
          turn.registerAction(new Spawn(teamID, champIdx[0], x, y));
          champIdx[0] = champIdx[0] + 1;
        }
      }
    });
    if(champIdx[0] != champions.size()) {
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
