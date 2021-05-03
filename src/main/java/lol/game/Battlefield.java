package lol.game;

import java.util.*;

// The battlefield is the place where teams compete.
// It is a square grid with ground tiles (such as grass and rock), and destructible entities (such as champion and nexus).
// The overlapping of ground tiles with destructible entities forms the state of the battlefield.
// When a destructible entity is destroyed, the ground tile is shown instead.
// The coordinate (0, 0) is the top left corner of the battlefield.
public class Battlefield {
  public enum GroundTile {
    GRASS,
    ROCK;
    public static GroundTile fromASCII(char c) {
      switch(c) {
        case '~': return GroundTile.GRASS;
        case '*': return GroundTile.ROCK;
        default: throw new RuntimeException("No ground tile with the representation `" + c + "`.");
      }
    }

    public static boolean walkable(GroundTile groundTile) {
      switch(groundTile) {
        case GRASS: return true;
        case ROCK: return false;
        default: throw new RuntimeException("Missing GroundTile case in walkable.");
      }
    }

    public static char stringOf(GroundTile groundTile) {
      switch(groundTile) {
        case GRASS: return '~';
        case ROCK: return '*';
        default: throw new RuntimeException("Missing GroundTile case in stringOf.");
      }
    }
  }

  private GroundTile[][] ground;
  private Optional<Destructible>[][] battlefield;

  // Initialize a battlefield with a ground tiles map.
  // See `ASCIIBattlefieldBuilder` for a class initializing the battlefield using ASCII map.
  @SuppressWarnings("unchecked")
  public Battlefield(GroundTile[][] ground) {
    this.ground = ground;
    battlefield = new Optional[ground.length][ground[0].length];
    for(int i = 0; i < ground.length; ++i) {
      for(int j = 0; j < ground[i].length; ++j) {
        battlefield[i][j] = Optional.empty();
      }
    }
  }

  // We can place something at (x, y) if:
  //   * The ground tile at (x, y) is walkable.
  //   * No other destructible is present at (x, y).
  public boolean canPlaceAt(int x, int y) {
    return GroundTile.walkable(ground[x][y]) && battlefield[x][y].isEmpty();
  }

  // Place a destructible object on the battlefield *for the first time*.
  // To move a destructible object, use `moveTo`.
  // The move is allowed (return `true`) if `canPlaceAt(x,y)`.
  // Otherwise no action is performed.
  public boolean placeAt(Destructible d, int x, int y) {
    if(canPlaceAt(x, y)) {
      battlefield[x][y] = Optional.of(d);
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
      battlefield[oldX][oldY] = Optional.empty();
      return false;
    }
    return true;
  }

  // Visit the battlefield map from top left to bottom right in order.
  // If a destructible is present on the map, we visit it, otherwise we visit the ground tile.
  public void visitFullMap(TileVisitor visitor) {
    for(int x = 0; x < ground.length; ++x) {
      for(int y = 0; y < ground[x].length; ++y) {
        if(battlefield[x][y].isEmpty()) {
          visitor.visitGround(ground[x][y], x, y);
        }
        else {
          battlefield[x][y].get().accept(visitor, x, y);
        }
      }
    }
  }

  public String toString() {
    StringBuilder map = new StringBuilder();
    // The following TileVisitor construction is called an "anonymous class", check it out :-)
    visitFullMap(new TileVisitor(){
      void newline(int y) {
        if(y == ground[0].length - 1) {
          map.append('\n');
        }
      }

      public void visitGround(GroundTile groundTile, int x, int y) {
        map.append(GroundTile.stringOf(groundTile));
        newline(y);
      }

      public void visitChampion(Champion c, int x, int y) {
        map.append('C');
        newline(y);
      }

      public void visitNexus(Nexus n, int x, int y) {
        switch(n.color()) {
          case BLUE: map.append('B'); break;
          case RED: map.append('R'); break;
          default: throw new RuntimeException("Unknown Nexus Color in Battlefield.toString");
        }
        newline(y);
      }
    });
    return map.toString();
  }
}
