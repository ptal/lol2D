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
  private final Thread acceptTask;
  private ServerSocket server;
  private Arena arena;
  private LOL2D ui;
  private Battlefield battlefield;

  public Server(LOL2D ui, Battlefield battlefield) {
    this.ui = ui;
    players = new ArrayList<>();
    acceptTask = Thread.currentThread();
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
    }
    catch (IOException e) {
      System.err.println(e);
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
