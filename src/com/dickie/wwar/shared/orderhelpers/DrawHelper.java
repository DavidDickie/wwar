package com.dickie.wwar.shared.orderhelpers;

import com.dickie.wwar.shared.Card;
import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderHelper;
import com.dickie.wwar.shared.Player;

public class DrawHelper implements OrderHelper,java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String executeOrder(Game game, boolean server, Order order) {
			Card.CardType type = order.getCardType();
			Player player = game.getPlayer(order.getOwnerName());
			player.drawCardFromDraw(type);
			if (player.getPassThroughPoints() <1){
				return "Insufficient points";
			}
			if (player.getTempPassThrough() > 0){
				player.setTempPassThrough(player.getTempPassThrough() - 1);
			}
			player.setPassThroughPoints(player.getPassThroughPoints() - 1);
			return null;
	}

}
