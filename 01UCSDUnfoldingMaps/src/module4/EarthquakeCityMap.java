package module4;

import java.util.ArrayList;
import java.util.List;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractShapeMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

/** 
 * EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */

public class EarthquakeCityMap extends PApplet {
	/*
	 * We will use member variables, instead of local variables, to store the data
	 * that the setUp and draw methods will need to access (as well as other methods)
	 * You will use many of these variables, but the only one you should need to add
	 * code to modify is countryQuakes, where you will store the number of earthquakes
	 * per country.
	 */
	private static final long serialVersionUID = 1L;  // You can ignore this.  It's to get rid of eclipse warnings
	private static final boolean offline = false;   // IF YOU ARE WORKING OFFILINE, change the value of this variable to true
	public static String mbTilesString = "blankLight-1-3.mbtiles";  // This is where to find the local tiles, for working without an Internet connection */
	private String earthquakesData = "quiz1.atom"; // earthquakesData = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";  //feed with magnitude 2.5+ Earthquakes
	//earthquakesData = "test1.atom";
	// earthquakesData = "test2.atom";
	// earthquakesData = "quiz1.atom";	
	private String cityFile = "city-data.json";  // The files containing city names and info and country names and info
	private String countryFile = "countries.geo.json";
	private UnfoldingMap map; // The map
	private List<Marker> cityMarkers; // Markers for each city
	private List<Marker> quakeMarkers; // Markers for each earthquake
	private List<Marker> countryMarkers; // A List of country markers
	private List<PointFeature> earthquakes;
	
	public void setup() {
		//(1) Initializing canvas and map tiles
		size(900, 700, OPENGL);		
		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 650, 600, new MBTilesMapProvider(mbTilesString));
		    earthquakesData = "2.5_week.atom";  // The same feed, but saved August 7, 2015
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 650, 600, new Google.GoogleMapProvider());// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line earthquakesURL = "2.5_week.atom";
		}
		
		map.zoomToLevel(2);
		MapUtils.createDefaultEventDispatcher(this, map);			
		
		
		
				
		// (2) Reading in earthquake data and geometric properties
	    //STEP 1: load country features and markers
		List<Feature> countries = GeoJSONReader.loadData(this, countryFile);
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		
		//STEP 2: read in city data
		List<Feature> cities = GeoJSONReader.loadData(this, cityFile);
		cityMarkers = new ArrayList<Marker>();
		for(Feature city : cities){
			CityMarker cm = new CityMarker(city); 
			cityMarkers.add(cm);
		}
	    
		//STEP 3: read in earthquake RSS feed
	    earthquakes = ParseFeed.parseEarthquake(this, earthquakesData);
	    quakeMarkers = new ArrayList<Marker>();	    
	    for(PointFeature feature : earthquakes) {	    	
	    	if(isLand(feature)) {
	    		LandQuakeMarker lqm = new LandQuakeMarker(feature);
	    		quakeMarkers.add(lqm);
	    	}		    
	    	else {
	    		OceanQuakeMarker oqm = new OceanQuakeMarker(feature);
	    		quakeMarkers.add(oqm);
		  }
	    }  

	    printQuakes(); 
	 		
	    // (3) Add markers to map    NOTE: Country markers are not added to the map. They are used for their geometric properties
	    map.addMarkers(quakeMarkers);
	    map.addMarkers(cityMarkers);	    
	} 
		
	public void draw() {
		background(0);
		map.draw();
		addKey();		
	}
		
	private void addKey() {	
		fill(255, 250, 240);
		rect(25, 50, 150, 450);
		
		fill(0);
		textAlign(LEFT, CENTER);
		textSize(12);
		text("Earthquake Key", 50, 75);
		
		fill(color(255, 0, 0));
		ellipse(50, 125, 15, 15);
		fill(color(255, 255, 0));
		ellipse(50, 175, 10, 10);
		fill(color(0, 0, 255));
		ellipse(50, 225, 5, 5);
		
		fill(0, 0, 0);
		text("5.0+ Magnitude", 75, 125);
		text("4.0+ Magnitude", 75, 175);
		text("Below 4.0", 75, 225);
		
		fill(200,100,100);
		triangle(50,300, 45, 310, 55, 310);
		noFill();
		text("major cities", 70, 305);		
		rect(45,320,10,10);
		text("ocean quakes", 70, 322);
		ellipse(50, 340, 10, 10);
		text("land quakes", 70, 340);
		
		noFill();
		text("shallow: blue", 40, 380);
		text("intermediate: green", 40, 410);
		text("deep: yellow", 40, 440);
		text("size: magnitude", 40, 470);
	}
	
	/*
	 * Checks whether this quake occurred on land.  
	 * If it did, it sets the "country" property of its PointFeature to the country where it occurred 
	 * and returns true.  Notice that the helper method isInCountry will set this "country" property already.  
	 * Otherwise it returns false. 
	 */	
	private boolean isLand(PointFeature earthquake) {
		for (Marker country: countryMarkers){
			if(isInCountry(earthquake,country)){
				return true;
			}
		}				
		return false;
	}
	
	/* 
	 * prints countries with number of earthquakes. 
	 * You will want to loop through the country markers or country features (either will work) 
	 * and then for each country, loop through the quakes to count how many occurred in that country.
	 * Recall that the country markers have a "name" property, and LandQuakeMarkers have a "country" property set.
	 */
	private void printQuakes(){
		int landQuakeCounter=0;
		int oceanQuakeCounter=0;
		for(Marker c: countryMarkers){							
			for (PointFeature pf: earthquakes){				
				if(isInCountry(pf,c)){								
					landQuakeCounter++;											
				}					
			}
			if (landQuakeCounter!=0){
				System.out.println(c.getProperty("name") + "\t" +landQuakeCounter);
			}			
			landQuakeCounter=0;	
		}			
		
		for (PointFeature pf: earthquakes){			
			if(isLand(pf)==false){
				oceanQuakeCounter++;
			}				
		}
		System.out.println("ocean " + "\t" + oceanQuakeCounter);
	}
	
	/*
	 * helper method to test whether a given earthquake is in a given country.
	 * This will also add the country property to the properties of the earthquake feature 
	 * if it's in one of the countries.
	 */	 
	private boolean isInCountry(PointFeature earthquake, Marker country) {		
		Location quakeLoc = earthquake.getLocation();   	// getting location of quake feature		
		if(country.getClass() == MultiMarker.class) {		// countries as MultiMarker 		
			for(Marker marker : ((MultiMarker)country).getMarkers()) {		// looping over markers making up MultiMarker
					if(((AbstractShapeMarker)marker).isInsideByLocation(quakeLoc)) {		// checking if inside
					earthquake.addProperty("country", country.getProperty("name"));
					return true;		// return if is inside one 
				}
			}
		}
		else if(((AbstractShapeMarker)country).isInsideByLocation(quakeLoc)) {    // countries as SimplePolygonMarker
			earthquake.addProperty("country", country.getProperty("name"));   			
			return true;
		}
		return false;
	}
}