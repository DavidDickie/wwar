package com.dickie.wwar.shared.orderhelpers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dickie.wwar.server.GameEngine;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.Player;

public class IncreasePassthroughHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	private static String tests = "Increase Passthrough";

	@Test
	public void test() {
		TestSetUp tsu = new TestSetUp();
		GameEngine ge = tsu.setup(tests);
		Player p = ge.getGame().getPlayer("Player1");
		p.setDepth(p.getDepth()+1);
		int depth = p.getPassthrough();
		Order o = new Order();
		tsu.executeOrder(tests, o, ge);
		assertTrue("Passthrough is increased by one", p.getPassthrough() == depth + 1);
	}

}
