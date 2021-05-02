package lol.game;

import java.util.*;
import java.io.*;
import java.net.*;
import lol.common.*;

public class Arena implements Serializable {
  private ArrayList<Team> teams;

  public Arena() {
    teams = new ArrayList<>();
  }

  public void addTeam(Team team) {
    teams.add(team);
  }

  public void send(Socket socket) throws IOException {
    OutputStream outputStream = socket.getOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
    objectOutputStream.writeObject(this);
  }

  public static Arena receive(Socket socket) throws IOException {
    InputStream inputStream = socket.getInputStream();
    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
    Object rawArena = null;
    try { rawArena = objectInputStream.readObject(); } catch(Exception e) {}
    if(!(rawArena instanceof Arena)) {
      throw new BadProtocolException("an arena of type `Arena`.");
    }
    return (Arena) rawArena;
  }

  @Override public String toString() {
    String data = "Arena:\n";
    data += "  Teams:\n";
    for(int i = 0; i < teams.size(); ++i) {
      data += "    " + i + ": " + teams.get(i).toString() + "\n";
    }
    return data;
  }
}