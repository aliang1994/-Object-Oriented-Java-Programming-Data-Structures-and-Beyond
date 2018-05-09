package GUIPractice;

import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import java.util.*;

public class MapWithMarker extends PApplet {
	private UnfoldingMap map;
	
	public void setup(){
		size(900, 600, P2D);
		AbstractMapProvider provider = new Google.GoogleMapProvider();
		map = new UnfoldingMap(this, 200, 50, 700, 500, provider);
		map.zoomToLevel(2);
		MapUtils.createDefaultEventDispatcher(this,map);
		
		//add one marker with properties for quake location 1
		Location loc1 = new Location (-36.50f, -72.15f);
		Feature f1 = new PointFeature(loc1);
			f1.addProperty("date", "May 22, 1960");
			f1.addProperty("magnitude", 9.5f);
			f1.addProperty("title", "Valdivia, Chile");		
			f1.addProperty("year", 1960);
		HashMap<String,Object> properties = f1.getProperties();
		Marker m1 = new SimplePointMarker(loc1, properties);
		map.addMarker(m1);
		
		//add a list of markers with properties
		List<PointFeature> features = new ArrayList<PointFeature>();
			features.add((PointFeature) f1);
		List<Marker> markers = new ArrayList<Marker>();
		for (PointFeature pf: features){
			Marker m = new SimplePointMarker(pf.getLocation(), pf.getProperties());
			markers.add(m);			
		}
		
		//set different color for markers
		int yellow = color(255,255,0);
		int gray = color(150);
		for (Marker m: markers){
			if((float)m.getProperty("magnitude")>5.0f){
				m.setColor(yellow);			
			}
			else{
				m.setColor(gray);
			}
			
		}
		
		map.addMarkers(markers);
		
	}
	
	public void draw(){
		background(100);
		map.draw();
		//addKey();
	}

}
