package com.dickie.wwar.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dickie.wwar.shared.Card;
import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Golum;
import com.dickie.wwar.shared.Mover;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.Player;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Spell;

public class NpcEngine {
	Game game;
	GameEngine ge;
	
	public void setGame(Game game){
		this.game = game;
		ge = new GameEngine();
		ge.setGame(game);
	}
	
	public void GenerateOrders(Player player){
		List<Order> orders = new ArrayList<Order>();
		List<Card>cards = new ArrayList<Card>();
		for (Card card : player.getHand()){
			cards.add(card);
		}
		if (!doMove(player, orders, cards)){
			orders.add(genMoveOrder(player, true, false));
		}
		doHealSpell(player, orders, cards);
		doBuildGolumSpell(player, orders, cards);
		doArmorSpell(player, orders, cards);
		if (player.getKnownSpells().contains(Spell.getSpell("RemoteDamage"))){
			if (!doRemoteDamageSpell(player, orders, cards)){
				for (Golum golum: game.getGolums()){
					if (golum.getOwner().equals(player)){
						if (doRemoteDamageSpell(golum, orders, cards)){
							break;
						}
					}
				}
			}
		}
		doDamageSpell(player, orders, cards);
		doBuySpellOrder(player, orders, cards);
		for (Golum golum: game.getGolums()){
			if (golum.getOwner().equals(player)){
				orders.add(genMoveOrder(golum, true, true));
			}
		}
		doDiscardOrders(player, orders, cards);
		
		ge.doPlayerOrders(player.getName(), orders);
	}
	
	private void doDiscardOrders(Player player, List<Order> orders,
			List<Card> cards) {
		ArrayList<Card.CardType> disCards = new ArrayList<Card.CardType>();
		for (Card card : cards){
			if (card.getType().equals(Card.CardType.Air) ||
					card.getType().equals(Card.CardType.Water) ||
					card.getType().equals(Card.CardType.Earth) ||
					card.getType().equals(Card.CardType.Fire) ||
					card.getType().equals(Card.CardType.Mana)){
				Order order = new Order();
				order.setPlayer(player);
				order.setOrderType(Order.OrderType.Magic);
				order.setSpell(Spell.getSpell("Discard"));
				order.setCardType(card.getType());
				//removeCards(cards, order.getSpell());
				orders.add(order);
				disCards.add(card.getType());
			}
		}
		for (Card.CardType type: disCards){
			discardCardFromHand(cards, type);
		}
		return;
	}

	private void doDamageSpell(Mover mover, List<Order> orders,
			List<Card> cards) {
		Order order = new Order();
		order.setMover(mover);
		if (order.castable(cards).contains(Spell.getSpell("Damage"))){
			List<Location>locs = game.getConnectedLocations(order.getMover().getLocation());
			for (Location loc: locs){
				List<Mover>movers = game.getMoversAtLocation(loc);
				if (movers.size() > 1){
					for (Mover m: movers){
						if (!m.getOwnerName().equals(mover.getOwnerName())){
							
							order.setOrderType(Order.OrderType.Magic);
							order.setSpell(Spell.getSpell("Damage"));
							if (m instanceof Player){
								order.setDamagePlayer((Player) m);
							} else {
								order.setDamageGolum((Golum) m);
							}
							removeCards(cards, order.getSpell());
							orders.add(order);
							return;
						}
					}
				}
			}
		}
		
	}
	
	private void doBuildGolumSpell(Mover mover, List<Order> orders,
			List<Card> cards) {
		Order order = new Order();
		order.setMover(mover);
		if (order.castable(cards).contains(Spell.getSpell("Summon Golem"))){
			order.setOrderType(Order.OrderType.Magic);
			order.setSpell(Spell.getSpell("Summon Golem"));
			removeCards(cards, order.getSpell());
			orders.add(order);
			return;
		}
		
	}
	
