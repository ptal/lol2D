package lol.server;

import java.net.*;
import java.io.*;
import java.util.*;
import lol.common.*;
import lol.game.*;
import lol.game.action.*;
import lol.ui.*;

public class Game {

  private ArrayList<Player> players;
  private Arena arena;
  private Battlefield battlefield;
  private LOL2D ui;

  public Game(LOL2D ui, Player bluePlayer, Player redPlayer, Battlefield battlefield) {
    this.ui = ui;
    players = new ArrayList<>();
    players.add(bluePlayer);
    players.add(redPlayer);
    this.battlefield = battlefield;
    arena = new Arena(battlefield);
  }

  public void startGame() throws IOException {
    championSelectionPhase();
    spawnPhase();
    arena.startGamePhase();
    ui.update();
    wait(2000);
  }

  public void gameLoop() throws IOException {
    for(int turns = 0; battlefield.allNexusAlive() && turns < lol.client.Client.MAX_TURNS; ++turns) {
      for(Player player : players) {
        Turn turn = player.askTurn();
        arena.applyTurn(turn);
        ui.update();
        broadcast(turn);
        wait(2000);
      }
    }
  }

  private void championSelectionPhase() throws IOException {
    System.out.println("Champion selection phase...");
    ArrayList<Turn> champSelect = new ArrayList<Turn>();
    for(Player p : players) {
      p.sendUID();
      Turn turn = p.askTurn();
      champSelect.add(turn);
      arena.applyTurn(turn);
    }
    System.out.println("Broadcast team composition...");
    broadcast(champSelect);
  }

  private void spawnPhase() throws IOException {
    Turn turn = arena.spawnTurn();
    arena.applyTurn(turn);
    System.out.println("Broadcast spawning positions...");
    broadcast(turn);
  }

  private void broadcast(Turn turn) throws IOException {
    for(Player p : players) {
      p.sendTurn(turn);
    }
  }

  private void broadcast(ArrayList<Turn> turns) throws IOException {
    for(Player p : players) {
      p.sendTurns(turns);
    }
  }

  public int endOfGameMessage() {
    int winner = -1;
    for(int i = 0; i < battlefield.numberOfTeams(); ++i) {
      Nexus nexus = battlefield.nexusOf(i);
      if(nexus.isAlive()) {
        System.out.println("WINNER: " + nexus);
        winner = i;
      }
      else {
        System.out.println("LOSER: " + nexus);
      }
    }
    return winner;
  }

  private void wait(int timeInMS) {
    try {
      Thread.sleep(timeInMS); // wait time in milliseconds to control duration
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
  }
}