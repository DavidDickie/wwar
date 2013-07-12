package com.dickie.wwar.shared;

import java.util.ArrayList;

import com.dickie.wwar.shared.Location.LocType;

public class GameHelper {
	public static String[] color = {"#ff0000", "#FFFF99", "#00CC00", "#99CCFF", "#FF33FF", "#00FFCC", "#999900", "#CCCC33"};
	public void initGame(Game game, String[] playerNames){
		for (int i = 0; i < playerNames.length; i++){
			Player player = new Player();
			player.setName(playerNames[i]);
			if (i == 0){
				player.setNpr(false);
			}
			player.setPassword("password" + i);
			player.setColor(color[i]);
			player.setDepth(4);
			switch(i){
			case 0:
				player.setxOffset(1);
				player.setyOffset(0);
				break;
			case 1:
				player.setxOffset(0);
				player.setyOffset(1);
				break;
			case 2:
				player.setxOffset(-1);
				player.setyOffset(0);
				break;
			case 3:
				player.setxOffset(0);
				player.setyOffset(-1);
				break;
			case 4:
				player.setxOffset(1);
				player.setyOffset(1);
				break;
			case 5:
				player.setxOffset(1);
				player.setyOffset(-1);
				break;
			case 6:
				player.setxOffset(-1);
				player.setyOffset(1);
				break;
			case 7:
				player.setxOffset(-1);
				player.setyOffset(-1);
				break;
			}
			player.addCardToHand(Card.CardType.Mana.name());
			player.addCardToHand(Card.CardType.Mana.name());
			player.addCardToDiscard(Card.CardType.Mana.name());
			player.addCardToDiscard(Card.CardType.Mana.name());
			int dieRoll = (int)java.lang.Math.round(java.lang.Math.random()*4 + 0.5);
			switch(dieRoll){
			case 1:
				player.addCardToDiscard(Card.CardType.Fire.name());
				player.addCardToDiscard(Card.CardType.Air.name());
				player.addCardToHand(Card.CardType.Water.name());
				player.addCardToHand(Card.CardType.Earth.name());
				break;
			case 2:
				player.addCardToDiscard(Card.CardType.Fire.name());
				player.addCardToHand(Card.CardType.Air.name());
				player.addCardToDiscard(Card.CardType.Water.name());
				player.addCardToHand(Card.CardType.Earth.name());
				break;
			case 3:
				player.addCardToHand(Card.CardType.Fire.name());
				player.addCardToDiscard(Card.CardType.Air.name());
				player.addCardToDiscard(Card.CardType.Water.name());
				player.addCardToHand(Card.CardType.Earth.name());
				break;
			case 4:
				player.addCardToHand(Card.CardType.Fire.name());
				player.addCardToHand(Card.CardType.Air.name());
				player.addCardToDiscard(Card.CardType.Water.name());
				player.addCardToDiscard(Card.CardType.Earth.name());
				break;
			}
			
			game.addPlayer(player);
		}
		
		// create the map
		Location centralCity = new Location(LocType.City, "Central", 0, 0);
		game.addLocation(centralCity);
		ArrayList<Location> cities = new ArrayList<Location>();
		ArrayList<Location> leftTowns = new ArrayList<Location>();
		ArrayList<Location> rightTowns = new ArrayList<Location>();
		ArrayList<Location> eleftTowns = new ArrayList<Location>();
		ArrayList<Location> erightTowns = new ArrayList<Location>();
		
		
		
		for (int i = 0; i < playerNames.length; i++){
			int locCount = 0;
			ArrayList<Location>fixLoc = new ArrayList<Location>();
			int scale = 6;
			int xDist = 2*scale;
			
			String pNum = "_" + i + "_";
			
			Location loc = new Location(LocType.City, "City" + pNum + i, xDist, 0);
			game.addLocation(loc);
			fixLoc.add(loc);
			Connection conn = new Connection();
			conn.setStartLocation(centralCity);
			conn.setEndLocation(loc);
			game.addConnection(conn);
			cities.add(loc);
			
			
			xDist = 4*scale;
			
			Location innerRightTown = new Location(LocType.Town, "Town" + pNum +locCount++, xDist, -2);
			conn = new Connection();
			conn.setStartLocation(loc);
			conn.setEndLocation(innerRightTown);
			game.addLocation(innerRightTown);
			fixLoc.add(innerRightTown);
			game.addConnection(conn);
			leftTowns.add(innerRightTown);
			
			Location innerLeftTown = new Location(LocType.Town, "Town" + pNum +locCount++, xDist + 1*scale, 2);
			conn = new Connection();
			conn.setStartLocation(loc);
			conn.setEndLocation(innerLeftTown);
			game.addLocation(innerLeftTown);
			fixLoc.add(innerLeftTown);
			game.addConnection(conn);
			rightTowns.add(innerLeftTown);
			
			conn = new Connection();
			conn.setStartLocation(innerRightTown);
			conn.setEndLocation(innerLeftTown);
			game.addConnection(conn);
			
			xDist = 6*scale;
			
			// upper
			
			loc = new Location(LocType.Town, "Town" + pNum +locCount++, xDist, 8);
			conn = new Connection();
			conn.setStartLocation(innerLeftTown);
			conn.setEndLocation(loc);
			game.addLocation(loc);
			fixLoc.add(loc);
			game.addConnection(conn);
			erightTowns.add(loc);

			Location innerKeepTown = new Location(LocType.Town, "Town" + pNum +locCount++, xDist + 1*scale, 3);
			conn = new Connection();
			conn.setStartLocation(innerLeftTown);
			conn.setEndLocation(innerKeepTown);
			game.addLocation(innerKeepTown);
			fixLoc.add(innerKeepTown);
			game.addConnection(conn);
		

			conn = new Connection();
			conn.setStartLocation(loc);
			conn.setEndLocation(innerKeepTown);
			game.addConnection(conn);
			
			// lower
			
			Location outerKeepTown = new Location(LocType.Town, "Town" + pNum +locCount++, xDist, -3);
			conn = new Connection();
			conn.setStartLocation(innerRightTown);
			conn.setEndLocation(outerKeepTown);
			game.addLocation(outerKeepTown);
			fixLoc.add(outerKeepTown);
			game.addConnection(conn);
		
			loc = new Location(LocType.Town, "Town"+ pNum + locCount++, xDist-1*scale, -8);
			conn = new Connection();
			conn.setStartLocation(innerRightTown);
			conn.setEndLocation(loc);
			game.addLocation(loc);
			fixLoc.add(loc);
			game.addConnection(conn);
			eleftTowns.add(loc);

			conn = new Connection();
			conn.setStartLocation(loc);
			conn.setEndLocation(outerKeepTown);
			game.addConnection(conn);
			
			xDist = 8*scale;
			
			Location keep = new Location (LocType.Keep, "Keep_" + i, xDist, 0);
			game.addLocation(keep);
			fixLoc.add(keep);
			game.getPlayer(playerNames[i]).setLocation(keep);
			keep.addVisibleTo(playerNames[i]);
			
			conn = new Connection();
			conn.setStartLocation(innerKeepTown);
			conn.setEndLocation(keep);
			game.addConnection(conn);
			conn = new Connection();
			conn.setStartLocation(outerKeepTown);
			conn.setEndLocation(keep);
			game.addConnection(conn);
			
			int distmult =(int) i/4;
			
			for (Location tloc : fixLoc){
				if (distmult > 0){
					tloc.setX(tloc.getX() + 10);
				}
				int xCoord;
				if (i%4 == 0) {
					xCoord = tloc.getX();
					tloc.setX(-tloc.getY());
					tloc.setY(xCoord);
				} else if (i%4 == 2) {
					xCoord = tloc.getX();
					tloc.setX(tloc.getY());
					tloc.setY(-xCoord);			
				} else if (i%4 == 3) {
					tloc.setX(-tloc.getX());
					tloc.setY(-tloc.getY());
				}
				System.out.println(tloc.getName() + " " + tloc.getX() + ", " + tloc.getY());
			}
			
		}
		for (int i = 0; i < rightTowns.size(); i++){
			Location lTown = leftTowns.get(i);
			Location rTown;
			if (i == rightTowns.size() - 1){
				rTown = rightTowns.get(0);
			} else {
				rTown = rightTowns.get(i+1);
			}
			Connection conn = new Connection();
			conn.setStartLocation(rTown);
			conn.setEndLocation(lTown);
			game.addConnection(conn);
		}
		for (int i = 0; i < erightTowns.size(); i++){
			Location lTown = eleftTowns.get(i);
			Location rTown;
			if (i == erightTowns.size() - 1){
				rTown = erightTowns.get(0);
			} else {
				rTown = erightTowns.get(i+1);
			}
			Connection conn = new Connection();
			conn.setStartLocation(rTown);
			conn.setEndLocation(lTown);
			game.addConnection(conn);
		}
		for (int i = 0; i < cities.size(); i++){
			Location c1 = cities.get(i);
			Location c2;
			if (i == cities.size() - 1){
				c2 = cities.get(0);
			} else {
				c2 = cities.get(i + 1);
			}
			Location c3 = new Location(Location.LocType.Mystical, null, (c1.getX() + c2.getX())/2, (c1.getY() + c2.getY())/2);
			game.addLocation(c3);
			c3.setName("Mystical_"+i);
			Connection con = new Connection();
			con.setStartLocation(c1);
			con.setEndLocation(c3);
			game.addConnection(con);
			con = new Connection();
			con.setStartLocation(c2);
			con.setEndLocation(c3);
			game.addConnection(con);
			
		}
		for (int i = 0;i < playerNames.length; i++){
			game.addGolum(game.getPlayer(playerNames[i]), game.getLocation("Keep_"+i));
		}
	}
	
}
