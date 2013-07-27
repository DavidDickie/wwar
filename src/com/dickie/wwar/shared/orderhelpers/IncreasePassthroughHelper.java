package com.dickie.wwar.shared.orderhelpers;

import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderHelper;
import com.dickie.wwar.shared.Player;

public class IncreasePassthroughHelper implements OrderHelper,java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String executeOrder(Game game, boolean server, Order order) {
		Player player = game.getPlayer(order.getOwnerName());
		if (player.getDepth() < player.getPassthrough()){
			game.addMessage(player.getName(), " needs to increase depth to increase passthrough", true);
			return player.getName()+ " needs to increase depth to increase passthrough";
		}
		player.setPassthrough(player.getPassthrough() + 1);
		game.addMessage(player.getName(), player.getName()+ " increases pasthrough to " + player.getPassthrough(), true);
		return null;
	}
}
