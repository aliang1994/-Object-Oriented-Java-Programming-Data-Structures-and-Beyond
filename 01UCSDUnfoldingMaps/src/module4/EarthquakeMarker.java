package module4;

import java.util.HashMap;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PGraphics;

/** Implements a visual marker for earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 *
 */
public abstract class EarthquakeMarker extends SimplePointMarker{	
	protected boolean isOnLand; // Did the earthquake occur on land?  This will be set by the subclasses.
	protected float radius; // SimplePointMarker has a field "radius": protected float radius; You will want to set this in the constructor, either using the thresholds below, or a continuous function based on magnitude. 
 	public static final float THRESHOLD_MODERATE = 5; /** Greater than or equal to this threshold is a moderate earthquake */
	public static final float THRESHOLD_LIGHT = 4; /** Greater than or equal to this threshold is a light earthquake */
	public static final float THRESHOLD_INTERMEDIATE = 70; /** Greater than or equal to this threshold is an intermediate depth */
	public static final float THRESHOLD_DEEP = 300; /** Greater than or equal to this threshold is a deep depth */

	// ADD constants for colors
	
	public EarthquakeMarker (PointFeature feature) { 
		super(feature.getLocation());
		
		// Add a radius property and then set the properties
		HashMap<String, Object> properties = feature.getProperties();
		float magnitude = Float.parseFloat(properties.get("magnitude").toString());
		properties.put("radius", 2*magnitude );
		setProperties(properties);
		
		//set the value for instance variable		
		this.radius = 1.75f*getMagnitude();
	}	
	
	public abstract void drawEarthquake(PGraphics pg, float x, float y); 
	
	/* 
	 * calls abstract method drawEarthquake and then checks age and draws X if needed
	 */
	public void draw(PGraphics pg, float x, float y) {
		// save previous styling
		pg.pushStyle();
			
		// determine color of marker from depth
		colorDetermine(pg);
		
		// call abstract method implemented in child class to draw marker shape
		drawEarthquake(pg, x, y);
		
		// OPTIONAL TODO: draw X over marker if within past day		
		
		// reset to previous styling
		pg.popStyle();		
	}
	
	/*
	 * determine color of marker from depth
	 * We suggest: Deep = red, intermediate = blue, shallow = yellow. But this is up to you, of course.
	 * You might find the getters below helpful. 
	 */
	private void colorDetermine(PGraphics pg) {
		if(getDepth()<THRESHOLD_INTERMEDIATE){
			pg.fill(0,0,255); //blue
		}
		if(getDepth()>THRESHOLD_INTERMEDIATE && getMagnitude()<THRESHOLD_DEEP){			
			pg.fill(0,255,0); //green
		}
		if(getDepth()>THRESHOLD_DEEP){
			pg.fill(255,255,0); //yellow			
		}
		
	}	
	
	/*
	 * getters for earthquake properties
	 */	
	public float getMagnitude() {
		return Float.parseFloat(getProperty("magnitude").toString());
	}	
	public float getDepth() {
		return Float.parseFloat(getProperty("depth").toString());	
	}	
	public String getTitle() {
		return (String) getProperty("title");			
	}	
	public float getRadius() {
		return Float.parseFloat(getProperty("radius").toString());
	}	
	public boolean isOnLand() {
		return isOnLand;
	}
}
