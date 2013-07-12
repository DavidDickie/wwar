package com.dickie.wwar.shared.orderhelpers;

import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderHelper;
import com.dickie.wwar.shared.Player;

public class HealHelper implements OrderHelper ,java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String executeOrder(Game game, boolean server, Order order) {
		Player player = game.getPlayer(order.getOwnerName());
		if (player.getDamage() <= 0){
			game.addMessage(player.getName(), "No damage to heal", true);
			return "No damage to heal";
		}
		player.setDamage(player.getDamage()-1);
		game.addMessage(player.getName(), "Healed one point of damage", true);
		return null;
	}

}
