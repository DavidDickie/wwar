package com.dickie.wwar.shared.orderhelpers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dickie.wwar.server.GameEngine;
import com.dickie.wwar.shared.Golum;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.Player;

public class DamageHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	private static String tests = "Damage";
	@Test
	public void test() {
		TestSetUp tsu = new TestSetUp();
		GameEngine ge = tsu.setup(tests);
		Player p = ge.getGame().getPlayer("Player1");
		Player damagePlayer = ge.getGame().getPlayer("Player2");
		tsu.colocatePlayers(p, damagePlayer, ge.getGame());
		int depth = damagePlayer.getDepth() - damagePlayer.getDamage();
		Order o = new Order();
		o.setDamagePlayer(damagePlayer);
		tsu.executeOrder(tests, o, ge);
		assertTrue("Depth is decreased by one", damagePlayer.getDepth() - damagePlayer.getDamage() == depth-1);
		
		//test armor
		ge = tsu.setup(tests);
		p = ge.getGame().getPlayer("Player1");
		damagePlayer = ge.getGame().getPlayer("Player2");
		damagePlayer.setArmor(1);
		tsu.colocatePlayers(p, damagePlayer, ge.getGame());
		depth = damagePlayer.getDepth() - damagePlayer.getDamage();
		o = new Order();
		o.setDamagePlayer(damagePlayer);
		tsu.executeOrder(tests, o, ge);
		assertTrue("Depth does not change", damagePlayer.getDepth() - damagePlayer.getDamage() == depth);
		assertTrue("Armor is decreased by one", damagePlayer.getArmor() == 0);
		
		// test Golem
		
		ge = tsu.setup(tests);
		p = ge.getGame().getPlayer("Player1");
		Golum g = ge.getGame().getGolums().get(ge.getGame().getGolums().size() - 1);
		tsu.colocatePlayerAndGolem(p, g, ge.getGame());
		o.setDamagePlayer(null);
		o.setDamageGolum(g);
		tsu.executeOrder(tests, o, ge);
		assertTrue("Golum destroyed", !ge.getGame().getGolums().contains(g));
	}

}
