package com.dickie.wwar.shared.orderhelpers;

import com.dickie.wwar.shared.Card;
import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderHelper;
import com.dickie.wwar.shared.Player;

public class SpiritHelper implements OrderHelper,java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String executeOrder(Game game, boolean server, Order order) {
		Player player = game.getPlayer(order.getOwnerName());
		player.addCardToDiscard(Card.CardType.Spirit.toString());
		game.addMessage(player.getName(), player.getName()+ " adds a spirit card to his discard pile", true);
		return null;
	}
}
