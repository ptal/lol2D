package lol.game;

import java.util.*;

// The battlefield is the place where teams compete.
// It is a square grid with ground tiles (such as grass and rock), and destructible entities (such as champion and nexus).
// The overlapping of ground tiles with destructible entities forms the state of the battlefield.
// When a destructible entity is destroyed, the ground tile is shown instead.
// The coordinate (0, 0) is the top left corner of the battlefield.
public class Battlefield {
  public static enum GroundTile {
    GRASS,
    ROCK,
    TREE;
    public static GroundTile fromASCII(char c) {
      switch(c) {
        case '~': return GroundTile.GRASS;
        case '*': return GroundTile.ROCK;
        case '|': return GroundTile.TREE;
        default: throw new RuntimeException("No ground tile with the representation `" + c + "`.");
      }
    }

    public static boolean walkable(GroundTile groundTile) {
      switch(groundTile) {
        case GRASS: return true;
        case ROCK: return false;
        case TREE: return false;
        default: throw new RuntimeException("Missing GroundTile case in walkable.");
      }
    }


    public static char stringOf(GroundTile groundTile) {
      switch(groundTile) {
        case GRASS: return '~';
        case ROCK: return '*';
        case TREE: return '|';
        default: throw new RuntimeException("Missing GroundTile case in stringOf.");
      }
    }
  }

  // /!\ NOTE: Accessing position (x, y) is done with reversed indices ground[y][x].
  private GroundTile[][] ground;
  private Optional<Destructible>[][] battlefield;
  private ArrayList<Nexus> nexuses;
  private ArrayList<Tower> towers;
  private ArrayList<Monster> monsters;

  // Initialize a battlefield with a ground tiles map.
  // See `ASCIIBattlefieldBuilder` for a class initializing the battlefield using ASCII map.
  @SuppressWarnings("unchecked")
  public Battlefield(GroundTile[][] ground) {
    this.ground = ground;
    battlefield = new Optional[height()][width()];
    for(int y = 0; y < height(); ++y) {
      for(int x = 0; x < width(); ++x) {
        battlefield[y][x] = Optional.empty();
      }
    }
    nexuses = new ArrayList<>();
    nexuses.add(new Nexus(0));
    nexuses.add(new Nexus(1));

    towers = new ArrayList<>();
    towers.add(new Tower(0));
    towers.add(new Tower(1));

    monsters = new ArrayList<>();
    monsters.add(Monster.makeDragon());
    monsters.add(Monster.makeNashor());
  }

  public int width() {
    return ground[0].length;
  }

  public int height() {
    return ground.length;
  }

  public GroundTile groundAt(int x, int y) {
    return ground[y][x];
  }

  public Nexus nexusOf(int teamID) {
    return nexuses.get(teamID);
  }

  public Tower towerOf(int teamID) {
    return towers.get(teamID);
  }

  public Monster monsterOf(int monsterID) {
    return monsters.get(monsterID);
  }

  public int numberOfTeams() {
    return nexuses.size();
  }

  public boolean allNexusAlive() {
    for(Nexus nexus : nexuses) {
      if(!nexus.isAlive()) {
        return false;
      }
    }
    return true;
  }

  // We can place something at (x, y) if:
  //   * The ground tile at (x, y) is walkable.
  //   * No other destructible is present at (x, y).
  public boolean canPlaceAt(int x, int y) {
    return GroundTile.walkable(ground[y][x]) && !battlefield[y][x].isPresent();
  }

  // Place a destructible object on the battlefield *for the first time*.
  // To move a destructible object, use `moveTo`.
  // The move is allowed (return `true`) if `canPlaceAt(x,y)`.
  // Otherwise no action is performed.
  public boolean placeAt(Destructible d, int x, int y) {
    if(canPlaceAt(x, y)) {
      battlefield[y][x] = Optional.of(d);
      d.place(x, y);
      return true;
    }
    return false;
  }

  // Move a destructible object on the battlefield if `placeAt(d, x, y)` succeeds.
  public boolean moveTo(Destructible d, int x, int y) {
    int oldX = d.x();
    int oldY = d.y();
    if(placeAt(d, x, y)) {
      battlefield[oldY][oldX] = Optional.empty();
      return true;
    }
    return false;
  }

  public void destroy(Destructible d) {
      battlefield[d.y()][d.x()] = Optional.empty();
  }

  // Visit a tile using the visitor.
  // Do nothing if x or y is out of bounds.
  public void visit(int x, int y, TileVisitor visitor) {
    if(x >= width() || x < 0 || y >= height() || y < 0) {
      return;
    }
    if(!battlefield[y][x].isPresent()) {
      visitor.visitGround(ground[y][x], x, y);
    }
    else {
      battlefield[y][x].get().accept(visitor);
    }
  }

  @Override public String toString() {
    StringBuilder map = new StringBuilder();
    // The following TileVisitor construction is called an "anonymous class", check it out :-)
    new BattlefieldTraversal(this).visitFullMap(new TileVisitor(){
      void newline(int x) {
        if(x == width() - 1) {
          map.append('\n');
        }
      }

      @Override public void visitGround(GroundTile groundTile, int x, int y) {
        map.append(GroundTile.stringOf(groundTile));
        newline(x);
      }

      @Override public void visitChampion(Champion c) {
        map.append('C');
        newline(c.x());
      }

      @Override public void visitTower(Tower t) {
        switch(t.teamOfTower()) {
          case Nexus.BLUE: map.append('b'); break;
          case Nexus.RED: map.append('r'); break;
          default: throw new RuntimeException("Unknown Tower Color in Battlefield.toString");
        }
        newline(t.x());
      }

      @Override public void visitMonster(Monster monster) {
        switch(monster.monsterID()) {
          case Monster.DRAGON: map.append('d'); break;
          case Monster.NASHOR: map.append('n'); break;
          default: throw new RuntimeException("Unknown Monster in Battlefield.tower");
        }
        newline(monster.x());
      }

      @Override public void visitNexus(Nexus n) {
        switch(n.teamOfNexus()) {
          case Nexus.BLUE: map.append('B'); break;
          case Nexus.RED: map.append('R'); break;
          default: throw new RuntimeException("Unknown Nexus Color in Battlefield.toString");
        }
        newline(n.x());
      }
    });
    return map.toString();
  }
}
