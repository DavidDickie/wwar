package com.dickie.wwar.shared;

import java.util.HashMap;

public interface Storable {
	public HashMap<String,Object> getProps();
	public void setProperties(Game game, HashMap<String, Object> hm);
	public String getName();
}
