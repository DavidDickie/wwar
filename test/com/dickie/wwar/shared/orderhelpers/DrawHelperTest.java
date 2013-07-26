package com.dickie.wwar.shared.orderhelpers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dickie.wwar.server.GameEngine;
import com.dickie.wwar.shared.Card;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.Player;

public class DrawHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	private static String tests = "Draw";

	@Test
	public void test() {
		TestSetUp tsu = new TestSetUp();
		GameEngine ge = tsu.setup(tests);
		Player p = ge.getGame().getPlayer("Player1");
		p.addCardToDraw("Air");
		p.addCardToDraw("Water");
		p.setTempPassThrough(1);
		p.setPassthrough(1);
		p.setPassThroughPoints();
		Order o = new Order();
		o.setCardType(Card.CardType.Air);
		tsu.executeOrder(tests, o, ge, false);
		o.setCardType(Card.CardType.Water);
		tsu.executeOrder(tests, o, ge, false);
		assertTrue("two cards in hand", p.getHand().size() == 2);
		assertTrue("Temp passthrough points used", p.getTempPassThrough() == 0);
	}

}
