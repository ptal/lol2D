package lol.server;

import java.net.*;
import java.io.*;
import java.util.*;
import lol.common.*;
import lol.game.*;
import lol.game.action.*;
import lol.ui.*;

public class Server implements Runnable {
  private final ArrayList<Player> players;
  private ServerSocket server;
  private Arena arena;
  private LOL2D ui;
  private Battlefield battlefield;
  private int MILLISECOND_PER_TURN = 2000;

  public Server(LOL2D ui, Battlefield battlefield) {
    this.ui = ui;
    players = new ArrayList<>();
    this.battlefield = battlefield;
    arena = new Arena(battlefield);
  }

  @Override
  public void run() {
    System.out.println("Starting server...");
    try(ServerSocket serverRessource = new ServerSocket(ServerInfo.port)) {
      server = serverRessource;
      while(players.size() < battlefield.numberOfTeams()) {
        waitNewPlayer();
      }
      startGame();
      gameLoop();
      endOfGameMessage();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    stop();
    System.out.println("Server shutted down.");
  }

  private void waitNewPlayer() throws IOException {
    Socket socket = server.accept();
    System.out.println("New player at " + socket);
    players.add(new Player(socket, this));
  }

  private void startGame() throws IOException {
    championSelectionPhase();
    spawnPhase();
    arena.startGamePhase();
    ui.update();
    wait(MILLISECOND_PER_TURN);
  }

  private void wait(int timeInMS) {
    try {
      Thread.sleep(timeInMS); // wait time in milliseconds to control duration
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void endOfGameMessage() {
    for(int i = 0; i < battlefield.numberOfTeams(); ++i) {
      Nexus nexus = battlefield.nexusOf(i);
      if(nexus.isAlive()) {
        System.out.println("WINNER: " + nexus);
      }
      else {
        System.out.println("LOSER: " + nexus);
      }
    }
  }

  private void gameLoop() throws IOException {
    for(int turns = 0; battlefield.allNexusAlive() && turns < lol.client.Client.MAX_TURNS; ++turns) {
      for(Player player : players) {
        Turn turn = player.askTurn();
        arena.applyTurn(turn);
        ui.update();
        broadcast(turn);
        wait(MILLISECOND_PER_TURN);
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

  // Can be called from any thread. The server will be shutted down
  // as soon as possible. Note that it is just a request, it returns immediatly.
  public void stop() {
    try {
      if(server != null) {
        server.close();
      }
    } catch(IOException e) {}
  }
}
