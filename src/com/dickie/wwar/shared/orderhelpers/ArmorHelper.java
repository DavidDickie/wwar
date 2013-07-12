package com.dickie.wwar.shared.orderhelpers;

import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Mover;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderHelper;

public class ArmorHelper implements OrderHelper,java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String executeOrder(Game game, boolean server, Order order) {
		Mover mover = game.getMover(order.getMover().getName());
		mover.setArmor(mover.getArmor() + 1);
		game.addMessage(mover.getOwnerName(), mover.getName() + " added one point of armor", true);
		return null;
	}

}
