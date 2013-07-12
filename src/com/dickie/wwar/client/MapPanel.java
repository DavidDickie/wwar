package com.dickie.wwar.client;

import java.util.List;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Line;
import org.vaadin.gwtgraphics.client.shape.Circle;
import org.vaadin.gwtgraphics.client.shape.Rectangle;
import org.vaadin.gwtgraphics.client.shape.Text;
import org.vaadin.gwtgraphics.client.Image;

import com.dickie.wwar.shared.Game;
import com.dickie.wwar.shared.Golum;
import com.dickie.wwar.shared.Location;
import com.dickie.wwar.shared.Player;
import com.google.gwt.user.client.ui.DecoratorPanel;


public class MapPanel extends DecoratorPanel {
	DrawingArea canvas = new DrawingArea(800, 800);
	int scale = 6;
	public boolean initialized = false; 
	
	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public int getScale() {
		return scale;
	}

	public int getCenter() {
		return center;
	}

	int center = 400;
	
	
	DrawingArea getCanvas(){
		return canvas;
	}

	public MapPanel() {

		this.add(canvas);
	}
	
	public void drawMapBackground(){
		initialized = true;
		Image image = new Image(0, 0, 800, 800, "Project1.png");
		canvas.add(image);
	}
	public void drawLocation(Location l, Player seenBy, Game game, boolean golum, boolean henchman){
		int textDrop = (int)3.5*scale;
		if (l.getType() == Location.LocType.Town || l.getType() == Location.LocType.City || l.getType() == Location.LocType.Mystical){
			int cSize = 2*scale-2;
			if (l.getType() == Location.LocType.City){
				cSize += scale;
				textDrop = 4*scale;
			}
			Circle circle = new Circle(l.getX()* scale + center, l.getY()* scale + center, cSize);
			if (l.getVisibleTo().contains(seenBy.getName())){
				circle.setStrokeColor("#4A9586");
			} else {
				circle.setStrokeColor("#000000");
			}
			
			if (l.isLocked()){
				circle.setFillColor(game.getPlayer(l.getLockedBy()).getColor());
			} else {
				if (l.getType() == Location.LocType.Mystical){
					circle.setFillColor("#EF0000");
				} else {
					circle.setFillColor("#EEEEEE");
				}
			}

			canvas.add(circle);
		} else if (l.getType() == Location.LocType.Keep){
			textDrop = 4*scale;
			Rectangle rec = new Rectangle(l.getX()* scale + center - 2*scale, l.getY()* scale + center - 2*scale, 
					scale*4, scale*4);
			if (l.getVisibleTo().contains(seenBy.getName())){
				rec.setStrokeColor("#4A9586");
			} else {
				rec.setStrokeColor("#000000");
			}
			if (l.isLocked()){
				rec.setFillColor(game.getPlayer(l.getLockedBy()).getColor());
			} else {
				rec.setFillColor("#EEEEEE");
			}
			canvas.add(rec);
		} 
		for (Player p: game.getPlayers()){
			if (p.getLocation().equals(l)){
				Circle c = new Circle(l.getX()* scale + center + p.getxOffset()*scale, 
						l.getY()*scale + center + p.getyOffset()*scale, scale-2);
				c.setFillColor(p.getColor());
				canvas.add(c);
			}
		}
		for (Golum p: game.getGolums()){
			if (p.getLocation().equals(l)){
				Circle c = new Circle(l.getX()* scale + center + p.getOwner().getxOffset()*scale, 
						l.getY()*scale + center + p.getOwner().getyOffset()*scale, scale-4);
				c.setFillColor(p.getOwner().getColor());
				canvas.add(c);
			}
		}


		Text t = new Text(l.getX() * scale + center, l.getY() * scale + center + textDrop, l.getName());
		t.setFontSize(12);
		int len = t.getTextWidth();
		t.setX(t.getX() - len/2);
		canvas.add(t);
	}

	public void drawText(String s, int x, int y) {
		Text text = new Text(x * scale + center, y * scale + center, s);
		
		canvas.add(text);
	}

	public void drawLine(int x, int y, int x2, int y2) {
		Line line = new Line(x * scale + center, y * scale + center, x2 * scale
				+ center, y2 * scale + center);
		canvas.add(line);
		Rectangle rec = new Rectangle(
				((x+x2)/2) * scale + center-5,
				((y+y2)/2) * scale + center-5,
				10,
				10);
		rec.setFillColor("#FFFFFF");
		canvas.add(rec);
	}
	
	public void clearCanvas(){
		canvas.clear();
	}

}
