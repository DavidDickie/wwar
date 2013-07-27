package com.dickie.wwar.shared.orderhelpers;

import static org.junit.Assert.assertTrue;

import com.dickie.wwar.server.GameEngine;
import com.dickie.wwar.shared.Card;
import com.dickie.wwar.shared.Card.CardType;
import com.dickie.wwar.shared.Connection;
import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Golum;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Order;
import com.dickie.wwar.shared.OrderEngine;
import com.dickie.wwar.shared.Player;
import com.dickie.wwar.shared.Spell;

public class TestSetUp {

	private Game game; 
	
	public GameEngine generateTestDb(){
		GameEngine ge = new GameEngine();
		String[] players = {"Player1", "Player2", "Player3", "Player4"};
		ge.initGame(players);
		game = ge.getGame();
		ge.getGame().setName("test");
		//ge.doTurn();
		game.getMessages().clear();
		return ge;
	}
	
	public void giveWizardAllSpells(Player p){
		for (Spell spell : Spell.spells()){
			if (spell.startSpell || spell.isInvisible()){
				continue;
			}
			p.addKnownSpells(spell);
		}
	}
	
	public void colocatePlayers(Player p1, Player p2, Game game){
		String loc1 = "Kragaford";
		String loc2 = "Myrmack";
		p1.setLocation(game.getLocation(loc1));
		p2.setLocation(game.getLocation(loc2));
	}
	
	public void setPlayerLocation(Player p1, String location){
		p1.setLocation(game.getLocation(location));
	}
	
	public void setPlayerLocation(Player p1){
		String loc1 = "Kragaford";
		p1.setLocation(game.getLocation(loc1));
	}
	
	public void colocatePlayerAndGolem(Player p, Golum g, Game game){
		String loc1 = "Kragaford";
		String loc2 = "Myrmack";
		p.setLocation(game.getLocation(loc1));
		g.setLocation(game.getLocation(loc2));
	}
	
	public void givePlayerAllCards(Player p){
		p.addCardToHand(Card.CardType.Air.toString());
		p.addCardToHand(Card.CardType.Earth.toString());
		p.addCardToHand(Card.CardType.Fire.toString());
		p.addCardToHand(Card.CardType.Water.toString());
		p.addCardToHand(Card.CardType.Mana.toString());
		p.addCardToHand(Card.CardType.Mana.toString());
	}
	
	public  void givePlayerAllDrawCards(Player p){
		p.addCardToDraw(Card.CardType.Air.toString());
		p.addCardToDraw(Card.CardType.Earth.toString());
		p.addCardToDraw(Card.CardType.Fire.toString());
		p.addCardToDraw(Card.CardType.Water.toString());
		p.addCardToDraw(Card.CardType.Mana.toString());
		p.addCardToDraw(Card.CardType.Mana.toString());
	}

	public Connection getRandomConnection() {
		return game.getConnections().get(((int)java.lang.Math.random()*game.getConnections().size()));
	}
	
	public Location getRandomLocation() {
		return game.getLocations().get(((int)java.lang.Math.random()*game.getLocations().size()));
	}

	public void givePlayerCard(Player p, CardType type) {
		p.addCardToHand(type.toString());
	}
	
	public void givePlayerSpellCards(Player p, String spell){
		Spell s = Spell.getSpell(spell);
		assert (s!= null);
		for (Card.CardType type : s.getCards()){
			givePlayerCard(p, type);
		}
	}
	
	public GameEngine setup(String spell){
		GameEngine ge = generateTestDb();
		Player p = ge.getGame().getPlayer("Player1");
		p.clearHand();
		givePlayerSpellCards(p, spell);
		return ge;
	}
	
	public void executeOrder(String spell, Order o, GameEngine ge){
		executeOrder( spell,  o,  ge, true);
	}
	
	public void executeOrder(String spell, Order o, GameEngine ge, boolean verifyEmptyHand){
		o.setSpell(spell);
		Player p = ge.getGame().getPlayer("Player1");
		o.setPlayer(p);
		o.setOrderType(Order.OrderType.Magic);
		OrderEngine oe = new OrderEngine();
		oe.setGame(ge.getGame());
		String response = oe.executeOrder(o);
		assertTrue(response, response == null);
		if (verifyEmptyHand){
			assertTrue("Hand size is zero", p.getHand().size() == 0);
		}
	}

}
