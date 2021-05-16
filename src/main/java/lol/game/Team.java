package lol.game;

import java.util.*;
import java.io.*;
import java.net.*;
import java.util.function.BiConsumer;
import lol.common.*;
import lol.game.action.*;
import lol.ui.Sound;

public class Team {
  private int teamID;
  private ArrayList<Champion> champions;
  private Tower tower;
  private Nexus nexus;
  private Battlefield battlefield;
  private BattlefieldTraversal traversal;
  private boolean logInvalidMove = false;
  private Sound sound;
  public Team(int teamID, Battlefield battlefield) {
    this.teamID = teamID;
    this.champions = new ArrayList<>();
    this.nexus = battlefield.nexusOf(teamID);
    this.tower = battlefield.towerOf(teamID);
    this.battlefield = battlefield;
    this.sound = new Sound();
    this.traversal = new BattlefieldTraversal(battlefield);
  }

  public void addChampion(Champion c) {
    champions.add(c);
  }

  public void forEachChampion(BiConsumer<Champion, Integer> f) {
    for(int i = 0; i < champions.size(); ++i) {
      f.accept(champions.get(i), i);
    }
  }

  public boolean spawnChampion(int championID, int x, int y) {
    Champion champion = champions.get(championID);
    boolean placed = battlefield.placeAt(champion, x, y);
    if(!placed) {
      System.out.println("Invalid spawn position of champion " + champion.name());
    }
    return placed;
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
        sound.attackSound(champion.name());
        if(d.isDead()) {
          battlefield.destroy(d);
          sound.destroyBuilding();
          d.boostTeam(Team.this);
        }
      }
    });
    if(!attacked[0]) {
      System.out.println("Invalid attack target of champion " + champion.name());
    }
    return attacked[0];
  }

  public void makeSpawnTurn(final Turn turn) {
    int[] champIdx = {0};
    traversal.visitAdjacent(nexus.x(), nexus.y(), 1, new TileVisitor(){
      @Override public void visitGrass(int x, int y) {
        if(champIdx[0] < champions.size()) {
          turn.registerAction(new Spawn(teamID, champIdx[0], x, y));
          System.out.println("Spawn hero " + champIdx[0] + " at " + x + ", " + y + " team " + teamID);
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
