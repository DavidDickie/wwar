/**
 * 
 */
package com.dickie.wwar;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dickie.wwar.server.GameEngine;
import com.dickie.wwar.shared.Card;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.Player;
import com.dickie.wwar.shared.orderhelpers.ArmorHelper;

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

	@Test
	public void test() {
		System.out.println("Starting a ArmorHelper Test");
		TestSetUp tsu = new TestSetUp();
		GameEngine ge = tsu.generateTestDb();
		Player p = ge.getGame().getPlayer("Player1");
		int armor = p.getArmor();
		tsu.givePlayerAllCards(p);
		Order o = new Order();
		o.setSpell("Armor");
		o.setPlayer(p);
		o.setOrderType(Order.OrderType.Magic);
		ArrayList<Order> orders = new ArrayList<Order>();
		orders.add(o);
		ge.doPlayerOrders("Player1", orders);
		if (p.getArmor() != armor+1){
			throw new RuntimeException("Fail");
		}
		System.out.println("ArmorHelper test passed");
	}

}
