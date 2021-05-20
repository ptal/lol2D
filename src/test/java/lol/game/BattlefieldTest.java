package lol.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class BattlefieldTest {
	@Test
	public boolean testPlaceAt(Destructible d, int x, int y) {
   		x==3; y==2;
      	assertEquals(d.place(x, y),battlefield[3],[2]);
      	
    }
    @Test
    public boolean testPlaceAt(Destructible d, int x, int y) {
    	x==3; y==2;
      	assertEquals(d.place(x, y),battlefield[3],[2]);
      	

    }
    @Test
    public void visitAdjacent(int x, int y, int radius, TileVisitor visitor) {
    	r == 3;
    	xCorner == 6;
    	yCorner == 8;
    	assertEquals(xCorner[1], 18);
    	assertEquals(yCorner[1], 24);
    }