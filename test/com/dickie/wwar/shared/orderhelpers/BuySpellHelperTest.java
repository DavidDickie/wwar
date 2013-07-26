package com.dickie.wwar.shared.orderhelpers;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dickie.wwar.server.GameEngine;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.Player;

public class BuySpellHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	private static String tests = "Buy Spell";

	@Test
	public void test() {
		TestSetUp tsu = new TestSetUp();
		GameEngine ge = tsu.setup(tests);
		Player p = ge.getGame().getPlayer("Player1");
		p.setGold(3);
		p.getKnownSpells().clear();
		assertTrue("Player has no spells", p.getKnownSpells().size() == 0);
		Order o = new Order();
		o.setPurchasedSpell("Vision");
		tsu.executeOrder(tests, o, ge);
		assertTrue("Player has one spell", p.getKnownSpells().size() == 1);
		assertTrue("Player has vision", p.getKnownSpells().get(0).name.equals("Vision"));
	}

}
