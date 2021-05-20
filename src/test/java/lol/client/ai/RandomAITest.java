package lol.client.ai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
 
class RandomAITest{

	@BeforeEach
	Turn turn = new Turn();

	@Test
	public Turn testChampionSelect() {
    String championName = Warrior;
    assertTrue(Nexus.BLUE);

	}
	@Test
	public void testVisitGrass(int x, int y) {
          Integer == 9;
          assertTrue(turn.registerAction(new Move(teamID, id, x, y)));
    } 
 	@Test
    public void testVisitNexus(Nexus nexus) {
          teamID == 1234567;
          nexus.teamOfNexus != 1234567;
          assertTrue(turn.registerAction(new Attack(teamID, id, nexus.x(), nexus.y())));
          }
}