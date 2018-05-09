package GUIPractice;

// need to fix the map since it's still empty

import java.util.*;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class LifeExpectancyVisuals extends PApplet {
	private UnfoldingMap map;
	Map<String, Float> lifeExpByCountry;
	List<Feature> features;
	List<Marker> markers;
	
	public void setup(){
		size(900,600,P2D);
		AbstractMapProvider provider = new Google.GoogleMapProvider();
		map = new UnfoldingMap(this, 200,25, 600, 550,provider);
		MapUtils.createDefaultEventDispatcher(this,map);
		
		lifeExpByCountry = loadData("LifeExpectancyWorldBankModule3.csv");
		
		features = GeoJSONReader.loadData(this,"countries.geo.json");
		markers = MapUtils.createSimpleMarkers(features);
		map.addMarkers(markers);
		
		shadeCountries();
	}
	
	public void draw(){
		background(200);
		map.draw();
		
	}
	
	private Map<String, Float> loadData(String fileName){
		Map<String, Float> lifeExpMap = new HashMap<String,Float>();
		String[] rows = loadStrings(fileName);    //from Processing Library
		for (String s: rows){
			String[] column = s.split(",");
			if(column.length==5 && !column[4].contains("..")){
				String country = column[3];						
				float exp = Float.parseFloat(column[4]);
				lifeExpMap.put(country, exp);				
			}	
		}
		
		return lifeExpMap; 
	}
	
	private void shadeCountries(){
		System.out.println(lifeExpByCountry);		
		for(Marker m: markers){
			String id = m.getId();
			if(lifeExpByCountry.containsKey(id)){
				System.out.println("found id: " + id);
				float exp = lifeExpByCountry.get(id);
				int colorLevel = (int) map(exp,40,90,0,255);
				int c = color(255-colorLevel,150,colorLevel);
				m.setColor(c);
			}
			else{
				//System.out.println("id not found");
				int d = color(150,150,150);
				m.setColor(d);
			}
		}
	}

}
