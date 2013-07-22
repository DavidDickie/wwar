package com.dickie.wwar.server;

import java.util.ArrayList;
import java.util.List;

import com.dickie.wwar.client.GreetingService;
import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.Player;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	
	private static ArrayList<GameEngine> gameEngines = new ArrayList<GameEngine>();
	
	public GreetingServiceImpl(){
		DataStoreImpl dsi = new DataStoreImpl();
		List<String> names = dsi.getGameNames();
		for (String game: names){
			System.out.println("Getting game " + game);
			GameEngine ge = new GameEngine();
			ge.setGame(new Game());
			ge.getGame().setName(game);
			ge.readGame(game);
			gameEngines.add(ge);
		}
	}
	
	private void createNewGame(String gameName){
		for (GameEngine gEng: gameEngines){
			if (gEng.getGame().getName().equals(gameName)){
				throw new RuntimeException("Game " + gameName + "already exists");
			}
		}
		GameEngine ge = new GameEngine();
		String[] players = {"Player1", "Player2", "Player3", "Player4"};
		ge.initGame(players);
		ge.getGame().setName(gameName);
		ge.doTurn();
		ge.game.getMessages().clear();
		gameEngines.add(ge);
	}

	public String greetServer(String input) throws IllegalArgumentException {
		String games = "";
		if (gameEngines.size() == 0){
			createNewGame("demo");
		}
		for (GameEngine ge: gameEngines){
			games += games + ge.getGame().getName()+ ";";
		}
		return games;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	@Override
	public Game getGame(String gameName) {
		return getGameEngine(gameName).getGame();
	}
	
	public GameEngine getGameEngine(String gameName){
		for (GameEngine ge: gameEngines){
			if (ge.getGame().getName().equals(gameName)){
				return ge;
			}
		}
		if (gameName.equals("demo")){
			createNewGame("demo");
			for (GameEngine ge: gameEngines){
				if (ge.getGame().getName().equals("demo")){
					return ge;
				}
			}
		}
		throw new RuntimeException("No game named " + gameName);
	}
	
	@Override
	public Boolean doOrders(String gameName, String playerName, List<Order> orders) {
		return new Boolean(getGameEngine(gameName).doPlayerOrders(playerName, orders));
	}

	@Override
	public String getData(String gameName, String player, int x, int y) {
		return getGame(gameName).getData(player, x, y);
	}

	@Override
	public Player getPlayer(String gameName, String player, String password) {
		Player p = getGame(gameName).getPlayer(player);
		if (p.getPassword().equals(password)){
			return p;
		} else {
			System.out.println(password + " doesn't match " + p.getPassword());
		}
		throw new RuntimeException("Bad playername or password");
	}

	@Override
	public void editLocation(String gameName, String oldLocationName, Location newLoc) {
		Location loc = getGame(gameName).getLocation(oldLocationName);
		loc.setName(newLoc.getName());
		loc.setX(newLoc.getX());
		loc.setY(newLoc.getY());
	}

	@Override
	public Location getLocation(String gameName, int x, int y) {
		return getGame(gameName).getLocation(x, y);
	}

	@Override
	public void setPlayer(String gameName, String oldPlayerName, Player player) {
		getGame(gameName).setPlayer(oldPlayerName, player);
		
	}

	@Override
	public void setGameName(String oldGameName, String newGameName) {
		GameEngine ge = getGameEngine(oldGameName);
		ge.getGame().setName(newGameName);
		ge.storeGame();
	}

	@Override
	public void saveGame(String gameName) {
		getGameEngine(gameName).storeGame();
		
	}
}
