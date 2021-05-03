package lol.server;

import java.net.*;
import java.io.*;
import java.util.*;
import lol.common.*;
import lol.game.*;
import lol.ui.*;

public class Server implements Runnable {
  private final static int NUM_PLAYERS = 2;
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
    arena = new Arena(battlefield);
  }

  @Override
  public void run() {
    System.out.println("Starting server...");
    try(ServerSocket serverRessource = new ServerSocket(ServerInfo.port)) {
      server = serverRessource;
      while(players.size() < NUM_PLAYERS) {
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
    newPlayer(socket);
  }

  private void newPlayer(Socket socket) {
    Player player = new Player(socket, this);
    players.add(player);
  }

  private void startGame() throws IOException {

    askTeamComposition();
    arena.spawnChampions();
    ui.update();
    broadcastTeamComposition();
  }

  private void askTeamComposition() throws IOException {
    System.out.println("Ask team composition...");
    for(Player p : players) {
      Team team = p.askTeamComposition();
      arena.addTeam(team);
      p.sendUID();
    }
  }

  private void broadcastTeamComposition() throws IOException {
    System.out.println("Broadcast team composition...");
    for(Player p : players) {
      p.sendTeamComposition(arena);
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
