package com.dickie.wwar.shared;

public interface Mover {
	public Location getLocation();
	public void setLocation(Location location);
	public String getName();
	public String getOwnerName();
	public void setArmor(int pts);
	public int getArmor();
	public boolean isMoved();
	public void setHasMoved(boolean moved);
}
