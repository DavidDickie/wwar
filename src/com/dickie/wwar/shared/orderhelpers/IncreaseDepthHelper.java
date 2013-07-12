package com.dickie.wwar.shared.orderhelpers;

import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderHelper;
import com.dickie.wwar.shared.Player;

public class IncreaseDepthHelper implements OrderHelper,java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String executeOrder(Game game, boolean server, Order order) {
		Player player = game.getPlayer(order.getOwnerName());
		if (player.getDepth()*2 > player.getHand().size() + player.getDrawPile().size() + player.getDiscardPile().size()){
			game.addMessage(order.getMover().getOwnerName(), " needs more cards to increase depth", true);
			return order.getMover().getOwnerName()+ " needs more cards to increase depth";
		}
		player.setDepth(player.getDepth() + 1);
		game.addMessage(order.getMover().getOwnerName(), " increases depth to " + player.getDepth(), true);
		return null;
	}

}
