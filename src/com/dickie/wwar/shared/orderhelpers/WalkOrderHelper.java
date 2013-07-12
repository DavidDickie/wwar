package com.dickie.wwar.shared.orderhelpers;

import java.util.List;

import com.dickie.wwar.shared.Connection;
import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Mover;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderHelper;

public class WalkOrderHelper implements OrderHelper,java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String executeOrder(Game game, boolean server, Order order) {

			Mover mover = game.getMover(order.getMover());

			Location loc1 = game.getLocation(game.getMover(mover.getName()).getLocation());
			Location loc2 = game.getLocation(order.getLocation());
			if (loc2 == null){
				String s = order.getMover().getName() + " move fails; no locatoin set";
				game.addMessage(mover.getName(), s, true);
				return s;
			}
			String response = "";
			if (server){
				Connection con = game.getConnection(loc1.getName(), loc2.getName());
				if (con == null){
					String s =  mover.getName() + " - Move fails; Points " + loc1 + " and " + loc2 + " are not connected";
					game.addMessage(mover.getName(), s, true);
					return s;
				} else if (con.isBlocked()){
					String s = order.getPlayer().getName() + " move fails; path is blocked";
					game.addMessage(mover.getName(), s, true);
					return s;
				} else if (con.isTrapped()){
					String s  = order.getPlayer().getName() + " move .. trap! " + OrderCommon.assignDamage(game, 1, mover) + "\n";
					game.addMessage(mover.getName(), s, true);
					return s;
				}
			}
			if (!game.isConnected(loc1.getName(), loc2.getName())){
				return "Points " + loc1 + " and " + loc2 + " are not connected";
			}
			if (mover.isMoved()){
				String s = mover.getName() + " move to location " + loc2.getName() + " fails, already moved or picked up card";
				game.addMessage(mover.getName(), s, true);
				return s;
			}
			if (server){
				List<Mover> ms = game.getMoversAtLocation(loc2);
				for (Mover m : ms){
					if (m.getOwnerName().equals(mover.getOwnerName())){
						continue;
					}
					String s = mover.getName() + " move to location " + loc2.getName() + " fails, already occupied by " + m.getOwnerName();
					game.addMessage(mover.getName(), s, true);
					return s;
				}
				loc2.addVisibleTo(mover.getOwnerName());
			}
			mover.setHasMoved(true);
			mover.setLocation(loc2);
			loc1.setChanged(true);
			loc2.setChanged(true);
			game.addMessage(mover.getName(), mover.getName() + " moved to " + loc2.getName(), true);
			return null;
	}

}
