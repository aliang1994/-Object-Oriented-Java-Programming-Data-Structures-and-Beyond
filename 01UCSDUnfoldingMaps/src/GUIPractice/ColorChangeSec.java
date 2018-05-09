package GUIPractice;

import processing.core.PApplet;
import processing.core.PImage;

public class ColorChangeSec extends PApplet{
	
	PImage img;
	public void setup(){
		size(800,600);
		background(200,200,200);		
		
		img = loadImage("panda.jpg");
		img.resize(0,height);
		image(img,0,0);
	}
	
	
	public void draw(){
		int[] color = colorSec(second());
		stroke(200);
		fill(color[0],color[1],color[2]);
		ellipse(width/8, height/5, width/5, height/4);
		
		
		
	}
	
	public int[] colorSec(double sec){
		int[] rgb = new int[3];
		double diffFrom30 = Math.abs(sec-30);
		double ratio = diffFrom30/30;
		
		rgb[0]=(int) (255*ratio);
		rgb[1]=(int) (255*ratio);
		rgb[2]=0;
		
	    return rgb;
	}
}
