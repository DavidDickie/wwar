package com.dickie.wwar.shared;

import java.util.ArrayList;
import java.util.List;

public class Game implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<Player> players = new ArrayList<Player>();
	
	public List<Player> getPlayers() {
		return players;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public List<Connection> getConnections() {
		return connections;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setLocations(List<Location> locations){
		this.locations=locations;
	}
	public void setConnections(List<Connection>connections){
		this.connections=connections;
	}
	public void setPlayers(List<Player>players){
		this.players = players;
	}
	public void setGolums(List<Golum>golums){
		this.golums=golums;
	}

	private List<Location> locations = new ArrayList<Location>();
	private List<Connection> connections = new ArrayList<Connection>();
	private int turn;
	private String name;
	private List<Golum> golums = new ArrayList<Golum>();
	private List<PlayerMessage> messages = new ArrayList<PlayerMessage>();
	private List<Order> queuedOrders = new ArrayList<Order>();
	private List<Order> instantOrders = new ArrayList<Order>();
	
	public List<PlayerMessage> getMessages() {
		return messages;
	}
	
	public void setMessages(List<PlayerMessage> messages) {
		this.messages = messages;
	}

	public void addQueuedOrder(Order order){
		queuedOrders.add(order);
	}
	public List<Order> getQueuedOrders(){
		return queuedOrders;
	}
	public void clearQueuedOrders(){
		queuedOrders.clear();
	}
	
	public void addInstantOrder(Order order){
		instantOrders.add(order);
	}
	public List<Order> getInstantOrders(){
		return instantOrders;
	}
	public void clearInstantOrders(){
		instantOrders.clear();
	}
	
	public void addMessage(String toPlayer, String message, boolean allSee){
		if (message.length() > 499){
			throw new RuntimeException ("Message too long: " + message);
		}
		PlayerMessage pm = new PlayerMessage();
		pm.setPlayerName(toPlayer);
		pm.setMessage(message);
		pm.setAllSee(allSee);
		messages.add(pm);
	}
	
	public void addGolum(Player player, Location location){
		Golum golum = new Golum();
		golum.setGolumNumber(this);
		golum.setLocation(location);
		golum.setOwner(player);
		golums.add(golum);
	}
	
	public List<Golum>getGolums(){
		return golums;
	}
	
	public void addPlayer(Player player){
		players.add(player);
	}
	
	public void setPlayer(String oldName, Player player){
		Player oldPlayer = null;
		for (Player p: players){
			if (p.getName().equals(oldName)){
				oldPlayer = p;
				break;
			}
		}
		players.remove(oldPlayer);
		players.add(player);
	}
	
	public void addLocation(Location loc){
		locations.add(loc);
	}
	
	public void addConnection(Connection conn){
		connections.add(conn);
	}
	
	public Location getLocation(Location iloc){
		for(Location loc: locations){
			if (loc.equals(iloc)){
				return loc;
			}
		}
		throw new RuntimeException("Tried to get Location " + name + " but it doesn't exist");
	}
	
	public Location getLocation(String name){
		for(Location loc: locations){
			if (loc.getName().equals(name)){
				return loc;
			}
		}
		throw new RuntimeException("Tried to get Location " + name + " but it doesn't exist");
	}
	
	public Connection getConnection(String point1, String point2){
		for(Connection conn: connections){
			if (conn.getStartLocation().equals(getLocation(point1))&& 
					conn.getEndLocation().equals(getLocation(point2))){
				return conn;
			} 
			if (conn.getStartLocation().equals(getLocation(point2))&& 
					conn.getEndLocation().equals(getLocation(point1))){
				return conn;
			}
		}
		return null;
	}
	
	public Connection getConnection(Connection con){
		for(Connection conn: connections){
			if (conn.equals(con)){
				return conn;
			}
		}
		return null;
	}
	
	public Mover getMover(String name){
		Player player = getPlayer(name);
		if (player != null)
			return player;
		return getGolum(name);
	}
	
	public Mover getMover(Mover m){
		if (m instanceof Player){
			return getPlayer((Player)m);
		}
		return getGolum((Golum)m);
	}
	
	public Player getPlayer(String name){
		if (name == null)
			return null;
		for(Player player: players){
			if (player.getName().equals(name)){
				return player;
			}
		}
		return null;
	}
	
	public Player getPlayer(Player p){
		if (p == null)
			return null;
		for(Player player: players){
			if (player.equals(p)){
				return player;
			}
		}
		return null;
	}
	
	public Golum getGolum(String name){
		if (name == null)
			return null;
		if (name.charAt(0) == 'G'){
			String[] split = name.split(" ");
			name = split[1];
		}
		for (Golum golum : golums){
			if (golum.getGolumNumber() == Integer.valueOf(name)){
				return golum;
			}
		}
		return null;
	}
	
	public Golum getGolum(Golum g){
		if (g == null)
			return null;
		for (Golum golum : golums){
			if (golum.equals(g)){
				return golum;
			}
		}
		return null;
	}
	
	public boolean isConnected(String loc1, String loc2){
		return getConnection(loc1, loc2) != null;
	}
	
	public String getData(String player, int x, int y){
		for (Location loc: locations){
			if (loc.isAtLocation(x, y)){
				return getLocData(loc.isVisibleTo(player), loc);
			}
		}
		return "Nothing at "+ x + ", " + y;
	}
	
	public String getLocData(boolean full, Location loc){
		StringBuffer sb = new StringBuffer();
		sb.append(loc.getName() + ":\n");
		if (full){
			sb.append("Cards: ");
			if (loc.getCard1() != null){
				sb.append(loc.getCard1().toString());
			}
			if (loc.getCard2() != null){
				sb.append(", ").append(loc.getCard2().toString() + "\n");
			}
		}
		return sb.toString();
	}
	
	public Location getLocation(int x, int y){
		for (Location loc: locations){
			if (loc.isAtLocation(x, y)){
				return loc;
			}
		}
		return null;
	}
	
	public Connection getConnection(int x, int y){
		for (Connection loc: connections){
			if (loc.isAtLocation(x, y)){
				return loc;
			}
		}
		return null;
	}
	
	public List<Location> getConnectedLocations(Location loc){
		ArrayList<Location> locs = new ArrayList<Location>();
		for (Location aLoc: getLocations()){
			if (aLoc.equals(loc)){
				continue;
			} else if (isConnected(aLoc.getName(), loc.getName())){
				locs.add(aLoc);
			}
		}
		return locs;
	}
	
	public List<Mover> getMoversAtLocation(Location l){
		ArrayList<Mover> movers = new ArrayList<Mover>();
		for (Player player: players){
			if (player.getLocation().equals(l)){
				movers.add(player);
			}
		}
		for (Golum golum: golums){
			if (golum.getLocation().equals(l)){
				movers.add(golum);
			}
		}
		return movers;
	}
	

}
