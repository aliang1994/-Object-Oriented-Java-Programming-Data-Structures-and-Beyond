package module5;

import java.util.HashMap;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PGraphics;

/** Implements a common marker for cities and earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 *
 */
public abstract class CommonMarker extends SimplePointMarker {	
	protected boolean clicked = false;   // Records whether this marker has been clicked (most recently)	
	public CommonMarker(Location location) {
		super(location);
	}	
	public CommonMarker(Location location, HashMap<String,Object> properties) {
		super(location, properties);
	}	

	
	
	/* 
	 * Getter method for clicked field 
	 */
	public boolean getClicked() {
		return clicked;
	}	
	/* 
	 * Setter method for clicked field 
	 */
	public void setClicked(boolean state) {
		clicked = state;
	}	
			
	/** 
	 *  Common piece of drawing method for markers; 
	 *  Note that you should implement this by making calls drawMarker and showTitle, which are abstract methods 
	 *  implemented in subclasses
	 */
	public void draw(PGraphics pg, float x, float y) {
		if (!hidden) {		//if the marker is not hidden
			drawMarker(pg, x, y);
			if (selected) {
				showTitle(pg, x, y);  // If the marker is selected
			}
		}
	}
	
	public abstract void drawMarker(PGraphics pg, float x, float y);
	public abstract void showTitle(PGraphics pg, float x, float y);
	
}