package lol.game;

import java.util.*;
import java.io.*;
import java.net.*;
import lol.common.*;
import lol.game.action.*;

public class Arena {
  private ArrayList<Team> teams;
  private Battlefield battlefield;

  public enum Phase {
    CHAMP_SELECT,
    GAME,
    TERMINATED;
  }

  private Phase phase;

  public Arena(Battlefield battlefield) {
    teams = new ArrayList<>();
    this.battlefield = battlefield;
    this.phase = Phase.CHAMP_SELECT;
  }

  public void initTeam() {
    int teamID = teams.size();
    teams.add(new Team(teamID, battlefield));
  }

  public int teamsNum() {
    return teams.size();
  }

  public Team teamOf(int teamID) {
    return teams.get(teamID);
  }

  private class ApplyAction implements ActionVisitor {
    public void visitSpawn(int teamID, int championID, int x, int y) {
      teams.get(teamID).spawnChampion(championID, x, y);
    }

    public void visitChampionSelect(int teamID, String championName) {
      if(phase == Phase.CHAMP_SELECT) {
        try {
          teams.get(teamID).addChampion(Champion.make(championName));
        }
        catch(RuntimeException e) {
          System.out.println(e);
        }
      }
      else {
        System.out.println("Champion selection action outside of the champion selection phase (teamID = "
          + teamID + ", championName = " + championName + ".");
      }
    }
  }

  public void applyTurn(Turn turn) {
    turn.accept(new ApplyAction());
  }

  public Turn spawnTurn() {
    Turn turn = new Turn();
    for(Team team : teams) {
      team.makeSpawnTurn(turn);
    }
    return turn;
  }

  public void startGamePhase() {
    phase = Phase.GAME;
  }

  @Override public String toString() {
    String data = "Arena:\n";
    data += "  Teams:\n";
    for(int i = 0; i < teams.size(); ++i) {
      data += "    " + i + ": " + teams.get(i).toString() + "\n";
    }
    return data;
  }
}