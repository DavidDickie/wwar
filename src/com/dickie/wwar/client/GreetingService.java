package com.dickie.wwar.client;

import java.util.List;

import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.Player;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	Boolean doOrders(String gameName, String playerName, List<Order>orders);
	String getData(String gameName, String player, int x, int y);
	Location getLocation(String gameName, int x, int y);
	Player getPlayer(String gameName, String player, String password);
	void setPlayer(String gameName, String oldPlayerName, Player player);
	void setGameName(String oldGameName, String newGameName);
	void saveGame(String gameName);
	void editLocation(String gameName, String oldLocationName, Location newLoc);
	Game getGame(String gameName);
}
