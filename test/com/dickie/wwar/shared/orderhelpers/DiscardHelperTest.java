package com.dickie.wwar.shared.orderhelpers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dickie.wwar.server.GameEngine;
import com.dickie.wwar.shared.Card;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.Player;

public class DiscardHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	private static String tests = "Discard";

	@Test
	public void test() {
		TestSetUp tsu = new TestSetUp();
		GameEngine ge = tsu.setup(tests);
		Player p = ge.getGame().getPlayer("Player1");
		p.addCardToHand(Card.CardType.Mana);
		
		Order o = new Order();
		o.setCardType(Card.CardType.Mana);
		tsu.executeOrder(tests, o, ge);
		p.addCardToHand(Card.CardType.Air);
		o.setCardType(Card.CardType.Air);
		tsu.executeOrder(tests, o, ge);
		assertTrue("Player has one gold, no cards", p.getHand().size() == 00 && p.getGold() == 1);
	}

}
