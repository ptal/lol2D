package lol.client;

import lol.common.*;
import java.net.*;
import java.io.*;
import java.util.*;
import lol.game.*;
import lol.game.action.*;
import lol.client.ai.*;

public class Client implements Runnable {
  public static int MAX_TURNS = 25;

  public static void main(String[] args) {
    ASCIIBattlefieldBuilder battlefieldBuilder = new ASCIIBattlefieldBuilder();
    Battlefield battlefield = battlefieldBuilder.build();
    Arena arena = new Arena(battlefield);
    RandomAI ai = new RandomAI(arena, battlefield);
    new Client(ai, arena, battlefield).run();
  }

  private AIBase ai;
  private int teamID;
  private Arena arena;
  private Battlefield battlefield;
  private Socket socket;

  public Client(AIBase ai, Arena arena, Battlefield battlefield) {
    this.ai = ai;
    this.arena = arena;
    this.battlefield = battlefield;
  }

  @Override
  public void run() {
    System.out.println("Connecting to server " + ServerInfo.info());
    try(Socket s = new Socket(ServerInfo.ip, ServerInfo.port)) {
      socket = s;
      System.out.println("Connection succeeds.");
      receiveUID();
      ai.initTeamID(teamID);
      System.out.println("UID received: " + teamID);
      Turn turn = ai.championSelect();
      System.out.println("champion selected");
      turn.send(socket);
      System.out.println("sent");
      allChampionSelection();
      System.out.println("allCham");
      allSpawningChampion();
      System.out.println("Champion selection phase done.");
      // Now the turn-based game starts until the game is over.
      arena.startGamePhase();
      gameLoop();
    }
    catch (IOException e) {
      System.err.println(e);
    }
  }

  private void gameLoop() throws IOException {
    for(int turns = 0; battlefield.allNexusAlive() && turns < MAX_TURNS; ++turns) {
      for(int i = 0; i < arena.numberOfTeams(); ++i) {
        if(i == teamID) {
          Turn turn = ai.turn();
          turn.send(socket);
        }
        oneTurn();
      }
    }
  }

  private void allChampionSelection() throws IOException {
    turnsFromAll();
  }

  private void allSpawningChampion() throws IOException {
    oneTurn();
  }

  private void oneTurn() throws IOException {
    Turn turn = Turn.receive(socket);
    ai.applyTurn(turn);
  }

  private void turnsFromAll() throws IOException {
    for(int i = 0; i < arena.numberOfTeams(); ++i) {
      oneTurn();
    }
  }

  private void receiveUID() throws IOException {
    InputStream inputStream = socket.getInputStream();
    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
    Object rawUID = null;
    try { rawUID = objectInputStream.readObject(); } catch(Exception e) {}
    if(!(rawUID instanceof Integer)) {
      throw new ProtocolException("an UID of type `Integer`");
    }
    teamID = (Integer) rawUID;
  }
}
