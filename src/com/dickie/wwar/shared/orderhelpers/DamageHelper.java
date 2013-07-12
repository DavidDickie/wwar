package com.dickie.wwar.shared.orderhelpers;

import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Mover;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderHelper;
import com.dickie.wwar.shared.Player;

public class DamageHelper implements OrderHelper,java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String executeOrder(Game game, boolean server, Order order) {

		Mover mover = order.getMover();
		Player dPlayer = order.getDamagePlayer();
		Location loc1 = game.getLocation(order.getMover().getLocation());
		Location loc2 = game.getLocation(dPlayer.getLocation());

		if (!game.isConnected(loc1.getName(), loc2.getName())){
			game.addMessage(order.getMover().getOwnerName(), "Location is not connected to casters", true);
			return "Location is not connected to casters";
		}

		int totDam = 1;
		String s = mover.getName() + ": " + OrderCommon.assignDamage(game,totDam, dPlayer);
		game.addMessage(mover.getOwnerName(), s, true);
		return s;

	}

}
