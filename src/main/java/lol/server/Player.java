package lol.server;

import java.net.*;
import java.io.*;
import lol.game.*;

public class Player {
  private final Socket socket;
  private final Server server;
  private Team team;
  // The UID of a player is synchronized with the indices of `teams` in arena.
  private Integer uid;
  private static int UID = 0;

  public Player(Socket socket, Server server) {
    this.socket = socket;
    this.server = server;
    this.uid = UID++;
  }

  public Team askTeamComposition() throws IOException {
    if(team != null) {
      throw new RuntimeException("The team composition is already known.");
    }
    team = Team.receive(socket);
    return team;
  }

  public void sendUID() throws IOException {
    OutputStream outputStream = socket.getOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
    objectOutputStream.writeObject(this.uid);
  }

  public void sendArena(Arena arena) throws IOException {
    arena.send(socket);
  }
}
