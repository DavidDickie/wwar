package com.dickie.wwar.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dickie.wwar.shared.Card.CardType;
import com.dickie.wwar.shared.Card.Where;

public class Location implements java.io.Serializable, Storable {
	private static final long serialVersionUID = 1L;
	public static enum LocType {City, Town, Keep, Mystical};
	
	public Location(){
		
	};
	
	public Location(LocType type, String name, int x, int y){
		this.type = type;
		this.name = name;
		this.x = x;
		this.y = y;
		
		if (type == LocType.City){
			pointValue = 2;
			card1 = Card.generateCard();
			card2 = Card.generateCard();
		} else if (type == LocType.Town){
			pointValue = 1;
			card1 = Card.generateCard();
			card2 = Card.CardType.Nothing;
		} else if (type == LocType.Mystical){
			pointValue = 0;
			card1 = Card.CardType.Mana;
			card2 = Card.CardType.Nothing;
		} else if (type == LocType.Keep){
			pointValue = 0;
			card1 = Card.CardType.Nothing;
			card2 = Card.CardType.Nothing;
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocType getType() {
		return type;
	}
	public void setType(LocType type) {
		this.type = type;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getPointValue() {
		return pointValue;
	}
	public void setPointValue(int pointValue) {
		this.pointValue = pointValue;
	}
	public String getLockedBy() {
		return lockedBy;
	}
	public void setLockedBy(String lockedBy) {
		this.lockedBy = lockedBy;
	}
	public CardType getCard1() {
		return card1;
	}
	public void setCard1(CardType card1) {
		this.card1 = card1;
	}
	public CardType getCard2() {
		return card2;
	}
	public void setCard2(CardType card2) {
		this.card2 = card2;
	}
	public boolean isLocked(){
		return !(lockedBy == null || lockedBy.equals(""));
	}
	
	private String name;
	private LocType type;
	private int x;
	private int y;
	private int pointValue;
	private String lockedBy;
	private CardType card1;
	private CardType card2;
	private List<String> visibleTo = new ArrayList<String>();
	private List<String> hasCrony = new ArrayList<String>();
	private boolean changed; 
	
	public HashMap<String, Object> getProps(){
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("name", name);
		hm.put("type", type.toString());
		hm.put("x", new Integer(x));
		hm.put("y", new Integer(y));
		hm.put("pointValue", new Integer(pointValue));
		hm.put("lockedBy", lockedBy);	
		hm.put("card1", card1.toString());
		hm.put("card2", card2.toString());
		String v = "";
		for (String s: visibleTo){
			v += s + "~";
		}
		hm.put("visibleTo", v);
		v = "";
		for (String s: hasCrony){
			v += s + "~";
		}
		hm.put("hasCrony", v);
		hm.put("changed", new Boolean(changed));
		return hm;
	}
	
	public void setProperties(Game game, HashMap<String, Object> props){
		setName((String)props.get("name"));
		setType(Location.LocType.valueOf((String)props.get("type")));
		setX(((Long)props.get("x")).intValue());
		setY(((Long)props.get("y")).intValue());
		setPointValue(((Long)props.get("pointValue")).intValue());
		setLockedBy((String)props.get("lockedBy"));	
		setCard1(Card.CardType.valueOf((String)props.get("card1")));
		setCard2(Card.CardType.valueOf((String)props.get("card2")));
		String[] locks = ((String)props.get("visibleTo")).split("~");
		for (int i = 0; i < locks.length; i++){
			addVisibleTo(locks[i]);	
		}
		locks = ((String)props.get("hasCrony")).split("~");
		for (int i = 0; i < locks.length; i++){
			addCrony(locks[i]);	
		}
		setChanged(((Boolean)props.get("changed")).booleanValue());
	}
	
	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public String addCrony(String playerName){
		if (hasCrony.contains(playerName)){
			return "Already have a familiar there";
		}
		hasCrony.add(playerName);
		return "Added familiar to " + getName();
	}
	
	public String removeCrony(String playerName){
		if (!hasCrony.contains(playerName)){
			return "No familiar to remove";
		}
		hasCrony.remove(playerName);
		return "Removed";
	}
	
	public List<String> getCronies(){
		return hasCrony;
	}
	
	public boolean isVisibleTo(String playerName){
		return visibleTo.contains(playerName);
	}
	public List<String> getVisibleTo() {
		return visibleTo;
	}
	public void addVisibleTo(String playerName) {
		visibleTo.add(playerName);
	}
	public void clearVisibleTo(){
		visibleTo.clear();
	}

	
	public void putCard1(Card card){
		card1 = card.getType();
	}
	
	public void putCard2(Card card){
		if (type != LocType.City){
			throw new RuntimeException("Tried to pull a second card from " + name);
		}
		card2 = card.getType();
	}
	
	public boolean isAtLocation(int x, int y){
		return (java.lang.Math.abs(x - getX()) < 3 && java.lang.Math.abs(y - getY()) < 3);
	}
	
	public boolean equals(Object l){
		if (l instanceof Location){
			return ((Location)l).getName().equals(getName());
		} 
		return false;
	}
	
	public String toString(){
		return "Location " + name;
	}
	

}
