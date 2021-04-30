package lol.server;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import lol.common.ServerInfo;

/* The server is stopped whenever a client ask for it with the
*  command "\shutdown". If a client send "\quit", the client is
*  removed from the room.
*/

public class Server implements Runnable {
  // Any access or modification of the room is subject to concurrent
  // data races, so protect it with synchronized methods.
  private final ArrayList<Connection> room = new ArrayList<>();
  private final Thread acceptTask = Thread.currentThread();
  private ServerSocket server;

  public static void main(String[] args) {
    new Server().run();
  }

  @Override
  public void run() {
    System.out.println("Starting server...");
    try(ServerSocket serverRessource = new ServerSocket(ServerInfo.port)) {
      server = serverRessource;
      while(true) {
        waitNewClient();
      }
    } catch (IOException e) {
      System.err.println(e);
    }
    cleanShutdown();
    System.out.println("Server shutted down.");
  }

  private void waitNewClient() throws IOException {
    Socket socket = server.accept();
    System.out.println("New client at " + socket);
    newClient(socket);
  }

  private synchronized void newClient(Socket socket) {
    Connection connection = new Connection(socket, this);
    room.add(connection);
    connection.start();
  }

  public synchronized void broadcastMsg(String msg) {
    for(Connection c : room) {
      c.send(msg);
    }
  }

  public synchronized void clientQuits(Connection connection) {
    System.out.println(connection + ": client has quit.");
    room.remove(connection);
  }

  // Can be called from any thread. The server will be shutted down
  // as soon as possible. Note that it is just a request, it returns immediatly.
  public void stop() {
    acceptTask.interrupt();
    try {
      if(server != null) {
        server.close();
      }
    } catch(IOException e) {}
  }

  private void cleanShutdown() {
    boolean isShuttedDown = false;
    while(!isShuttedDown) {
      try {
        shutdown();
        isShuttedDown = true;
      } catch (InterruptedException ex) {
      }
    }
  }

  // Synchronized because we want to avoid interrupt if `broadcast_msg` is
  // currently running or any modification of the connections is happening.
  private synchronized void shutdown() throws InterruptedException {
    for(Connection c : room) {
      c.interrupt();
    }
    for(Connection c : room) {
      c.join();
    }
  }
}
