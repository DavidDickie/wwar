package com.dickie.wwar.shared.orderhelpers;

import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderHelper;
import com.dickie.wwar.shared.Player;


public class DiscardHelper implements OrderHelper,java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String executeOrder(Game game, boolean server, Order order) {
		Player player = game.getPlayer(order.getMover().getOwnerName());
		player.discardCardFromHand(order.getCardType());
		if (server) {
			String s = order.getMover().getOwnerName() + " discards " + order.getCardType().toString();
			game.addMessage(order.getMover().getOwnerName(), s, true);
			return s;
		}
		return null;
	}

}
