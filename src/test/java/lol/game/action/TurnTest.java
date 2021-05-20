package lol.game.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
 
class TurnTest {

	@Test
	public void testAccept(ActionVisitor visitor) {
    actions = 1, 2;
    action = 1, 2;
    assertTrue(action.accept(visitor));
	}

	@Test
	public static Turn testReceive(Socket socket){
		Object rawTurn = null;
		assertTrue(objectInputStream.readObject())
	}
}