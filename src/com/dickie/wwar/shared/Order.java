package com.dickie.wwar.shared;

import java.util.ArrayList;
import java.util.List;

public class Order implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	public static enum OrderType {Move, Magic, Golum};
	
	private static Spell[] spells = Spell.spells();
	
	public Location getLocation() {
		return location;
	}
	
	Location location;
	Connection connection;
	OrderType orderType;
	Player damagePlayer; 
	Golum damageGolum;
	Player player;
	Golum golum;
	private int multiples;
	private Spell spell;
	private String purchasedSpell;
	private Card.CardType cardType;
	
	public Card.CardType getCardType() {
		return cardType;
	}
	public void setCardType(Card.CardType cardType) {
		this.cardType = cardType;
	}
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getPurchasedSpell() {
		return purchasedSpell;
	}
	public void setPurchasedSpell(String purchasedSpell) {
		this.purchasedSpell = purchasedSpell;
	}
	public Spell getSpell() {
		return spell;
	}
	public void setSpell(Spell spell) {
		this.spell = spell;
	}
	public int getMultiples(){
		return multiples;
	}
	public void setMultiples(int multiples){
		this.multiples=multiples;
	}
	public Golum getGolum() {
		return golum;
	}
	public void setGolum(Golum golum) {
		this.golum = golum;
	}

	public Player getPlayer() {
		return player;
	}
	
	public String getOwnerName(){
		if (player != null){
			return player.getName();
		} else {
			return golum.getOwnerName();
		}
	}
	
	public Mover getMover(){
		if (player != null){
			return player;
		}
		return golum;
	}
	public void setMover(Mover mover){
		if (mover instanceof Player){
			player = (Player)mover;
		} else if (mover instanceof Golum){
			golum = (Golum)mover;
		} else {
			throw new RuntimeException("Not a player or a golem: " + mover.toString());
		}
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Player getDamagePlayer() {
		return damagePlayer;
	}
	public void setDamagePlayer(Player damagePlayer) {
		this.damagePlayer = damagePlayer;
	}
	public Golum getDamageGolum() {
		return damageGolum;
	}
	public Mover getDamageMover(){
		if (damageGolum != null)
			return damageGolum;
		return damagePlayer;
	}
	public void setDamageGolum(Golum damageGolum) {
		this.damageGolum = damageGolum;
	}
	public OrderType getOrderType() {
		return orderType;
	}
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	
	public void setSpell(String type){
		for (Spell spell: spells){
			if (spell.name.equals(type)){
				this.spell = spell;
				break;
			}
		}
	}

	
	public void setLocation(Location name){
		location = name;
	}
	
	public Spell getSpell(String spellName){
		for (int i = 0; i < spells.length; i++){
			if (spells[i].name.equals(spellName))
				return spells[i];
		}
		throw new RuntimeException("No spell " + spellName);
	}
	
	public List<Spell> getSpells(){
		ArrayList<Spell> spellList = new ArrayList<Spell>();
		for (int i = 0; i < spells.length; i++){
			spellList.add(spells[i]);
		}
		return spellList;
	}
	
	public List<Spell> castable(List<Card> cards){
		ArrayList<Spell> spellList= new ArrayList<Spell>();
		for (Spell spell:spells){
			boolean canCast = true;
			for (Card.CardType type : spell.cards){
				canCast = false;
				for (Card card: cards){
					if (type == card.getType()){
						canCast = true;
						break;
					}
				}
				if (!canCast){
					break;
				}
			}
			if (spell.name.equals("Buy Spell")){
				int totalMana = 0;
				for (Card card: cards){
					if (card.getType().equals(Card.CardType.Mana)){
						totalMana++;
					}
				}
				if (totalMana < 2){
					canCast = false;
				}
			}
			if (canCast){
				spellList.add(spell);
			}
		}
		return spellList;
	}
	
	public String toString(){
		if (spell == null){
			return "Order [no spell set]";
		}
		String response =  "Order " + spell.name + " [" + getMover().getName() + "]";
		if (spell.affects=='L' && location != null){
			response += " " + location.getName();
			
		} else if (spell.affects == 'P'){
			if (getDamageMover() != null){
				response += " " + this.getDamageMover();
			} else {
				response += " no target";
			}
		}
		return response;
	}
	
	
}
