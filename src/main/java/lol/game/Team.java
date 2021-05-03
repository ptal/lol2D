package lol.game;

import java.util.*;
import java.io.*;
import java.net.*;
import lol.common.*;

public class Team implements Serializable {
  private ArrayList<Champion> champions;
  private Nexus nexus;

  public Team(Nexus nexus) {
    this.champions = new ArrayList<>();
    this.nexus = nexus;
  }

  public void addChampion(Champion c) {
    champions.add(c);
  }

  // Simple boolean holder for `placed` in spawnChampions ("local variables referenced from an inner class must be final").
  class BooleanPlaceholder {
    BooleanPlaceholder(boolean b) { this.b = b; }
    public boolean b;
  }

  public void spawnChampions(Battlefield battlefield) {
    for(Champion champion : champions) {
      final BooleanPlaceholder placed = new BooleanPlaceholder(false);
      battlefield.visitAdjacent(nexus.x(), nexus.y(), 1, new TileVisitor(){
        public void visitGrass(int x, int y) {
          if(!placed.b) {
            battlefield.placeAt(champion, x, y);
            placed.b = true;
          }
        }
      });
      if(!placed.b) {
        throw new RuntimeException("Cannot place the champion `" + champion
          + "` due to all spawned spots next to the Nexus occupied.");
      }
    }
  }

  public Nexus.Color color() {
    return nexus.color();
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
