package com.dickie.wwar.shared.orderhelpers;

import com.dickie.wwar.shared.Connection;
import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderHelper;

public class TrapPathHelper implements OrderHelper,java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String executeOrder(Game game, boolean server, Order order) {
		Connection con = game.getConnection(order.getConnection());
		if (con == null){
			return "Select a path";
		}
		con.setTrapped(true);
		game.addMessage(order.getOwnerName(), order.getOwnerName() + " traps the connection between " + con.toString(), false);
		return null;
	}

}
