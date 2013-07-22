package com.dickie.wwar.shared.orderhelpers;

import java.util.ArrayList;

import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Golum;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Mover;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderHelper;
import com.dickie.wwar.shared.Player;

public class RemoteDamageHelper implements OrderHelper,java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@Override
	public String executeOrder(Game game, boolean server, Order order) {
		Mover mover = game.getMover(order.getMover());
		Location loc1 = mover.getLocation();
		for (Location loc2: game.getConnectedLocations(loc1)){
			ArrayList<Player>tPlay = new ArrayList<Player>();
			for (Player player : game.getPlayers()){
				tPlay.add(player);
			}
			for (Player player : tPlay){
				if (player.getName().equals(mover.getOwnerName())){
					continue;
				}
				if (player.getLocation().equals(loc2)){
					game.addMessage(order.getMover().getOwnerName(), OrderCommon.assignDamage(game, 1, player), true);
				}
			}
			// we have to do this indirectly for Golems because they might be destroyed
			// and then we would get a concurrent modification exception
			ArrayList<Golum>tGol = new ArrayList<Golum>();
			for (Golum golum : game.getGolums()){
				if (golum.getOwner().getName().equals(mover.getOwnerName())){
					continue;
				}
				if (golum.getLocation().equals(loc2)){
					tGol.add(golum);
				}
			}
			for (Golum golum : tGol){
				game.addMessage(order.getMover().getOwnerName(), OrderCommon.assignDamage(game, 1, golum), true);
			}
			game.addMessage(order.getMover().getOwnerName(), loc1.getName() + " familiars destroyed", true);
			loc2.getCronies().clear();
		}
		return null;
	}
	
}
