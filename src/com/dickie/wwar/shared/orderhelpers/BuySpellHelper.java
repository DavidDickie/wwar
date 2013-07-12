package com.dickie.wwar.shared.orderhelpers;

import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderHelper;
import com.dickie.wwar.shared.Player;
import com.dickie.wwar.shared.Spell;

public class BuySpellHelper implements OrderHelper,java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String executeOrder(Game game, boolean server, Order order) {
			Spell spell = Spell.getSpell(order.getPurchasedSpell());
			Player player = game.getPlayer(order.getOwnerName());
			player.addKnownSpells(spell);
			if (player.getGold() < spell.cards.length){
				game.addMessage(player.getName(), "Insufficient Gold", true);
				return "Insufficient Gold";
			}
			player.setGold(player.getGold() - spell.cards.length);
			game.addMessage(player.getName(), player.getName() + " bought " + spell.toString(), true);
			return null;
	
	}

}
