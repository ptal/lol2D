package lol.game;

import java.util.*;
import java.io.*;
import java.net.*;
import lol.common.*;

public class Team implements Serializable {
  private ArrayList<Champion> champions;

  public Team() {
    champions = new ArrayList<>();
  }

  public void addChampion(Champion c) {
    champions.add(c);
  }

  public void send(Socket socket) throws IOException {
    OutputStream outputStream = socket.getOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
    objectOutputStream.writeObject(this);
  }

  public static Team receive(Socket socket) throws IOException {
    InputStream inputStream = socket.getInputStream();
    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
    Object rawTeam = null;
    try { rawTeam = objectInputStream.readObject(); } catch(Exception e) {}
    if(!(rawTeam instanceof Team)) {
      throw new BadProtocolException("a team composition of type `Team`");
    }
    return (Team) rawTeam;
  }

  @Override public String toString() {
    String data = "";
    for(Champion c : champions) {
      data += c.toString() + " ";
    }
    return data;
  }
}
