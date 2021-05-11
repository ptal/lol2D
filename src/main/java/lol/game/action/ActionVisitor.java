package lol.game.action;

public interface ActionVisitor {
  public void visitSpawn(int teamID, int championID, int x, int y);
  public void visitMove(int teamID, int championID, int x, int y);
  public void visitAttack(int teamID, int championID, int x, int y);
  public void visitChampionSelect(int teamID, String championName);
}