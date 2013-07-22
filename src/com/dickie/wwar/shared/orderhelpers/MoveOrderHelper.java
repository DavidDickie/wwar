package com.dickie.wwar.shared.orderhelpers;

import java.util.List;

import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Golum;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Mover;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderHelper;

public class MoveOrderHelper implements OrderHelper,java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String executeOrder(Game game, boolean server, Order order) {

		Mover mover = game.getMover(order.getMover());
		Location loc1 = game.getLocation(mover.getLocation());
		Location loc2 = game.getLocation(order.getLocation());
		String s = "";
		if (mover instanceof Golum){
			s += mover.getName() + " ";
		}
		if (loc2 == null) {
			s += order.getMover().getName()
					+ " move fails; no locatoin set";
			game.addMessage(mover.getOwnerName(), s, true);
			return s;
		}
		if (loc2.isLocked()) {
			s += mover.getName() + " - Move fails; location "
					+ loc2.getName() + " is locked";
			game.addMessage(mover.getOwnerName(), s, true);
			return s;
		}
		if (!game.isConnected(loc1.getName(), loc2.getName())) {
			s += mover.getName() + " - Move fails; Points " + loc1
					+ " and " + loc2 + " are not connected";
			game.addMessage(mover.getOwnerName(), s, true);
			return s;
		}
		if (mover.isMoved()){
			
			s += " move to location " + loc2.getName() + " fails, already moved or picked up card";
			game.addMessage(mover.getOwnerName(), s, true);
			return s;
		}
		
		if (server) {
			List<Mover> ms = game.getMoversAtLocation(loc2);
			for (Mover m : ms){
				if (m.getOwnerName().equals(mover.getOwnerName())){
					continue;
				}
				s += " move to Location " + loc2.getName() + " fails, already occupied by " + m.getName();
				game.addMessage(mover.getOwnerName(), s, true);
				return s;
			}
			loc2.addVisibleTo(mover.getOwnerName());
		}
		loc1.setChanged(true);
		loc2.setChanged(true);
		mover.setHasMoved(true);
		mover.setLocation(loc2);
		game.addMessage(mover.getOwnerName(),
				s + " moves to " + loc2.getName(), true);
		return null;

	}

}
