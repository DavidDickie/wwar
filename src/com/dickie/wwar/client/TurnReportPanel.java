package com.dickie.wwar.client;

import java.util.List;

import com.dickie.wwar.shared.Card;
import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Mover;
import com.dickie.wwar.shared.Player;
import com.dickie.wwar.shared.PlayerMessage;
import com.dickie.wwar.shared.Spell;
import com.google.gwt.user.client.ui.DecoratorPanel;

public class TurnReportPanel extends DecoratorPanel {

	public TurnReportPanel() {	
	}
	
	public static void showPlayers(Game game){
		StringBuffer sb = new StringBuffer();
		sb.append("<table class=\"ms-color2-main\" style=\"width: 100%\">");
		sb.append("<!-- fpstyle: 9,011111100 -->");
		sb.append("<tr>");
		sb.append("<td class=\"ms-color2-tl\">Player</td>");
		sb.append("<td class=\"ms-color2-top\">Spells</td>");
		sb.append("<td class=\"ms-color2-top\">Cards</td>");
		sb.append("<td class=\"ms-color2-top\">Towns</td>");
		sb.append("<td class=\"ms-color2-top\">Total</td>");
		sb.append("<td class=\"ms-color2-top\">Cards in hand</td></tr>");
		for (Player pl : game.getPlayers()) {
			int towns = pl.getTownCount(game);
			int numCards = pl.getHand().size()
					+ pl.getDrawPile().size()
					+ pl.getDiscardPile().size();
			String cardsInHand = "";
			for (Card card: pl.getHand()){
				cardsInHand += card.getType().toString() + " ";
			}
			sb.append("<tr><td class=\"ms-color2-even\">")
					.append(pl.getName())
					.append("</td><td class=\"ms-color2-even\">")
					.append(pl.getKnownSpells().size())
					.append("</td><td class=\"ms-color2-even\">")
					.append(numCards)
					.append("</td><td class=\"ms-color2-even\">")
					.append(towns)
					.append("</td><td class=\"ms-color2-even\">")
					.append(numCards + pl.getKnownSpells().size() * 2
							+ towns * 3)
					.append("</td><td class=\"ms-color2-even\">")
					.append(cardsInHand)
					.append("</td></tr>");
		}
		sb.append("</table>");
		DisplayHtmlDialog.getInstance().display("Player Info", sb.toString());

	}
	
	public static void showMessages (List<PlayerMessage> msgs){
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		sb.append("<head>");
		sb.append("<meta content=\"en-us\" http-equiv=\"Content-Language\" />");
		sb.append("<meta content=\"text/html; charset=windows-1252\" http-equiv=\"Content-Type\" />");
		sb.append("<title>Untitled 1</title>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<table class=\"ms-color2-main\" style=\"width: 100%\">");
		sb.append("	<!-- fpstyle: 9,011111100 -->");
		sb.append("	<tr>");
		sb.append("		<td class=\"ms-color2-tl\" style=\"width: 127px\">Player</td>");
		sb.append("		<td class=\"ms-color2-top\">Order</td>");
		sb.append("	</tr>");
		for (PlayerMessage pm : msgs){
			sb.append("	<tr><td class=\"auto-style1\" style=\"width: 127px\">");
			sb.append(pm.getPlayerName()).append("</td><td class=\"ms-color2-even\">").append(pm.getMessage()).
			append("</td></tr>\n");
		}
		sb.append("</table>");
		sb.append("</body>");
		sb.append("</html>");
		DisplayHtmlDialog.getInstance().display("Turn results", sb.toString());
	}
	
	public static void showSpells(Player p){
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		
		sb.append("<head>");
		sb.append("<meta content=\"en-us\" http-equiv=\"Content-Language\" />");
		sb.append("<meta content=\"text/html; charset=windows-1252\" http-equiv=\"Content-Type\" />");
		sb.append("<title>Untitled 1</title>");
		
		sb.append("</head>");
		sb.append("");
		sb.append("<body>");
		sb.append("");
		sb.append("<table class=\"ms-color2-main\" style=\"width: 100%\">");
		sb.append("<!-- fpstyle: 9,011111100 -->");
		sb.append("<tr>");
		sb.append("<td class=\"ms-color2-tl\">Name</td>");
		sb.append("<td class=\"ms-color2-top\">Description</td>");
		sb.append("<td class=\"ms-color2-top\">Order</td>");
		sb.append("<td class=\"ms-color2-top\">Start</td>");
		sb.append("<td class=\"ms-color2-top\">Multiples</td>");
		sb.append("<td class=\"ms-color2-top\">Type</td>");
		sb.append("<td class=\"ms-color2-top\">Cards used (if spell)</td>");
		sb.append("</tr>");
		Spell[] spells = Spell.spells();
		for (int i = 0; i < spells.length; i++){
			boolean known = false;
			sb.append("<tr>");
			for (Spell ps : p.getKnownSpells()){
				if (ps.name.equals(spells[i].name)){
					known = true;
					break;
				}
			}
			if (known){
				sb.append("<td class=\"auto-style1\">" + spells[i].name + "</td>");
			} else {
				sb.append("<td class=\"ms-color2-left\">" + spells[i].name + "</td>");
			}
			sb.append("<td class=\"ms-color2-even\">" + spells[i].getDescription() + "</td>");
			sb.append("<td class=\"ms-color2-even\">" + spells[i].getSpellOrder() + "</td>");
			sb.append("<td class=\"ms-color2-even\">" + spells[i].startSpell + "</td>");
			sb.append("<td class=\"ms-color2-even\">" + spells[i].multiples + "</td>");
			sb.append("<td class=\"ms-color2-even\">" + spells[i].affects + "</td>");
			sb.append("<td class=\"ms-color2-even\">");
			for (int y = 0; y < spells[i].cards.length; y++){
				sb.append(spells[i].cards[y].toString()).append(" ");
			}
			sb.append("</td>");
		}
		sb.append("</td></tr></table></body></html>");
		System.out.println();
		DisplayHtmlDialog.getInstance().display("Orders", sb.toString());
	}
	
	public static void showTurnOrders(List<PlayerMessage> msgs){
		
	}

}
