package com.dickie.wwar.shared.orderhelpers;

import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderHelper;

public class FamilliarHelper implements OrderHelper,java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String executeOrder(Game game, boolean server, Order order) {
		Location loc2 = order.getMover().getLocation();
		if (loc2.getCronies().contains(order.getMover().getName())){
			game.addMessage(order.getMover().getOwnerName(), "You already have a familiar in this town", true);
			return "You already have a familiar in this town";
		}
		loc2.addCrony(order.getMover().getName());
		return null;
	}

}
