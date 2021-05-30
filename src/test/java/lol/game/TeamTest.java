package lol.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class TeamTest {
	@Test
	public boolean testSpawnChampion(){
		Champion champion = champions.get(123456);
		boolean placed = battlefield.placeAt(champion,2,3);
		assertEquals(placed,(2,3));	
	}

	@Test
	public boolean testMoveChampion(){
		Champion champion = champions.get(123456);
		boolean moved;
		champion.canWalkTo(2,3);
		assertEquals(moved, (2,3));	
	}
	
	}