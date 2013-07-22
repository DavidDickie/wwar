package com.dickie.wwar.shared.orderhelpers;

import java.util.List;

import com.dickie.wwar.shared.Connection;
import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Golum;
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
			String s = "";
			if (mover instanceof Golum){
				s += mover.getName() + " ";
			}
			if (loc2 == null){
				s += " move fails; no locatoin set";
				game.addMessage(mover.getOwnerName(), s, true);
				return s;
			}
			String response = "";
			if (server){
				Connection con = game.getConnection(loc1.getName(), loc2.getName());
				if (con == null){
					s += " - Move fails; Points " + loc1 + " and " + loc2 + " are not connected";
					game.addMessage(mover.getOwnerName(), s, true);
					return s;
				} else if (con.isBlocked()){
					s += " move fails; path is blocked";
					game.addMessage(mover.getOwnerName(), s, true);
					return s;
				} else if (con.isTrapped()){
					s += " move .. trap! " + OrderCommon.assignDamage(game, 1, mover) + "\n";
					game.addMessage(mover.getOwnerName(), s, true);
					return s;
				}
			}
			if (!game.isConnected(loc1.getName(), loc2.getName())){
				return "Points " + loc1 + " and " + loc2 + " are not connected";
			}
			if (mover.isMoved()){
				s +=" move to location " + loc2.getName() + " fails, already moved or picked up card";
				game.addMessage(mover.getOwnerName(), s, true);
				return s;
			}
			if (server){
				List<Mover> ms = game.getMoversAtLocation(loc2);
				for (Mover m : ms){
					if (m.getOwnerName().equals(mover.getOwnerName())){
						continue;
					}
					s += " move to location " + loc2.getName() + " fails, already occupied by " + m.getOwnerName();
					game.addMessage(mover.getOwnerName(), s, true);
					return s;
				}
				loc2.addVisibleTo(mover.getOwnerName());
			}
			mover.setHasMoved(true);
			mover.setLocation(loc2);
			loc1.setChanged(true);
			loc2.setChanged(true);
			game.addMessage(mover.getOwnerName(), s + " moved to " + loc2.getName(), true);
			return null;
	}

}
