package com.dickie.wwar.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dickie.wwar.shared.Card;
import com.dickie.wwar.shared.Connection;
import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Golum;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Player;
import com.dickie.wwar.shared.PlayerMessage;
import com.dickie.wwar.shared.Storable;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class DataStoreImpl {
	public List<String> getGameNames(){
		//clearAll();
		ArrayList<String> names = new ArrayList<String>();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Game");
		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable()) {
			String s = (String)result.getProperty("Name");
			if (s.indexOf("_backup") > 0){
				// it's a backup
				continue;
			}
			names.add(s);
		}
		return names;
	}
	public void store(String gameName, List<Storable>items,String storeType){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key gameKey = getGameKey(gameName, datastore);
		Key backupGameKey = getGameKey(gameName + "_backup", datastore);
		// delete prior data
		Query q = new Query(storeType).setAncestor(gameKey);
		Query q2 = new Query(storeType).setAncestor(backupGameKey);
		// delete the backup
		PreparedQuery pq2 = datastore.prepare(q2);
		for (Entity result : pq2.asIterable()) {
		  datastore.delete(result.getKey());
		}
		// Use PreparedQuery interface to retrieve results
		// store the old ones in the backup
		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable()) {
//			if (result.getProperty("name") != null){
//				Key k = KeyFactory.createKey(backupGameKey,storeType, result.getProperty("name").toString());		
//				Entity e = new Entity(storeType, k);
//				datastore.put(e);
//			}
		    datastore.delete(result.getKey());
		}
		for (Storable s: items){
			Key k = KeyFactory.createKey(gameKey,storeType, s.getName());		
			Entity e = new Entity(storeType, k);
			HashMap<String,Object> hm = s.getProps();
			for (String key: hm.keySet()){
				e.setProperty(key, hm.get(key));
			}
			datastore.put(e);
		}
		
	}
	
	public void clearAll(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query();
		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable()) {
			//System.out.println("Deleting " + result.toString());
		    datastore.delete(result.getKey());
		}
	}
	
	public List<Storable> get(Game game, String gameName, String storeType){
		ArrayList<Storable> cons = new ArrayList<Storable>();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key gameKey = getGameKey(gameName,datastore);
		System.out.println("Key is " + gameKey.toString());
		Query q = new Query(storeType).setAncestor(gameKey);
		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);


		for (Entity result : pq.asIterable()) {
		  HashMap<String, Object> hm = new HashMap<String, Object>();
		  for (String key : result.getProperties().keySet()){
			  hm.put(key, result.getProperty(key));
		  }
		  Storable s = null;
		  if (storeType.equals("Connection")){
			  s = new Connection();
		  } else if (storeType.equals("Location")){
			  s = new Location();
		  } else if (storeType.equals("Player")){
			  s = new Player();
		  } else if (storeType.equals("Card")){
			  s = new Card();
		  } else if (storeType.equals("Golum")){
			  s = new Golum();
		  } else if (storeType.equals("Message")){
			  s = new PlayerMessage();
		  }
		  
		  s.setProperties(game, hm);
		  cons.add(s);
		}
		return cons;
	}
	
	private Key getGameKey(String gameName, DatastoreService datastore){
		Query q = new Query("Game");
		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable()) {
			if (((String)result.getProperty("Name")).equals(gameName)){
				return result.getKey();
			}
		}
		System.out.println("Creating new key for " + gameName);
		Entity game= new Entity("Game");
		game.setProperty("Name", gameName);
		datastore.put(game);
		return game.getKey();
	}
	

}
