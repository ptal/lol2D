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
  private ArrayList<Player> connectedUsers;
  private ServerSocket server;
  private Arena arena;
  private LOL2D ui;
  private Battlefield battlefield;

  public Server(LOL2D ui, Battlefield battlefield) {
    this.ui = ui;
    players = new ArrayList<>();
    connectedUsers = new ArrayList<>();
    this.battlefield = battlefield;
    arena = new Arena(battlefield);
  }

  @Override
  public void run() {
    System.out.println("Starting server...");
    try(ServerSocket serverRessource = new ServerSocket(ServerInfo.port)) {
      server = serverRessource;
      registrationPhase();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    stop();
    System.out.println("Server shutted down.");
  }

  public void registrationPhase() throws IOException {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the number of participants for the tourney/match: ");
    int expectedParticipants = scanner.nextInt();
    scanner.close();
    while(expectedParticipants != connectedUsers.size()) {
      waitNewPlayer();
      System.out.println(connectedUsers.size() + "/" + expectedParticipants + " players joined.");
    }
    if(connectedUsers.size() > 2) { //tourney
      System.out.println("A tourney will be started.");
      tourneyOrganization();
    }
    else {  //single match
      System.out.println("There were only 2 players. A single match will be held.");
      for(Player p: connectedUsers) {
        players.add(p);
      }
      startGame();
      gameLoop();
      endOfGameMessage();
    }
  }

  private void waitNewPlayer() throws IOException {
    Socket socket = server.accept();
    System.out.println("New player at " + socket);
    players.add(new Player(socket, this));
  }

  private void tourneyOrganization() throws IOException {
    ArrayList<Integer> set1Index = new ArrayList<Integer>();
    ArrayList<Integer> set2Index = new ArrayList<Integer>();
    for (int i = 0; i < connectedUsers.size(); i++) {
      if(i % 2 == 0)
        set1Index.add(i);
      else
        set2Index.add(i);
    }
    if(set2Index.size() < set1Index.size())
      set2Index.add(-1);
    for(int set = 0; set < set1Index.size(); set++) {
      for (int round = 0; round < set2Index.size(); round++) {
        int help = set2Index.get(0);
        set2Index.add(0, set2Index.get(size() - 1));
        set2Index.add(set2Index.get(size() - 1), help);
        if(set2Index.get(round) != -1) {
          roundOrganizer(set1Index.get(round));
        }
      }
    }
  }

  private void roundOrganizer(int playerIndex) throws IOException {
    players.clear();
    players.add(connectedUsers.get(playerIndex));
    players.add(connectedUsers.get(playerIndex));
    startGame();
    gameLoop();
    endOfGameMessage();
  }

  private void startGame() throws IOException {
    championSelectionPhase();
    spawnPhase();
    arena.startGamePhase();
    ui.update();
    wait(2000);
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
