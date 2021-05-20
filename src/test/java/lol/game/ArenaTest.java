package lol.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class ArenaTest {
	@Test
 	public void visitSpawn(int teamID, int championID, int x, int y) {
 		ApplyAction championsWhoActed = new ApplyAction;
 		assertEquals(championsWhoActed.size(),0);
    	championsWhoActed.add(123456);
   	 	assertEquals(championsWhoActed.toString(), "[123456]");
	}
	@Test
	public String testToString() {
    String data = "Arena" + "Teams" ;
    i == 3;
    assertEquals(data, "Arena Teams 3");

    }	