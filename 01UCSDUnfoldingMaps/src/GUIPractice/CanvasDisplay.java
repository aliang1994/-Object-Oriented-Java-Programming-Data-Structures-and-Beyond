package GUIPractice;

import processing.core.PApplet;



public class CanvasDisplay extends PApplet{
	public void setup(){
		size(600,600);
		background(50,200,90);
	}
	
	public void draw(){
		fill(255,0,255);
		ellipse(300,300,300,300);
		
		fill (0,255,255);
		ellipse(300,300,200,200);
	}
	

}
