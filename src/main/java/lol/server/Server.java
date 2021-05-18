package lol.server;

import java.net.*;
import java.io.*;
import java.util.*;
import lol.common.*;
import lol.game.*;
import lol.game.action.*;
import lol.ui.*;

public class Server implements Runnable {
  private ArrayList<Player> players;
  private final ArrayList<Player> connectedUsers;
  private ArrayList<Integer> playerScores;
  private ServerSocket server;
  private Arena arena;
  private LOL2D ui;
  private Battlefield battlefield;

  public Server(LOL2D ui, Battlefield battlefield) {
    this.ui = ui;
    players = new ArrayList<>();
    connectedUsers = new ArrayList<>();
    playerScores = new ArrayList<>();
    this.battlefield = battlefield;
    arena = new Arena(battlefield);
  }

  public void resetEnvironment() {
    System.out.println("Resetting the arena and battlefield for the next round.");
    ASCIIBattlefieldBuilder battlefieldBuilder = new ASCIIBattlefieldBuilder();
    battlefield = battlefieldBuilder.build();
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
      printScores();
    }
    else {  //single match
      System.out.println("There were only 2 players. A single match will be held.");
      for(Player p : connectedUsers) {
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
    connectedUsers.add(new Player(socket, this));
    playerScores.add(0);
  }

  //tourney seeded into 2 groups depending on the sign up order.
  private void tourneyOrganization() throws IOException {
    ArrayList<Player> group1 = new ArrayList<Player>();
    ArrayList<Player> group2 = new ArrayList<Player>();
    for (int i = 0; i < connectedUsers.size(); i++) {
      if(i % 2 == 0)
        group1.add(connectedUsers.get(i));
      else
        group2.add(connectedUsers.get(i));
    }
    //round robin rules according to wikipedia explanation.
    //Dummy player round is skipped.
    for(int set = 0; set < group1.size(); set++) {
      for (int round = 0; round < group2.size(); round++) {
        group1.add(1, group2.get(0));
        group2.remove(0);
        group2.add(group1.get(group1.size()-1));
        group1.remove(group1.size()-1);
        //group 2 will be the smaller one if uneven user size.
        if(round < group2.size()) {
          roundOrganizer(group1.get(round), group2.get(round));
        resetEnvironment();
        }
      }
    }
  }

  private void roundOrganizer(Player playerGroup1, Player playerGroup2) throws IOException {
    players.clear();
    Player bluePlayer = playerGroup1;
    Player redPlayer = playerGroup2;
    bluePlayer.setRoundUID(0);
    redPlayer.setRoundUID(1);
    players.add(bluePlayer);
    players.add(redPlayer);
    startGame();
    gameLoop();
    int winner = endOfGameMessage();
    if(winner == 0)
      updateScores(playerGroup1);
    else
      updateScores(playerGroup2);
  }

  //updates number of matches that a team won in the tourney.
  private void updateScores(Player winningPlayer) {
    int scoreIndex = connectedUsers.indexOf(winningPlayer);
    int oldScore = playerScores.get(scoreIndex);
    playerScores.set(scoreIndex, oldScore++);
  }

  private void printScores() {
    int scoreIndex = 0;
    for(Player p : connectedUsers){
      System.out.println("Team: " + p.getGeneralUID() + " won: " + playerScores.get(scoreIndex) + " rounds.");
      scoreIndex++;
    }
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

  private int endOfGameMessage() {
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
