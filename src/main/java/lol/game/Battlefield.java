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
  }

  private boolean walkable(GroundTile groundTile) {
    switch(groundTile) {
      case GRASS: return true;
      case ROCK: return false;
      default: throw new RuntimeException("Missing GroundTile case in walkable.");
    }
  }

  private GroundTile[][] ground;
  private Optional<Destructible>[][] battlefield;

  private void initTile(char groundASCII, char destructibleASCII, int x, int y) {
    ground[x][y] = GroundTile.fromASCII(groundASCII);
    switch(destructibleASCII) {
      case 'n':
        battlefield[x][y] = Optional.of(new Nexus());
        break;
      case ' ': break;
      default:
        throw new RuntimeException("No destructible object with the representation `" + destructibleASCII + "`.");
    }
  }

  private void verifyMaps(char[][] groundASCIIMap, char[][] destructibleASCIIMap) {
    String errorMap = "The ground and destructible maps must have the same size.";
    assert groundASCIIMap.length == destructibleASCIIMap.length : errorMap;
    assert groundASCIIMap.length > 0 && groundASCIIMap[0].length > 0: "The dimensions of the map must be greater than 0.";
    int height = groundASCIIMap[0].length;
    for(int i = 0; i < groundASCIIMap.length; ++i) {
      assert groundASCIIMap[i].length == destructibleASCIIMap[i].length : errorMap;
      assert groundASCIIMap[i].length == height : "The maps must be rectangle (all rows must have the same length).";
    }
  }

  // Initialize the battlefield with the given textual battlefield.
  // The textual battlefield is an ASCII representation:
  //   - '~' is a grass tile (groundASCIIMap).
  //   - '*' is a rock tile (groundASCIIMap).
  //   - 'n' is a Nexus tile (destructibleASCIIMap).
  //   - ' ' indicates no destructible are present there (destructibleASCIIMap).
  @SuppressWarnings("unchecked")
  public Battlefield(char[][] groundASCIIMap, char[][] destructibleASCIIMap) {
    verifyMaps(groundASCIIMap, destructibleASCIIMap);
    ground = new GroundTile[groundASCIIMap.length][groundASCIIMap[0].length];
    battlefield = new Optional[ground.length][ground[0].length];
    for(int i = 0; i < ground.length; ++i) {
      for(int j = 0; j < ground[i].length; ++j) {
        initTile(groundASCIIMap[i][j], destructibleASCIIMap[i][j], i, j);
      }
    }
  }

  // Move a destructible object on the battlefield.
  // The move is allowed (return `true`) if:
  //   * The ground tile at (x, y) is walkable.
  //   * No other destructible is present at (x, y).
  // Otherwise no action is performed.
  public boolean moveTo(Destructible d, int x, int y) {
    if(walkable(ground[x][y]) && battlefield[x][y].isEmpty()) {
      battlefield[x][y] = Optional.of(d);
      battlefield[d.x()][d.y()] = Optional.empty();
      d.place(x, y);
      return true;
    }
    return false;
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
}
