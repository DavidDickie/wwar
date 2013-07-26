package com.dickie.wwar.shared.orderhelpers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dickie.wwar.server.GameEngine;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.Player;

public class CreateGolumHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	private static String tests = "Create Golem";

	@Test
	public void test() {
		TestSetUp tsu = new TestSetUp();
		GameEngine ge = tsu.setup(tests);
		Player p = ge.getGame().getPlayer("Player1");
		Order o = new Order();
		Location theLoc = null;
		for (Location loc : ge.getGame().getLocations()){
			if (loc.getType().equals(Location.LocType.Mystical)){
				theLoc = loc;
				p.setLocation(loc);
				o.setLocation(loc);
				break;
			}
		}
		tsu.executeOrder(tests, o, ge);
		assertTrue("Golum not created", ge.getGame().getMoversAtLocation(theLoc).size() == 2);
	}

}
