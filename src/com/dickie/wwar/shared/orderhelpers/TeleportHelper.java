package com.dickie.wwar.shared.orderhelpers;

import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderHelper;

public class TeleportHelper implements OrderHelper ,java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String executeOrder(Game game, boolean server, Order order) {
		Location loc1 = game.getLocation(order.getLocation().getName());
		order.getMover().setLocation(loc1);
		loc1.addVisibleTo(order.getMover().getName());
		game.addMessage(order.getMover().getOwnerName(), "teleported to " + loc1.getName(), true);
		return null;
	}

}
