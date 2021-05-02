package lol.game;

import java.io.Serializable;

public class Champion implements Serializable {
  private String name;

  private Champion(String name) {
    this.name = name;
  }

  public static Champion makeArcher() {
    return new Champion("Archer");
  }

  public static Champion makeWarrior() {
    return new Champion("Warrior");
  }

  public static Champion make(String name) {
    switch(name) {
      case "Archer": return makeArcher();
      case "Warrior": return makeWarrior();
      default: throw new RuntimeException("The champion " + name + " does not exist.");
    }
  }

  public String name() {
    return name;
  }

  @Override public String toString() {
    return name();
  }
}