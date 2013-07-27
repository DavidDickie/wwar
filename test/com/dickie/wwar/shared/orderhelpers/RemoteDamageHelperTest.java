package com.dickie.wwar.shared.orderhelpers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dickie.wwar.server.GameEngine;
import com.dickie.wwar.shared.Golum;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.Player;

public class RemoteDamageHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	private static String tests = "Remote Damage";

	@Test
	public void test() {
		//test armor
		TestSetUp tsu = new TestSetUp();
		GameEngine ge = tsu.setup(tests);
		Player p = ge.getGame().getPlayer("Player1");
		Player damagePlayer = ge.getGame().getPlayer("Player2");
		damagePlayer.setArmor(1);
		tsu.colocatePlayers(p, damagePlayer, ge.getGame());
		Golum g = ge.getGame().getGolums().get(ge.getGame().getGolums().size() - 1);
		tsu.colocatePlayerAndGolem(p, g, ge.getGame());
		int depth = damagePlayer.getDepth() - damagePlayer.getDamage();
		Order o = new Order();
		tsu.executeOrder(tests, o, ge);
		assertTrue("Depth does not change", damagePlayer.getDepth() - damagePlayer.getDamage() == depth);
		assertTrue("Armor is decreased by one", damagePlayer.getArmor() == 0);
		assertTrue("Golum destroyed", !ge.getGame().getGolums().contains(g));
	}

}
