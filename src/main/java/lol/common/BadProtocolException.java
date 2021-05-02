package lol.common;

public class BadProtocolException extends RuntimeException {
  public BadProtocolException(String expected) {
    super("We expected to receive " + expected + " but something else was sent instead.");
  }
}