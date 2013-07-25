package com.dickie.wwar;

import com.dickie.wwar.server.GameEngine;
import com.dickie.wwar.shared.Card;
import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Golum;
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
		ge.doTurn();
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

}
