package lol.server;

import java.net.*;
import java.io.*;
import java.util.*;
import lol.game.*;
import lol.game.action.*;

public class Player {
  private final Socket socket;
  private final Server server;
  // The UID of a player is synchronized with the indices of `teams` in arena.
  private Integer uid;
  private static int UID = 0;

  public Player(Socket socket, Server server) {
    this.socket = socket;
    this.server = server;
    this.uid = UID++;
  }

  public Turn askTurn() throws IOException {
    return Turn.receive(socket);
  }

  public void sendTurn(Turn turn) throws IOException {
    turn.send(socket);
  }

  public void sendTurns(ArrayList<Turn> turns) throws IOException {
    for(Turn turn : turns) {
      sendTurn(turn);
    }
  }

  public void sendUID() throws IOException {
    OutputStream outputStream = socket.getOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
    objectOutputStream.writeObject(this.uid);
  }
}
