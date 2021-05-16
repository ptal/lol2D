package lol.game;

import java.util.*;
import java.io.*;

// Initialize the battlefield with the given textual battlefield.
// The textual battlefield is an ASCII representation:
//   - '~' is a grass tile (groundASCIIMap).
//   - '*' is a rock tile (groundASCIIMap).
//   - '|' is a tree tile (groundASCIIMap).
//   - 'B' is a blue Nexus tile (destructibleASCIIMap).
//   - 'R' is a red Nexus tile (destructibleASCIIMap).
//   - 'b' is a blue Tower tile (destructibleASCIIMap).
//   - 'r' is a red Tower tile (destructibleASCIIMap).
//   - 'd' is a dragon monster tile (destructibleASCIIMap).
//   - 'n' is a nashor monster tile (destructibleASCIIMap).
//   - '.' indicates no destructible is present there (destructibleASCIIMap).
public class ASCIIBattlefieldBuilder {
  private Battlefield battlefield;
  private char[][] groundASCIIMap;
  private char[][] destructibleASCIIMap;
  private Battlefield.GroundTile[][] ground;

  public Battlefield build() {
    groundASCIIMap = loadMapFile("/maps/ground.map");
    destructibleASCIIMap = loadMapFile("/maps/destructible.map");
    verifyMaps();
    Battlefield.GroundTile[][] ground = buildGround();
    battlefield = new Battlefield(ground);
    buildDestructible();
    return battlefield;
  }

  private Battlefield.GroundTile[][] buildGround() {
    ground = new Battlefield.GroundTile[groundASCIIMap.length][groundASCIIMap[0].length];
    for(int y = 0; y < ground.length; ++y) {
      for(int x = 0; x < ground[y].length; ++x) {
        ground[y][x] = Battlefield.GroundTile.fromASCII(groundASCIIMap[y][x]);
      }
    }
    return ground;
  }

  private void buildDestructible() {
    for(int y = 0; y < ground.length; ++y) {
      for(int x = 0; x < ground[y].length; ++x) {
        initDestructibleTile(destructibleASCIIMap[y][x], x, y);
      }
    }
  }

  private void initDestructibleTile(char destructibleASCII, int x, int y) {
    String errorMsg = "Impossible to place the destructible on this tile.";
    boolean placed;
    switch(destructibleASCII) {
      case 'B':
        placed = battlefield.placeAt(battlefield.nexusOf(Nexus.BLUE), x, y);
        assert placed: errorMsg;
        break;
      case 'R':
        placed =  battlefield.placeAt(battlefield.nexusOf(Nexus.RED), x, y);
        assert placed : errorMsg;
        break;
      case 'b':
        placed = battlefield.placeAt(battlefield.towerOf(Nexus.BLUE), x, y);
        assert placed: errorMsg;
        break;
      case 'r':
        placed =  battlefield.placeAt(battlefield.towerOf(Nexus.RED), x, y);
        assert placed : errorMsg;
        break;
      case 'd':
        placed =  battlefield.placeAt(battlefield.monsterOf(Monster.DRAGON), x, y);
        assert placed : errorMsg;
        break;
      case 'n':
        placed = battlefield.placeAt(battlefield.monsterOf(Monster.NASHOR), x, y);
        assert placed : errorMsg;
        break;
      case '.': break;
      default:
        throw new RuntimeException("No destructible object with the representation `" + destructibleASCII + "`.");
    }
  }

  private static char[][] loadMapFile(String mapFilename) {
    InputStream mapfile = ASCIIBattlefieldBuilder.class.getResourceAsStream(mapFilename);
    if(mapfile == null) {
      throw new RuntimeException("The map file `" + mapFilename + "` could not be found.");
    }
    Scanner scanner = new Scanner(mapfile);
    ArrayList<String> rows = new ArrayList<>();
    while (scanner.hasNextLine()) {
      String data = scanner.nextLine();
      if(!data.isEmpty()) {
        rows.add(data);
      }
    }
    char[][] map = new char[rows.size()][];
    for(int i = 0; i < rows.size(); ++i) {
      map[i] = rows.get(i).toCharArray();
    }
    scanner.close();
    return map;
  }

  private void verifyMaps() {
    String errorMap = "The ground and destructible maps must have the same size.";
    assert groundASCIIMap.length == destructibleASCIIMap.length : errorMap;
    assert groundASCIIMap.length > 0 && groundASCIIMap[0].length > 0: "The dimensions of the map must be greater than 0.";
    int height = groundASCIIMap[0].length;
    for(int i = 0; i < groundASCIIMap.length; ++i) {
      assert groundASCIIMap[i].length == destructibleASCIIMap[i].length : errorMap;
      assert groundASCIIMap[i].length == height : "The maps must be rectangle (all rows must have the same length).";
    }
  }
}
