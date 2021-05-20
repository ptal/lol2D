package lol.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class DestructibleTest {
	@Test
	public void testReviveAt(int x, int y) {
    currentHP = initialHP == 3;
    assertEquals(currentHP, initialHP);
    }