package lol.client;

import lol.common.*;
import java.net.*;
import java.io.*;
import java.util.*;
import lol.game.*;
import lol.client.ai.*;


public class Client implements Runnable {
  public static void main(String[] args) {
    new Client(new RandomAI()).run();
  }

  private AIBase ai;
  private int uid;
  private Arena arena;
  private Socket socket;

  public Client(AIBase ai) {
    this.ai = ai;
  }

  @Override
  public void run() {
    System.out.println("Connecting to server " + ServerInfo.info());
    try(Socket s = new Socket(ServerInfo.ip, ServerInfo.port)) {
      socket = s;
      System.out.println("Connection succeeds.");
      Team team = Team.receive(socket);
      ai.setTeam(team);
      ai.teamComposition().send(socket);
      System.out.println("Team composition sent.");
      receiveUID();
      System.out.println("UID received: " + uid);
      receiveArena();
      ai.setArena(arena);
      System.out.println("Arena received\n" + arena);
    }
    catch (IOException e) {
      System.err.println(e);
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
    uid = (Integer) rawUID;
    ai.setUID(uid);
  }

  private void receiveArena() throws IOException {
    arena.receive(socket);
  }
}
