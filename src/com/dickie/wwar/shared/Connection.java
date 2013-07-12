package com.dickie.wwar.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Connection implements java.io.Serializable, Storable {
	private static final long serialVersionUID = 1L;
	private static int instance;
	
	public Connection(){
		name = "Connection" + instance++;
	}
	
	public boolean isConnectedTo(Location loc){
		return startLocation.equals(loc) || endLocation.equals(loc);
	}
	
	public Location getStartLocation() {
		return startLocation;
	}
	public void setStartLocation(Location startLocation) {
		this.startLocation = startLocation;
	}
	public Location getEndLocation() {
		return endLocation;
	}
	public void setEndLocation(Location endLocation) {
		this.endLocation = endLocation;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getX1(){
		return startLocation.getX();
	}
	public int getX2(){
		return endLocation.getX();
	}
	public int getY1(){
		return startLocation.getY();
	}
	public int getY2(){
		return endLocation.getY();
	}
	
	private String name;
	private Location startLocation;
	private Location endLocation;
	private boolean blocked;
	private boolean trapped;
	
	public HashMap<String, Object> getProps(){
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("name", name);
		hm.put("startLocation", startLocation.getName());
		hm.put("endLocation", endLocation.getName());
		hm.put("blocked", new Boolean(blocked));
		hm.put("trapped", new Boolean(blocked));		
		return hm;
	}
	
	public void setProperties(Game game, HashMap<String, Object> props){
		setName((String)props.get("name"));
		setStartLocation(game.getLocation((String)props.get("startLocation")));
		setEndLocation(game.getLocation((String)props.get("endLocation")));
		setBlocked(((Boolean)props.get("blocked")).booleanValue());
		setTrapped(((Boolean)props.get("trapped")).booleanValue());
	}

	
	public boolean isTrapped() {
		return trapped;
	}


	public void setTrapped(boolean trapped) {
		this.trapped = trapped;
	}


	public boolean isAtLocation(int x, int y){
		return (java.lang.Math.abs(x - (getX1()+getX2())/2) < 5 && 
				java.lang.Math.abs(y - (getY1()+getY2())/2) < 5);
	}
	
	public boolean equals(Object c){
		if (c instanceof Connection){
			return ((Connection)c).getName().equals(name);
		}
		return false;
	}
	
	public String toString(){
		return startLocation.getName() + " to " + endLocation.getName();
	}
	

}
