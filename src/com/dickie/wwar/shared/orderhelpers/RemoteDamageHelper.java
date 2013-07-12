package com.dickie.wwar.shared.orderhelpers;

import java.util.ArrayList;

import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Golum;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderHelper;
import com.dickie.wwar.shared.Player;

public class RemoteDamageHelper implements OrderHelper,java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@Override
	public String executeOrder(Game game, boolean server, Order order) {
		Location loc1 = game.getLocation(order.getLocation().getName());
		Location loc2 = order.getMover().getLocation();
		if (!game.isConnected(loc1.getName(), loc2.getName())){
			game.addMessage(order.getMover().getOwnerName(), "Remote damage fails; location " + loc1.getName() + 
					" is not connected to " + loc2.getName(), true);
			return "Remote damage fails; location " + loc1.getName() + 
					" is not connected to " + loc2.getName();
		}
		ArrayList<Player>tPlay = new ArrayList<Player>();
		for (Player player : game.getPlayers()){
			tPlay.add(player);
		}
		for (Player player : tPlay){
			if (player.getLocation().equals(loc1)){
				game.addMessage(order.getMover().getOwnerName(), OrderCommon.assignDamage(game, 1, player), true);
				OrderCommon.assignDamage(game, 1, player);
			}
		}
		ArrayList<Golum>tGol = new ArrayList<Golum>();
		for (Golum golum : game.getGolums()){
			tGol.add(golum);
		}
		for (Golum golum : tGol){
			if (golum.getLocation().equals(loc1)){
				game.addMessage(order.getMover().getOwnerName(), OrderCommon.assignDamage(game, 1, golum), true);
				OrderCommon.assignDamage(game, 1, golum);
			}
		}
		game.addMessage(order.getMover().getOwnerName(), loc1.getName() + " familiars destroyed", true);
		loc1.getCronies().clear();
		return null;
	}
	
}
