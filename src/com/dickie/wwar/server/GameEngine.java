package com.dickie.wwar.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dickie.wwar.shared.Card;
import com.dickie.wwar.shared.Connection;
import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.GameHelper;
import com.dickie.wwar.shared.Golum;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderEngine;
import com.dickie.wwar.shared.Player;
import com.dickie.wwar.shared.PlayerMessage;
import com.dickie.wwar.shared.Spell;
import com.dickie.wwar.shared.Storable;

public class GameEngine {
	
	Game game;
	OrderEngine oe;
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
		oe = new OrderEngine();
		oe.setGame(game);
	}

	public void initGame(String[] playerNames){
		// create players
		// create locations
		// populate locations
		System.out.println("Creating a new demo game");
		setGame(new Game());
		new GameHelper().initGame(game, playerNames);
	}
	
	public void storeGame(){
		DataStoreImpl dsi = new DataStoreImpl();
		
		List<? extends Storable>ls = game.getMessages();
		List<Storable>ls2 = (List<Storable>) ls;
		dsi.store(game.getName(), ls2 ,"Message");
		
		ls = game.getConnections();
		ls2 = (List<Storable>) ls;
		dsi.store(game.getName(), ls2 ,"Connection");
		
		ls = game.getLocations();
		ls2 = (List<Storable>) ls;
		dsi.store(game.getName(), ls2 ,"Location");
		
		ls = game.getPlayers();
		ls2 = (List<Storable>) ls;
		dsi.store(game.getName(),  ls2, "Player");
		
		ls = game.getGolums();
		ls2 = (List<Storable>) ls;
		dsi.store(game.getName(),  ls2, "Golum");
		
		ls2 = new ArrayList<Storable>();
		for (Player player : game.getPlayers()){
			List<Card> lc = player.getHand();
			for (Storable s: lc){
				ls2.add(s);
			}
			lc = player.getDrawPile();
			for (Storable s: lc){
				ls2.add(s);
			}
			lc = player.getDiscardPile();
			for (Storable s: lc){
				ls2.add(s);
			}
		}
		dsi.store(game.getName(), ls2, "Card");
	}
	
	public void readGame(String gameName){
		DataStoreImpl dsi = new DataStoreImpl();
		ArrayList<PlayerMessage> am= new ArrayList<PlayerMessage>();
		for (Storable loc: dsi.get(game, game.getName(), "Message")){
			am.add((PlayerMessage)loc);
		}
		getGame().setMessages(am);
		
		ArrayList<Location> al= new ArrayList<Location>();
		for (Storable loc: dsi.get(game, game.getName(), "Location")){
			al.add((Location)loc);
		}
		getGame().setLocations(al);
		ArrayList<Connection> ac= new ArrayList<Connection>();
		for (Storable conn: dsi.get(game, game.getName(), "Connection")){
			ac.add((Connection)conn);
		}
		getGame().setConnections(ac);
		ArrayList<Player> ap= new ArrayList<Player>();
		for (Storable p: dsi.get(game, game.getName(), "Player")){
			ap.add((Player)p);
		}
		getGame().setPlayers(ap);
		for (Storable c: dsi.get(game, game.getName(), "Card")){
			Card card = (Card)c;
			Player p = game.getPlayer(card.getOwnerName());
			if (card.getWhere().equals(Card.Where.hand)){
				p.addCardToHand(card.getType().toString());
			} else if (card.getWhere().equals(Card.Where.discardPile)){
				p.addCardToDiscard(card.getType().toString());
			} else if (card.getWhere().equals(Card.Where.drawPile)){
				p.addCardToDraw(card.getType().toString());
			} else {
				throw new RuntimeException("Unknown pile for card... " + card.getWhere());
			}
		}
		ArrayList<Golum> ag= new ArrayList<Golum>();
		for (Storable p: dsi.get(game, game.getName(), "Golum")){
			ag.add((Golum)p);
		}
		getGame().setGolums(ag);
	}
	
	public boolean doPlayerOrders(String playerName, List<Order> orders){
		game.getPlayer(playerName).setTurnComplete(true);
		System.out.println("  Order for " + playerName);
		for (Order order: orders){
			oe.doOrder(order);
			System.out.println("    " + order.toString());
		}
		boolean doTurn = true;
		for (Player player : game.getPlayers()){
			if (!player.isTurnComplete()){
				doTurn = false;
				break;
			}
		}
		if (doTurn){
			doTurn();
		}
		return doTurn;
		
	}
	
	public void doTurn(){
		game.getMessages().clear();
		System.out.println("starting turn -----------------------------------");
		clearChangedFlag();
		applyQueuedOrders();
		generateCards();
		resetVisibility();
		setCards();
		addLocationCards();
		oe.clearSingletons();
		doNprOrders();
		System.out.println("end turn ----------------------------------------");
		storeGame();
	}

	private void clearChangedFlag() {
		for (Location loc: game.getLocations()){
			loc.setChanged(false);
		}
		
	}

	private void doNprOrders() {
		NpcEngine ne = new NpcEngine();
		ne.setGame(game);
		for (Player player: game.getPlayers()){
			if (player.isNpr()){
				ne.GenerateOrders(player);
				
			}
		}
		
	}
	
	private void addLocationCards(){
		ArrayList<Player> pList = new ArrayList<Player>();
		for (Player player: game.getPlayers()){
			pList.add(player);
		}
		Collections.shuffle(pList);

	}

	private void resetVisibility() {
		for (Location loc: game.getLocations()){
			loc.getVisibleTo().clear();
			for (String player: loc.getCronies()){
				loc.addVisibleTo(player);
			}
		}
		for (Player player: game.getPlayers()){
			player.setHasMoved(false);
		}
		for (Golum golum: game.getGolums()){
			golum.setHasMoved(false);
		}
	}
	private void setCards(){
		for (Player player: game.getPlayers()){
			player.setTurnComplete(false);
			player.setPassThroughPoints(player.getPassthrough() + player.getTempPassThrough());
			player.setArmor(0);
			player.discardRestOfHand(game);
			int pull = player.getDrawPile().size();
			if (pull == 0){
				pull = player.getDepth() - player.getDamage();
			}
			while (player.getHand().size() < pull){
				if (!player.drawCardFromDraw())
					break;
			}
			player.replentishDrawDeck();
			game.getLocation(player.getLocation().getName()).addVisibleTo(player.getName());
		}
		for (Golum golum: game.getGolums()){
			game.getLocation(golum.getLocation().getName()).addVisibleTo(golum.getOwner().getName());
		}
		
		
	}

	private void generateCards() {
		for (Location loc: game.getLocations()){
			if (!loc.getCard1().equals(Card.CardType.Nothing)){
				continue;
			}
			if (java.lang.Math.random() > 0.25){
				continue;
			}
			if (loc.getType().equals(Location.LocType.Town)){
				loc.setCard1(Card.generateCard());
			} else if (loc.getType().equals(Location.LocType.City)){
				loc.setCard1(Card.generateCard());
				loc.setCard2(Card.generateCard());
			}
		}
		
	}

	private void applyQueuedOrders() {
		int maxStep = 0;
		Collections.shuffle(game.getInstantOrders());
		Collections.shuffle(game.getQueuedOrders());
		for (Spell spell: Spell.spells){
			if (spell.getSpellOrder() > maxStep){
				maxStep = spell.getSpellOrder();
			}
		}
		for (int i = 0; i <= maxStep; i++){
			for (Spell spell: Spell.spells){
				if (spell.getSpellOrder() == i){
					applyQueuedOrder(spell);
				}
			}
		}
		game.getInstantOrders().clear();
		game.getQueuedOrders().clear();
	}
	
	private void applyQueuedOrder(Spell spell){
		for (Order order: game.getInstantOrders()){
			if (order.getSpell().equals(spell)){
				oe.executeOrder(order);
			}
		}
		for (Order order: game.getQueuedOrders()){
			if (order.getSpell().equals(spell)){
				oe.executeOrder(order);
			}
		}
	}
	
}
