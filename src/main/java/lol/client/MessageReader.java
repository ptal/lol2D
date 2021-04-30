package lol.client;

import java.io.*;
import java.net.*;

public class MessageReader extends Thread {
  private final Socket socket;
  private final Client client;
  private boolean quit = false;

  public MessageReader(Socket socket, Client client) {
    this.socket = socket;
    this.client = client;
  }

  @Override
  public void run() {
    try (BufferedReader in = new BufferedReader(
          new InputStreamReader(socket.getInputStream()))
    ){
      readerLoop(in);
    }
    catch(IOException e) {
      System.err.println(e);
    }
  }

  @Override
  public void interrupt() {
    super.interrupt();
    try {
      socket.close();
    } catch(IOException e) {}
  }

  private void readerLoop(BufferedReader in) throws IOException {
    String msg;
    while(!quit && (msg = in.readLine()) != null) {
      parseMessage(msg);
    }
  }

  private void parseMessage(String msg) {
    switch (msg) {
      case "\\shutdown":
        System.out.println("The server has been shutted down. Press [enter] to quit.");
        quit();
        break;
      case "\\quit":
        System.out.println("A client has quit.");
        break;
      default:
        System.out.println("\n> " + msg);
        break;
    }
    System.out.print("> ");
  }

  private void quit() {
    quit = true;
    client.interrupt();
  }
}
