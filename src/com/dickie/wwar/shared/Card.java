package com.dickie.wwar.shared;

import java.util.HashMap;

public class Card implements java.io.Serializable,Storable {
	private static final long serialVersionUID = 1L;
	public static enum CardType {Mana, Fire, Air, Water, Earth, Spirit, Epic, Nothing};
	public static enum Where{hand, discardPile, drawPile, location};
	
	public CardType getType() {
		return type;
	}
	public void setType(CardType type) {
		this.type = type;
	}
	public String getOwnerName() {
		return owner.getName();
	}
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	public Where getWhere() {
		return where;
	}
	public void setWhere(Where where) {
		this.where = where;
	}
	public String getLocName() {
		return locName;
	}
	public void setLocName(String locName) {
		this.locName = locName;
	}
	private CardType type;
	private Player owner;
	private Where where;
	private String locName;
	
	public String toString(){
		return "Card " + type.toString();
	}
	
	public static CardType generateCard(){
		int dieRoll = (int)java.lang.Math.round(java.lang.Math.random()*5 + 0.5);
		switch(dieRoll){
		case 1:
			return CardType.Air;
		case 2:
			return CardType.Earth;
		case 3:
			return CardType.Fire;
		case 4:
			return CardType.Water;
		case 5:
			return CardType.Mana;
		default:
			throw new RuntimeException("D5 roll returned " + dieRoll);
		}
	}
	
	public static Card getCard(CardType type){
		Card card = new Card();
		card.setType(type);;
		return card;
	}
	
	@Override
	public HashMap<String, Object> getProps() {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("ownerName", owner.getName());
		hm.put("where", where.toString());
		hm.put("cardType", type.toString());	
		hm.put("name", getName());
		return hm;
	}
	@Override
	public void setProperties(Game game, HashMap<String, Object> props) {
		setOwner(game.getPlayer((String)props.get("ownerName")));
		setWhere(Where.valueOf((String)props.get("where")));
		setType(CardType.valueOf((String)props.get("cardType")));
	}
	@Override
	public String getName() {
		return this.toString();
	}


}
