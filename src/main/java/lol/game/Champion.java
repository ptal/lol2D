package lol.game;

public class Champion {
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
  public String name() {
    return name;
  }
}