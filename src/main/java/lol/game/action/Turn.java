package lol.game.action;

import java.net.*;
import java.io.*;
import java.util.*;
import lol.common.*;

public class Turn implements Serializable {
  ArrayList<Action> actions;

  public Turn() {
    actions = new ArrayList<Action>();
  }

  public void registerAction(Action action) {
    actions.add(action);
  }

  public void accept(ActionVisitor visitor) {
    for(Action action : actions) {
      action.accept(visitor);
    }
  }

  public void send(Socket socket) throws IOException {
    OutputStream outputStream = socket.getOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
    objectOutputStream.writeObject(this);
  }

  @SuppressWarnings("unchecked")
  public static Turn receive(Socket socket) throws IOException {
    InputStream inputStream = socket.getInputStream();
    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
    Object rawTurn = null;
    try { rawTurn = objectInputStream.readObject(); } catch(Exception e) {}
    if(!(rawTurn instanceof Turn)) {
      throw new BadProtocolException("turn of type `Turn`.");
    }
    return (Turn) rawTurn;
  }
}