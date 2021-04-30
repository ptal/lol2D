package lol.client;

import lol.common.ServerInfo;
import java.net.*;
import java.io.*;
import java.util.Scanner;

/* Each messages entered by the user (via the console) is sent
*  to the server. We wait a response from the server before we
*  ask the user for input again.
*  The client exits when the user inputs "\quit", this command is
*  sent to the server for a clean shutdown and we quit when the server
*  has responded.
*  The client can shutdown the server with "\shutdown", if he does so,
*  each connection to the server is closed.
*/

public class Client implements Runnable {
  public static void main(String[] args) {
    new Client().run();
  }

  private MessageReader reader = null;
  private Thread writerTask = Thread.currentThread();
  private PrintWriter out;

  @Override
  public void run() {
    System.out.println("Connecting to server " + ServerInfo.info());
    try (
      Socket socket = new Socket(ServerInfo.ip, ServerInfo.port);
      PrintWriter outRessource = new PrintWriter(socket.getOutputStream(), true)
    ){
      out = outRessource;
      System.out.println("Connection succeeds. Enter \"\\quit\" to exit.");
      async_reader_loop(socket);
      writer_loop(out);
    } catch (IOException ex) {
      System.err.println(ex);
    } finally {
      stopReader();
    }
  }

  // We don't need to interrupt the reader thread
  // because we already closed the socket, so it can't read
  // anymore data and will stop by itself.
  private void stopReader() {
    if(reader != null) {
      try {
        reader.join();
      } catch (InterruptedException ex) {
      }
    }
  }

  private void async_reader_loop(Socket socket) {
    reader = new MessageReader(socket, this);
    reader.start();
  }

  // If the client quits, we only know it here, so we want to notify
  // the reader that it is over.
  // On the other hand, if someone has request a shutdown, it is the
  // reader which notify the writer (in the Client class).
  private void writer_loop(PrintWriter out) {
    Scanner console = new Scanner(System.in);
    String msg = "";
    System.out.print("> ");
    while(!Thread.currentThread().isInterrupted() && !msg.equals("\\quit")) {
      msg = console.nextLine();
      out.println(msg);
    }
    if(msg.equals("\\quit")) {
      reader.interrupt();
    }
  }

  // We can't interrupt console.nextLine() so the client will only quit
  // after he entered a last sentence. The trick is to say to the user
  // "Press [enter] to quit".
  public void interrupt() {
    writerTask.interrupt();
    if (out != null) {
      out.close();
    }
  }
}
