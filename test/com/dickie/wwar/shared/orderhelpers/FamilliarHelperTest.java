package com.dickie.wwar.shared.orderhelpers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dickie.wwar.server.GameEngine;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.Player;

public class FamilliarHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	private static String tests = "Familiar";

	@Test
	public void test() {
		TestSetUp tsu = new TestSetUp();
		GameEngine ge = tsu.setup(tests);
		Player p = ge.getGame().getPlayer("Player1");
		Order o = new Order();
		tsu.executeOrder(tests, o, ge);
		boolean hasFam = false;
		for (String player : p.getLocation().getCronies()){
			if (player.equals(p.getName())){
				hasFam = true;
				break;
			}
		}
		assertTrue("Location has familiar", hasFam);
	}

}
