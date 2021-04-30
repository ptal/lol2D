package lol.server;

import java.net.*;
import java.io.*;

/* A connection models a channel between two entities (client and server).
*  It also implements the protocol between them in the `run` method.
*  It has the responsibility to close socket when it finishes. It is not necessary
*  to close it explicitly because closing `in` or `out` automatically close the socket
*  as well.
*/

public class Connection extends Thread {
  private final Socket socket;
  private final Server server;
  private PrintWriter out;

  public Connection(Socket socket, Server server) {
    this.socket = socket;
    this.server = server;
  }

  // For the whole story about "how to properly interrupt thread"
  // see http://www.javaspecialists.eu/archive/Issue056.html
  @Override
  public void interrupt() {
    super.interrupt();
    try {
      socket.close();
    } catch(IOException e) {}
  }

  @Override
  public void run() {
    try(
      PrintWriter outRessource = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
    ) {
      out = outRessource;
      boolean quit = false;
      while(!quit) {
        String msg = in.readLine();
        quit = parseMessage(msg);
      }
    } catch(InterruptedIOException e) {
      Thread.currentThread().interrupt(); // see interrupt() above.
    } catch (IOException io) {
      System.err.println(io);
    }
  }

  // Returns true if it was the last message of the client.
  public boolean parseMessage(String msg) {
    if(msg == null) {
      return true;
    }
    server.broadcastMsg(msg);
    if(msg.equals("\\shutdown")) {
      server.stop();
      return true;
    }
    else if(msg.equals("\\quit")) {
      server.clientQuits(this);
      return true;
    }
    return false;
  }

  // Note: there are no troubles if `out` is closed.
  // The data will just not be sent.
  public void send(String msg) {
    if(out != null) {
      out.println(msg);
    }
  }
}
