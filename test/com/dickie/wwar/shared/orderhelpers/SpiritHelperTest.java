package com.dickie.wwar.shared.orderhelpers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dickie.wwar.server.GameEngine;
import com.dickie.wwar.shared.Card;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.Player;

public class SpiritHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	private static String tests = "Spirit";

	@Test
	public void test() {
		TestSetUp tsu = new TestSetUp();
		GameEngine ge = tsu.setup(tests);
		Player p = ge.getGame().getPlayer("Player1");
		Order o = new Order();
		tsu.executeOrder(tests, o, ge);
		boolean hasSpirit = false;
		for (Card card : p.getDiscardPile()){
			if (card.getType().equals(Card.CardType.Spirit)){
				hasSpirit = true;
				break;
			}
		}
		assertTrue("Player has a spirit card", hasSpirit);
	}

}
