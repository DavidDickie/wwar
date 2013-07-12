package com.dickie.wwar.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dickie.wwar.shared.Card.CardType;
import com.dickie.wwar.shared.Card.Where;
import com.dickie.wwar.shared.Spell;

public class Player implements java.io.Serializable, Mover, Storable {
	private static final long serialVersionUID = 1L;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getPassthrough() {
		return passthrough;
	}
	public void setPassthrough(int passthrough) {
		this.passthrough = passthrough;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public List<Card> getHand() {
		return hand;
	}
	public List<Card.CardType>getHandCardType(){
		ArrayList<Card.CardType>types = new  ArrayList<Card.CardType>();
		for (Card card: getHand()){
			types.add(card.getType());
		}
		return types;
	}
	public List<Card> getDiscardPile() {
		return discardPile;
	}
	public boolean isTurnComplete() {
		return turnComplete;
	}
	public void setTurnComplete(boolean turnComplete) {
		this.turnComplete = turnComplete;
	}
	public List<Card> getDrawPile() {
		return drawPile;
	}
	public int getArmor() {
		return armor;
	}
	public void setArmor(int armor) {
		this.armor = armor;
	}
	public List<Spell> getKnownSpells() {
		return knownSpells;
	}
	public void addKnownSpells(Spell spell) {
		System.out.println("Bought " + spell.toString());
		knownSpells.add(spell);
	}
	public int getTempPassThrough() {
		return tempPassThrough;
	}
	public void setTempPassThrough(int tempPassThrough) {
		this.tempPassThrough = tempPassThrough;
	}
	public void changeTempPassThrough(int add) {
		this.tempPassThrough += add;
	}
	public boolean isMoved() {
		return hasMoved;
	}
	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
	
	private String name;
	private String password;
	private int depth;
	private int passthrough;
	private int tempPassThrough;
	private int passThroughPoints;
	private int damage;
	private int armor;
	private List<Card> hand = new ArrayList<Card>();
	private List<Card> discardPile = new ArrayList<Card>();
	private List<Card> drawPile = new ArrayList<Card>();
	private List<Spell>knownSpells = new ArrayList<Spell>();
	private Location location;
	private boolean turnComplete;
	private String color;
	private int xOffset;
	private int yOffset;
	private boolean Npr = true;
	private int gold;
	private boolean hasMoved;
	
	

	@Override
	public HashMap<String, Object> getProps() {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("name", name);
		hm.put("password", password);
		hm.put("color", color);
		hm.put("depth", new Integer(depth));
		hm.put("passthrough", new Integer(passthrough));
		hm.put("tempPassThrough", new Integer(tempPassThrough));
		hm.put("passThroughPoints", new Integer(passThroughPoints));
		hm.put("damage", new Integer(damage));
		hm.put("armor", new Integer(armor));
		hm.put("xOffset", new Integer(xOffset));
		hm.put("yOffset", new Integer(yOffset));
		hm.put("gold", new Integer(gold));
		hm.put("turnComplete", new Boolean(turnComplete));
		hm.put("npr", new Boolean(Npr));
		hm.put("location", location.getName());
		return hm;
	}
	@Override
	public void setProperties(Game game, HashMap<String, Object> props) {
		setName(props.get("name").toString());
		setPassword(props.get("password").toString());
		setColor(props.get("color").toString());
		setDepth(((Long)props.get("depth")).intValue());
		setPassthrough(((Long)props.get("passthrough")).intValue());
		setTempPassThrough(((Long)props.get("tempPassThrough")).intValue());
		setPassThroughPoints(((Long)props.get("passThroughPoints")).intValue());
		setDamage(((Long)props.get("damage")).intValue());
		setArmor(((Long)props.get("armor")).intValue());
		setxOffset(((Long)props.get("xOffset")).intValue());
		setyOffset(((Long)props.get("yOffset")).intValue());
		setGold(((Long)props.get("gold")).intValue());
		setTurnComplete(((Boolean)props.get("turnComplete")).booleanValue());
		setNpr(((Boolean)props.get("npr")).booleanValue());
		setLocation(game.getLocation(props.get("location").toString()));
	}
	
	public int getPassThroughPoints() {
		return passThroughPoints;
	}
	public void setPassThroughPoints(int passThroughPoints) {
		this.passThroughPoints = passThroughPoints;
	}

	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public boolean isNpr() {
		return Npr;
	}
	public void setNpr(boolean npr) {
		Npr = npr;
	}
	public int getxOffset() {
		return xOffset;
	}
	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}
	public int getyOffset() {
		return yOffset;
	}
	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}

	public String getOwnerName(){
		return name;
	}

	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public void putCardOnDiscard(Card card){
		card.setOwnerName(name);
		card.setWhere(Where.discardPile);
		discardPile.add(card);
	}
	
	public void discardRestOfHand(Game game){
		int totalMana = 0;
		int totalOther = 0;
		
		for (int i = hand.size()-1; i >= 0; i--){
			Card card = hand.get(i);
			if (card.getType().equals(Card.CardType.Mana)){
				totalMana++;
				setGold(getGold()+1);
				card.setWhere(Card.Where.discardPile);
				discardPile.add(card);
				hand.remove(i);
			} else {
				totalOther++;
			}	
			
		}
		if (totalMana > 0){
			game.addMessage(getName(), getName() + " discarded " + totalMana + " Mana cards from his hand for gold", true);
		}
		if (totalOther > 0){
			game.addMessage(getName(), getName() + " held " + totalOther + " non-mana cards", true);
		}

	}
	
	public boolean drawCardFromDraw(){
		if (drawPile.size() == 0){
			shuffleDiscardPile();
			replentishDrawDeck();
			return false;
		}

		Card card = drawPile.get(0);
		drawPile.remove(card);
		card.setWhere(Where.hand);
		hand.add(card);
		System.out.println(getName() + " drew " + card.toString());
		return true;
		
	}
	
	public void drawCardFromDraw(Card.CardType specificType){
		Card c = null;
		for (Card card: drawPile){
			if (card.getType().equals(specificType)){
				c = card;
				break;
			}
		}
		if (c == null){
			throw new RuntimeException("Tried to pull a " + specificType.toString() + " from the drawpile but there isn't one");
		}
		c.setWhere(Card.Where.hand);
		drawPile.remove(c);
		hand.add(c);
		System.out.println(getName() + " put " + specificType.toString() + " in his hand");
	}
	
	public void addCardToHand(String cardType){
		Card card = new Card();
		card.setType(CardType.valueOf(cardType));
		card.setWhere(Card.Where.hand);
		card.setOwnerName(name);
		hand.add(card);
	}
	
	public void addCardToDiscard(String cardType){
		Card card = new Card();
		card.setType(CardType.valueOf(cardType));
		card.setWhere(Card.Where.discardPile);
		card.setOwnerName(name);
		discardPile.add(card);
	}
	
	public void addCardToDraw(String cardType){
		Card card = new Card();
		card.setType(CardType.valueOf(cardType));
		card.setWhere(Card.Where.drawPile);
		card.setOwnerName(name);
		drawPile.add(card);
	}
	
	public void shuffleDiscardPile(){
		ArrayList<Card> temp = new ArrayList<Card>();
		while (discardPile.size() > 0){
			int cardNum = (int)(java.lang.Math.random() * discardPile.size());
			temp.add(discardPile.get(cardNum));
			discardPile.remove(cardNum);
		}
		discardPile = temp;
	}
	
	public void replentishDrawDeck(){
		shuffleDiscardPile();
		while (drawPile.size() < depth && discardPile.size() > 0){
			Card card = discardPile.get(0);
			card.setWhere(Card.Where.drawPile);
			drawPile.add(card);
			discardPile.remove(card);
			System.out.println(getName() + " put " + card.toString() + " in his draw deck");
		}
	}
	
	public void discardCardFromHand(Card.CardType type) {
		Card drawn = null;
		for (Card card:hand){
			if (card.getType() == type){
				drawn = card;
				break;
			}
		}
		if (drawn == null){
			throw new RuntimeException("Tried to pull a " + type.toString() + " card from " + name + "'s hand, but there isn't one");
		}
		hand.remove(drawn);
		System.out.println(getName() + " discarded " + drawn.toString());
		putCardOnDiscard(drawn);
	}
	
	public void deleteCardFromHand(Card.CardType type){
		Card drawn = null;
		for (Card card:hand){
			if (card.getType() == type){
				drawn = card;
				break;
			}
		}
		if (drawn == null){
			throw new RuntimeException("Tried to delete a card from " + name + "'s hand, but there isn't one");
		}
		hand.remove(drawn);
		System.out.println(getName() + " removed " + drawn.toString() + " from his hand");
	}
	
	public String getDrawDistribution(){
		StringBuffer sb = new StringBuffer();

		for (CardType type: CardType.values()){
			if (type == CardType.Nothing || type == CardType.Epic){
				continue;
			}
			sb.append(type.toString()).append(": ");
			int count = 0;
			for (Card card: drawPile){
				if (card.getType().equals(type)){
					count++;
				}
			}
			sb.append(count).append("\n");
		}
		return sb.toString();
	}
	
	public boolean equals(Object p){
		if (p instanceof Player){
			return name.equals(((Player)p).getName());
		}
		return false;
	}
	
}
