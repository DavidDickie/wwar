package com.dickie.wwar.shared.orderhelpers;

import com.dickie.wwar.shared.Card;
import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderHelper;
import com.dickie.wwar.shared.Player;

public class TradeHelper implements OrderHelper, java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String executeOrder(Game game, boolean server, Order order) {
		Player player = game.getPlayer(order.getOwnerName());
		if (order.getCardType() == null) {
			if (server) {
				game.addMessage(player.getName(),
						"Select a card to trade for mana first", true);
			}
			return "Select a card to trade for mana first";
		}
		Card.CardType cardType = order.getCardType();

		if (player.getGold() < 1) {
			if (server) {
				game.addMessage(player.getName(), "Insufficient Gold", true);
			}
			return "Insufficient Gold";
		}
		player.setGold(player.getGold() - 1);
		player.deleteCardFromHand(cardType);
		player.addCardToDiscard(Card.CardType.Mana.toString());
		if (server){
			game.addMessage(player.getName(), "Swapped a " + order.getCardType().toString() + " for a mana", true);
		}
		return null;
	}

}
