package com.dickie.wwar.shared.orderhelpers;

import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Golum;
import com.dickie.wwar.shared.Mover;
import com.dickie.wwar.shared.Player;

public class OrderCommon implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	public static String assignDamage(Game game, int totDam, Mover mover){
		if (mover instanceof Player){
			return assignDamage (game, totDam, (Player)mover);
		} else {
			Golum golum = (Golum) mover;
			if (golum.getArmor() <= totDam){
				game.getGolums().remove(golum);
				return mover + " destroyed!";
			} else {
				golum.setArmor(golum.getArmor() - totDam);
				return mover + " armor stops damage";
			}
		}
	}
	public static String assignDamage(Game game, int totDam, Player dPlayer){
		int armor = dPlayer.getArmor();
		
		if (totDam == armor){
			dPlayer.setArmor(0);
			return "Spell eliminates armor but does no damage";
		} else if (totDam < armor){
			dPlayer.setArmor(-totDam);
			return "Spell stopped by armor";
		} else {
			totDam -= armor;
			dPlayer.setArmor(0);
			int buffer = dPlayer.getDepth() - dPlayer.getDamage();
			int diff = buffer - totDam;
			if (diff < 2){
				totDam = buffer-2;
			}
			dPlayer.setDamage(dPlayer.getDamage()-totDam);
			return "Did " + totDam + " damage to " + dPlayer.getName();
		}
	}
}
