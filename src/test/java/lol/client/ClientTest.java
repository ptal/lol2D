package lol.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ClientTest {

	@Test
	void testGameLoop() {
		Arena arena = new Arena();
		arena.numberOfTeams() == 2;
		assertEquals(arena.numberFfTeams(), 2)
	}
}
