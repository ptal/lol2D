package lol.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class ServerTest {

	@Test
	public void testRun(){
		players.size() == 1; 
		battlefield.numberOfTeams() == 2;
		assertTrue(waitNewPlayer())
	}

	@Test
	public testOfEndGameMessage(){
      Nexus nexus = battlefield.nexusOf(2);
      nexus.isAlive = true 
      assertTrue(System.out.println("WINNER: " + nexus));
           
	}

	
}