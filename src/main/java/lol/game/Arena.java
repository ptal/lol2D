package lol.game;

import java.util.*;
import java.io.*;
import java.net.*;
import lol.common.*;

public class Arena {
  private ArrayList<Team> teams;
  private Battlefield battlefield;

  public Arena(Battlefield battlefield) {
    teams = new ArrayList<>();
    this.battlefield = battlefield;
  }

  public void addTeam(Team team) {
    teams.add(team);
  }

  public void spawnChampions() {
    for(Team team : teams) {
      team.spawnChampions(battlefield);
    }
  }

  public Team teamOf(int uid) {
    return teams.get(uid);
  }

  public void send(Socket socket) throws IOException {
    OutputStream outputStream = socket.getOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
    objectOutputStream.writeObject(teams);
  }

  @SuppressWarnings("unchecked")
  public void receive(Socket socket) throws IOException {
    InputStream inputStream = socket.getInputStream();
    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
    Object rawTeams = null;
    try { rawTeams = objectInputStream.readObject(); } catch(Exception e) {}
    if(!(rawTeams instanceof ArrayList) || !(((ArrayList<Team>)rawTeams).get(0) instanceof Team)) {
      throw new BadProtocolException("teams of type `ArrayList<Team>`.");
    }
    teams = (ArrayList<Team>) rawTeams;
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