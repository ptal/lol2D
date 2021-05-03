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

  // /!\ NOTE: Accessing position (x, y) is done with reversed indices ground[y][x].
  private GroundTile[][] ground;
  private Optional<Destructible>[][] battlefield;

  // Initialize a battlefield with a ground tiles map.
  // See `ASCIIBattlefieldBuilder` for a class initializing the battlefield using ASCII map.
  @SuppressWarnings("unchecked")
  public Battlefield(GroundTile[][] ground) {
    this.ground = ground;
    battlefield = new Optional[height()][width()];
    for(int i = 0; i < height(); ++i) {
      for(int j = 0; j < width(); ++j) {
        battlefield[i][j] = Optional.empty();
      }
    }
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

  // We can place something at (x, y) if:
  //   * The ground tile at (x, y) is walkable.
  //   * No other destructible is present at (x, y).
  public boolean canPlaceAt(int x, int y) {
    return GroundTile.walkable(ground[y][x]) && battlefield[y][x].isEmpty();
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
      return false;
    }
    return true;
  }

  // Visit the battlefield map from top left to bottom right in order.
  // If a destructible is present on the map, we visit it, otherwise we visit the ground tile.
  public void visitFullMap(TileVisitor visitor) {
    for(int y = 0; y < width(); ++y) {
      for(int x = 0; x < height(); ++x) {
        if(battlefield[y][x].isEmpty()) {
          visitor.visitGround(ground[y][x], x, y);
        }
        else {
          battlefield[y][x].get().accept(visitor, x, y);
        }
      }
    }
  }

  public String toString() {
    StringBuilder map = new StringBuilder();
    // The following TileVisitor construction is called an "anonymous class", check it out :-)
    visitFullMap(new TileVisitor(){
      void newline(int x) {
        if(x == width() - 1) {
          map.append('\n');
        }
      }

      public void visitGround(GroundTile groundTile, int x, int y) {
        map.append(GroundTile.stringOf(groundTile));
        newline(x);
      }

      public void visitChampion(Champion c, int x, int y) {
        map.append('C');
        newline(x);
      }

      public void visitNexus(Nexus n, int x, int y) {
        switch(n.color()) {
          case BLUE: map.append('B'); break;
          case RED: map.append('R'); break;
          default: throw new RuntimeException("Unknown Nexus Color in Battlefield.toString");
        }
        newline(x);
      }
    });
    return map.toString();
  }
}
