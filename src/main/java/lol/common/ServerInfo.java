package lol.common;

public class ServerInfo {
  public static final int port = 3334;
  public static final String ip = "localhost";

  public static String info() {
    return ip + ":" + port;
  }
}
