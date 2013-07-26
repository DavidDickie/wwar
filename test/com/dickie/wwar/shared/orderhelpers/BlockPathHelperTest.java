package com.dickie.wwar.shared.orderhelpers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dickie.wwar.server.GameEngine;
import com.dickie.wwar.shared.Connection;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderEngine;
import com.dickie.wwar.shared.Player;

public class BlockPathHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	private static String tests = "Block Path";

	@Test
	public void test() {
		TestSetUp tsu = new TestSetUp();
		GameEngine ge = tsu.setup(tests);
		Player p = ge.getGame().getPlayer("Player1");
		Order o = new Order();
		//Connection c = p.getLocation().getConnections(ge.getGame()).get(0);
		Connection c = tsu.getRandomConnection();
		o.setConnection(c);
		tsu.executeOrder(tests, o, ge);
		assertTrue("Path is blocked", c.isBlocked());
		p.setLocation(c.getStartLocation());
		o = new Order();
		o.setOrderType(Order.OrderType.Magic);
		o.setSpell("Walk");
		o.setPlayer(p);
		o.setLocation(c.getEndLocation());
		OrderEngine oe = new OrderEngine();
		oe.setGame(ge.getGame());
		String response = oe.executeOrder(o);
		assertTrue("Player could not move; "+ response, p.getLocation().equals(c.getStartLocation()));
		System.out.println(response);
	}

}
