package module3;

import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */

public class EarthquakeCityMap extends PApplet {	
	private static final long serialVersionUID = 1L; // You can ignore this.  It's to keep eclipse from generating a warning.
	private static final boolean offline = false; // IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	public static final float THRESHOLD_MODERATE = 5; // Less than this threshold is a light earthquake
	public static final float THRESHOLD_LIGHT = 4; // Less than this threshold is a minor earthquake
	public static String mbTilesString = "blankLight-1-3.mbtiles";   //** This is where to find the local tiles, for working without an Internet connection */
	private UnfoldingMap map;
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";  //feed with magnitude 2.5+ Earthquakes

	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleTerrainProvider());			
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    List<Marker> markers = new ArrayList<Marker>();  //Use provided parser to collect properties for each earthquakePointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    for(PointFeature pf: earthquakes){
	    	SimplePointMarker spm = createMarker(pf);     	 	    	
	    	markers.add(spm); 
	    }
	    
	    map.addMarkers(markers);
	    
	    // These print statements show you (1) all of the relevant properties in the features, and (2) how to get one property and use it
	    if (earthquakes.size() > 0) {
	    	PointFeature f = earthquakes.get(0);
	    	System.out.println(f.getProperties());
	    	Object magObj = f.getProperty("magnitude");
	    	float mag = Float.parseFloat(magObj.toString());	    	
	    }
	     
	    int yellow = color(200, 200, 0);
	    int blue = color(0,0,200);
	    int red = color (150,0,0);        
	    	    
	    
	   for (Marker m: markers){    	  			    	
	    	float magnitude = Float.parseFloat(m.getProperty("magnitude").toString());	    	
	    	
	    	if(magnitude<4.0f){
	    		m.setColor(blue);	
	    		((SimplePointMarker) m).setRadius(2);
	    	}
	    	if(magnitude>4.0f && magnitude<4.9f){
	    		m.setColor(yellow);
	    		((SimplePointMarker) m).setRadius(5);
	    	}
	    	if(magnitude>5.0f){
	    		m.setColor(red);
	    		((SimplePointMarker) m).setRadius(10);
	    	}	   
	    }    	       
	    	    
	}
		
	// A suggested helper method that takes in an earthquake feature and returns a SimplePointMarker for that earthquake
	private SimplePointMarker createMarker(PointFeature feature){
		Location loc = feature.getLocation();
		HashMap<String,Object> properties = feature.getProperties();
		
		SimplePointMarker mk = new SimplePointMarker(loc);
		mk.setProperties(properties);
		return mk;
	}
	
	public void draw() {
	    background(100);
	    map.draw();
	    addKey();
	}
	
	private void addKey() {	
		noStroke();
		fill(183,215,232);
		rect(25,50,150,400);
		
		stroke(0);
		fill(0,0,255);
		ellipse(40,100, 5, 5);
		text("mag<4.0", 80,103);
		
		
		stroke(0);		
		fill(255,255,0);
		ellipse(40,150,7,7);
		text("4.0<mag<4.9", 80,153);
		
		stroke(0);
		fill(255,0,0);
		ellipse(40,200,10,10);	
		text("mag>5.0", 80,205);
	}
}
