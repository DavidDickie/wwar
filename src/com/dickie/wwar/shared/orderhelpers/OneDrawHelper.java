package com.dickie.wwar.shared.orderhelpers;

import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderHelper;
import com.dickie.wwar.shared.Player;

public class OneDrawHelper implements OrderHelper,java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String executeOrder(Game game, boolean server, Order order) {
		Player player = game.getPlayer(order.getOwnerName());
		player.changeTempPassThrough(1);
		game.addMessage(order.getMover().getOwnerName(), "added to his temporary draw count", true);
		return null;
	}

}

