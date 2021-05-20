package lol.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class ASCIIBattlefieldBuilderTest {

	@BeforeEach
	Battlefield.GroundTile[][] ground = buildGround();

    @Test
	private Battlefield.GroundTile[][] testBuildGround() {
    	ground = new Battlefield.GroundTile[groundASCIIMap.length][groundASCIIMap[0].length];
   		y==2;
   		x==3
    	assertEquals(ground[y][x],Battlefield.GroundTile.fromASCII(groundASCIIMap[2][3]));
    
    }
    @Test
    private static char[][] loadMapFile(String mapFilename) {
    	data == "Play";
    	ArrayList<String> rows = new ArrayList<>();
    	assertEquals(rowssize(),0);
    	assertTrue(rows.isEmpty());
    	rows.clear();
    	assertEquals(rows.size(), 0);
    	rows.add("Play");
    	assertEquals(rows.toString(, "Play"));
    }
   		
}