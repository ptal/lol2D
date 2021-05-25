package lol.server;

import java.net.*;
import java.io.*;
import java.util.*;
import lol.common.*;
import lol.game.*;
import lol.game.action.*;
import lol.ui.*;

public class Server implements Runnable {
  private final ArrayList<Player> connectedUsers;
  private ArrayList<Integer> playerScores;
  private ServerSocket server;
  private LOL2D ui;

  public Server(LOL2D ui) {
    this.ui = ui;
    connectedUsers = new ArrayList<>();
    playerScores = new ArrayList<>();
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
    if(connectedUsers.size() > 1) { //tourney
      System.out.println("A tourney will be started.");
      tourneyOrganization();
      printScores();
    }
    else
      System.out.println("Not enough players to start a match.");
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
    if(group2.size() < group1.size()) {
      group2.add(null); //add dummy player.
    }
    //round robin rules according to wikipedia explanation.
    //Dummy player round is skipped.
    int set = 0;
    int round = 0;
    while(set < (int)((double)((group1.size()+group2.size())/2)*((group1.size()+group2.size())-1))) {
      round = 0;
      while (round < group1.size()) {
        //group 2's dummy player is overgone.
        if(group2.get(round) != null && group2.get(round) != null) {
          roundOrganizer(group1.get(round), group2.get(round));
        }
        round++;
      }
      set += round;
      group1.add(1, group2.get(0));
      group2.remove(0);
      group2.add(group1.get(group1.size()-1));
      group1.remove(group1.size()-1);
    }

  }

  private void roundOrganizer(Player playerGroup1, Player playerGroup2) throws IOException {
    Player bluePlayer = playerGroup1;
    Player redPlayer = playerGroup2;
    bluePlayer.setRoundUID(0);
    redPlayer.setRoundUID(1);
    Game match = new Game(this.ui, bluePlayer, redPlayer, ui.makeNewGame());
    match.startGame();
    match.gameLoop();
    int winner = match.endOfGameMessage();
    if(winner == 0)
      updateScores(playerGroup1);
    else
      updateScores(playerGroup2);
  }

  //returns the number of matches that a team won in the tourney.
  private void updateScores(Player winningPlayer) throws IOException {
    int scoreIndex = connectedUsers.indexOf(winningPlayer);
    int oldScore = playerScores.get(scoreIndex);
    oldScore++;
    playerScores.set(scoreIndex, oldScore);
  }

  private void printScores() throws IOException {
    int scoreIndex = 0;
    for(Player p : connectedUsers){
      p.setRoundUID(playerScores.get(scoreIndex)+2);
      p.sendUID();
      scoreIndex++;
    }
    System.out.println("Teamscores were sent to the clients. Good luck in the next tourney");
  }

  private void wait(int timeInMS) {
    try {
      Thread.sleep(timeInMS); // wait time in milliseconds to control duration
    } catch(InterruptedException e) {
      e.printStackTrace();
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