	private boolean doRemoteDamageSpell(Mover mover, List<Order> orders,
			List<Card> cards) {
		Order order = new Order();
		order.setMover(mover);
		if (order.castable(cards).contains(Spell.getSpell("RemoteDamage"))){
			List<Location> locs = game.getConnectedLocations(mover.getLocation());
			for (Location loc : locs){
				List<Mover>movers = game.getMoversAtLocation(loc);
				boolean hitIt = true;
				if (movers.size() > 1){
					for (Mover mover2: movers){
						if (mover2.getOwnerName().equals(mover.getOwnerName())){
							hitIt = false;
							break;
						}
					}
					if (hitIt){
						
						order.setOrderType(Order.OrderType.Magic);
						order.setSpell(Spell.getSpell("RemoteDamage"));
						order.setLocation(loc);
						removeCards(cards, order.getSpell());
						orders.add(order);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private void doArmorSpell(Player player, List<Order> orders,
			List<Card> cards) {
		Order order = new Order();
		if (order.castable(cards).contains(Spell.getSpell("Armor"))){
			boolean possibleAttacker = false;
			List<Mover>movers = game.getMoversAtLocation(player.getLocation());
			if (movers.size() > 1){
				for (Mover mover: movers){
					if (!mover.getOwnerName().equals(player.getName())){
						possibleAttacker = true;
						break;
					}
				}
			}
			if (!possibleAttacker){
				return;
			}
			order.setPlayer(player);
			order.setOrderType(Order.OrderType.Magic);
			order.setSpell(Spell.getSpell("Armor"));
			removeCards(cards, order.getSpell());
			orders.add(order);
		}	
	}
	
	private void doHealSpell(Player player, List<Order> orders,
			List<Card> cards) {
		if (player.getDamage() == 0)
			return;
		Order order = new Order();
		if (order.castable(cards).contains(Spell.getSpell("Heal"))){
			order.setPlayer(player);
			order.setOrderType(Order.OrderType.Magic);
			order.setSpell(Spell.getSpell("Heal"));
			removeCards(cards, order.getSpell());
			orders.add(order);
		}	
	}

	private void doBuySpellOrder(Player player,List<Order> orders, List<Card> cards) {
		Order order = new Order();
		if (order.castable(cards).contains(Spell.getSpell("Buy Spell"))){
			for (Spell spell : Spell.spells) {
				if (spell.startSpell
						|| player.getKnownSpells().contains(spell)
						|| spell.cards.length > player.getGold()
						|| spell.isInvisible()){
							continue;
					}
				order.setPlayer(player);
				order.setOrderType(Order.OrderType.Magic);
				order.setSpell(Spell.getSpell("Buy Spell"));
				order.setPurchasedSpell(spell.name);
				removeCards(cards, order.getSpell());
				orders.add(order);
				break;
			}
		}
	}
	
	public boolean doMove(Player player,List<Order> orders, List<Card> cards){
		Order order = new Order();
		if (!player.getLocation().getCard1().equals(Card.CardType.Nothing)){
			order.setPlayer((Player)player);
			order.setOrderType(Order.OrderType.Magic );
			order.setSpell(Spell.getSpell("Pick up resource"));
			orders.add(order);
			cards.add(Card.getCard(player.getLocation().getCard1()));
			if (!player.getLocation().getCard2().equals(Card.CardType.Nothing)){
				cards.add(Card.getCard(player.getLocation().getCard2()));
			}
			return true;
		} else if (player.getLocation().getType().equals(Location.LocType.Mystical) &&
				game.getMoversAtLocation(player.getLocation()).size() < 2){
			order.setPlayer((Player)player);
			order.setOrderType(Order.OrderType.Magic );
			order.setSpell(Spell.getSpell("Create Golem"));
			orders.add(order);
			return true; 
		} else if (order.castable(cards).contains(Spell.getSpell("Move"))){
			orders.add(genMoveOrder(player, false,false));
			removeCards(cards, Spell.getSpell("Move"));
			return true;
		}
		return false;
	}

	public Order genMoveOrder(Mover player, boolean walk, boolean randomMove){
		Location loc = player.getLocation();
		List<Location> possibleLocs = game.getConnectedLocations(loc);
		Order order = new Order();
		if (player instanceof Player){
			order.setPlayer((Player)player);
		} else {
			order.setGolum((Golum)player);
		}
		order.setOrderType(Order.OrderType.Magic );
		if (walk){
			order.setSpell(Spell.getSpell("Walk"));
		} else {
			order.setSpell(Spell.getSpell("Move"));
		}
		Collections.shuffle(possibleLocs);
		Location moveTo = possibleLocs.get(0);
		if (!randomMove){
			for (Location loc2 : possibleLocs){
		
				if (moveTo == null){
					moveTo = loc2;
					continue;
				}
				if (moveTo.isLocked() && !moveTo.getLockedBy().equals(player.getName())){
					moveTo = loc2;
					continue;
				}
				if (moveTo.getCard1().equals(Card.CardType.Nothing) && 
						loc2.getType().equals(Location.LocType.Mystical)){
					moveTo = loc2;
					continue;
				}
				if (moveTo.getCard1().equals(Card.CardType.Nothing) && 
						!loc2.getCard1().equals(Card.CardType.Nothing)){
					moveTo = loc2;
					continue;
				}
				if (moveTo.getCard2().equals(Card.CardType.Nothing) && 
						!loc2.getCard2().equals(Card.CardType.Nothing)){
					moveTo = loc2;
					continue;
				}
			}
		}
		order.setLocation(moveTo);
		System.out.println("  NPC Order: " + order.getSpell().name + " " + player.getName() + " from " + loc + " to "
				+ moveTo);
		return order;
	}

	public void removeCards(List<Card>cards, Spell spell){
		Card.CardType[] cardTypes = spell.cards;
		for (int i = 0; i < cardTypes.length; i++){
			discardCardFromHand(cards, cardTypes[i]);
		}
	}
	
	
	public void discardCardFromHand(List<Card>cards, Card.CardType type) {
		Card drawn = null;
		for (Card card:cards){
			if (card.getType() == type){
				drawn = card;
				break;
			}
		}
		cards.remove(drawn);
	}
}
