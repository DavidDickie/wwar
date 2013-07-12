package com.dickie.wwar.shared.orderhelpers;

import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderHelper;


public class VisionHelper implements OrderHelper,java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String executeOrder(Game game, boolean server, Order order) {
		Location loc1 = game.getLocation(order.getLocation().getName());
		Location loc2 = game.getMover(order.getMover()).getLocation();
		if (!game.isConnected(loc1.getName(), loc2.getName())){
			game.addMessage(order.getMover().getOwnerName(), "Location is not connected to casters", true);
			return "Location is not connected to casters";
		}
		loc1.addVisibleTo(order.getMover().getName());
		game.addMessage(order.getMover().getOwnerName(), "can see into " + loc1.getName(), true);
		return null;
	}

}
