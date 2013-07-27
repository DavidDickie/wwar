package com.dickie.wwar.shared.orderhelpers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dickie.wwar.server.GameEngine;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.Player;

public class MoveOrderHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	private static String tests = "Move";

	@Test
	public void test() {
		TestSetUp tsu = new TestSetUp();
		GameEngine ge = tsu.setup(tests);
		Player p = ge.getGame().getPlayer("Player1");
		Location loc = ge.getGame().getConnectedLocations(p.getLocation()).get(0);
		Order o = new Order();
		o.setLocation(loc);
		tsu.executeOrder(tests, o, ge);
		assertTrue("Player did not move to location " + loc.getName(), p.getLocation().getName().equals(loc.getName()));
	}

}
