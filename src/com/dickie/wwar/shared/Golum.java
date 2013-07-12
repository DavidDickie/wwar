package com.dickie.wwar.shared;

import java.util.HashMap;

public class Golum implements java.io.Serializable, Mover, Storable {
	private static final long serialVersionUID = 1L;
	
	public int getGolumNumber() {
		return golumNumber;
	}
	public void setGolumNumber(Game g) {
		int highWaterMark = 0;
		for (Golum golum: g.getGolums()){
			if (golum.getGolumNumber() <= highWaterMark){
				highWaterMark = golum.getGolumNumber() + 1;
			}
		}
		golumNumber = highWaterMark;
	}
	
	public Player getOwner() {
		return owner;
	}
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	public String getOwnerName(){
		return owner.getName();
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public boolean isDidOrder() {
		return didOrder;
	}
	public void setDidOrder(boolean attacked) {
		this.didOrder = attacked;
	}
	private int golumNumber;
	private Player owner;
	private Location location;
	private boolean didOrder;
	private int armor;
	private boolean hasMoved;
	
	
	public boolean isMoved() {
		return hasMoved;
	}
	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
	public int getArmor() {
		return armor;
	}
	public void setArmor(int armor) {
		this.armor = armor;
	}
	public String toString(){
		return "Golem " + golumNumber + " [" + owner.getName() + "][" + getArmor() + "]";
	}
	
	public String getName(){
		return toString();
	}
	public boolean equals(Object g){
		if (g instanceof Golum){
			return golumNumber == ((Golum)g).getGolumNumber();
		}
		return false;
	}
	@Override
	public HashMap<String, Object> getProps() {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("name", toString());
		hm.put("golumnNumber", new Integer(golumNumber));
		hm.put("owner", owner.getName());		
		hm.put("location", location.getName());
		hm.put("armor", new Integer(armor));
		return hm;
	}
	@Override
	public void setProperties(Game game, HashMap<String, Object> props) {
		golumNumber = ((Long)props.get("golumnNumber")).intValue();
		owner = game.getPlayer(props.get("owner").toString());
		location = game.getLocation(props.get("location").toString());
		armor = ((Long)props.get("armor")).intValue();
	}
	
}
