package com.dickie.wwar.shared.orderhelpers;

import com.dickie.wwar.shared.Card;
import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Golum;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Mover;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderHelper;
import com.dickie.wwar.shared.Player;


public class PickUpResourceHelper implements OrderHelper,java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String executeOrder(Game game, boolean server, Order order) {
		Mover mover = game.getMover(order.getMover());
		if (mover instanceof Golum){
			return "Only wizards can pick up resources";
		}
		Player player = game.getPlayer(order.getPlayer());
		if (player.isMoved()){
			if (server) {
				game.addMessage(player.getName(),player.getName() + " cannot pick up resources, already moved", true);
			}
			return player.getName() + " cannot pick up resources, already moved";
		}
		Location loc = game.getLocation(player.getLocation().getName());
		if (loc.getCard1() != Card.CardType.Nothing){
			if (server) {
				game.addMessage(player.getName(),player.getName() + " added " + 
						loc.getCard1().toString() + " to his hand from " + loc.toString(), true);
			}
			player.addCardToHand(loc.getCard1().toString());
			loc.setCard1(Card.CardType.Nothing);
		}
		if (loc.getCard2() != Card.CardType.Nothing){
			player.addCardToHand(loc.getCard2().toString());
			if (server){
				game.addMessage(player.getName(),player.getName() + " added " + 
						loc.getCard2().toString() + " to his hand from " + loc.toString(), true);
			}
			loc.setCard2(Card.CardType.Nothing);
		}
		player.setHasMoved(true);
		return null;
	}

}
