package com.dickie.wwar.shared;

import java.util.HashMap;

public class PlayerMessage implements java.io.Serializable, Storable {
	private static final long serialVersionUID = 1L;
	
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isAllSee() {
		return allSee;
	}
	public void setAllSee(boolean allSee) {
		this.allSee = allSee;
	}
	public String playerName;
	public String message;
	public boolean allSee;

	@Override
	public HashMap<String, Object> getProps() {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("playerName", playerName);
		hm.put("message", message);
		hm.put("allSee", new Boolean(allSee));
		return hm;
	}
	@Override
	public void setProperties(Game game, HashMap<String, Object> hm) {
		setPlayerName(hm.get("playerName").toString());
		setMessage(hm.get("message").toString());
		setAllSee(((Boolean)hm.get("allSee")).booleanValue());
	}
	@Override
	public String getName() {
		return playerName + "-" + message;
	}

}
