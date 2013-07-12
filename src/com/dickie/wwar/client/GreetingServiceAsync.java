package com.dickie.wwar.client;

import java.util.List;

import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.Player;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {

	void getGame(String gameName, AsyncCallback<Game> callback);

	void greetServer(String name, AsyncCallback<String> callback);

	void doOrders(String gameName, String playerName, List<Order> orders, AsyncCallback<Boolean> callback);

	void getData(String gameName, String player, int x, int y, AsyncCallback<String> callback);

	void getPlayer(String gameName, String player, String password,
			AsyncCallback<Player> callback);

	void editLocation(String gameName, String oldLocationName, Location newLoc,
			AsyncCallback<Void> callback);

	void getLocation(String gameName, int x, int y, AsyncCallback<Location> callback);

	void setPlayer(String gameName, String oldPlayerName, Player player,
			AsyncCallback<Void> callback);

	void setGameName(String oldGameName, String newGameName,
			AsyncCallback<Void> callback);

	void saveGame(String gameName, AsyncCallback<Void> callback);
}
