package com.dickie.wwar.shared.orderhelpers;

import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Golum;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderHelper;
import com.dickie.wwar.shared.Player;

public class CreateGolumHelper implements OrderHelper,java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String executeOrder(Game game, boolean server, Order order) {
		if (order.getMover() instanceof Golum){
			return "Only a wizard can create a Golem";
		}
		Player player = game.getPlayer(order.getOwnerName());
		Location loc1 = game.getLocation(order.getMover().getLocation().getName());
		if (!loc1.getType().equals(Location.LocType.Mystical)) 
		{
			if(server){
				game.addMessage(order.getMover().getOwnerName(), "not at a mystical location, can't create a golem", true);
			}
			return "not at a mystical location, can't create a golem here";
		}
		Golum golum = new Golum();
		golum.setGolumNumber(game);
		golum.setOwner(player);
		golum.setLocation(loc1);
		game.getGolums().add(golum);
		if(server){
			game.addMessage(order.getMover().getOwnerName(), " created Golem " + golum.toString(), true);
		}
		return null;
	}

}