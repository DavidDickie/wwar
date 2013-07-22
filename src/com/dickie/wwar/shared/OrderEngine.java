package com.dickie.wwar.shared;


import java.util.ArrayList;
import java.util.List;

import com.dickie.wwar.shared.Spell;



public class OrderEngine {
	private Game game;

	public String doOrder(Order order){
		if (order.getSpell() != null){ 
			game.addInstantOrder(order);
			return "added a instant order\n";
			
		}
		return "Not a spell";
	}
	
	public String executeOrder(Order order){
		return executeSpell(true, order);
	}
	
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	private ArrayList<Order> singltons = new ArrayList<Order>();
	
	public void clearSingletons(){
		singltons.clear();
	}
	
	public String executeSpell(boolean server, Order orderInProgress){
		System.out.println(orderInProgress.toString());
		Spell spell = orderInProgress.getSpell();
		Card.CardType[] cards = orderInProgress.getSpell().cards;
		List<Card.CardType> aCards = orderInProgress.getSpell().getCards();
		if (!game.getPlayer(orderInProgress.getOwnerName()).getHandCardType().containsAll(aCards)){
			return "You do not have enough cards to cast " + spell.toString();
		}
		
		if (spell != null){
			if (!orderInProgress.getSpell().multiples){
				for (Order order: singltons){
					if (order.getMover().getName().equals(orderInProgress.getMover().getName()) && order.getSpell().equals(orderInProgress.getSpell()))
					return orderInProgress.getMover().getName() + " already did " + orderInProgress.getSpell().toString();
				}
			}
			
			Mover mover = orderInProgress.getMover();
			if (game.getMover(mover).isMoved() && spell.deffered){
				return "Can't do '" + spell.name + "' after moving";
			}
			if (game.getMover(mover.getName()) == null){
				game.addMessage(mover.getOwnerName(),"Order for dead man walking " + mover.getName() + " fails", true);
				return "Order for dead man walking " + mover.getName() + " fails"; 
			}
			String response = spell.getOh().executeOrder(game, server, orderInProgress);
			if (response != null){
				return response;
			}
		}

		Player activePlayer = game.getPlayer(orderInProgress.getOwnerName());
		for (int i = 0; i < cards.length; i++){
			activePlayer.discardCardFromHand(cards[i]);
		}
		if (!orderInProgress.getSpell().multiples){
			singltons.add(orderInProgress);
		}
		return null;
	}

	

}
