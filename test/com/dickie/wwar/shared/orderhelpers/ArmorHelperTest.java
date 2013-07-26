/**
 * 
 */
package com.dickie.wwar.shared.orderhelpers;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dickie.wwar.server.GameEngine;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.Player;

/**
 * @author Dave
 *
 */
public class ArmorHelperTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	private static String tests = "Armor";

	@Test
	public void test() {
		TestSetUp tsu = new TestSetUp();
		GameEngine ge = tsu.setup(tests);
		Player p = ge.getGame().getPlayer("Player1");
		int armor = p.getArmor();
		Order o = new Order();
		tsu.executeOrder(tests, o, ge);
		assertTrue("Armor is increased by one", p.getArmor() == armor + 1);
	}

}
