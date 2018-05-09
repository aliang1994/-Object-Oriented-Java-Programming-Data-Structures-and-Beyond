package GUIPractice;

import processing.core.PApplet;

public class GUI3D extends PApplet{
	private static final long serialVersionUID = 1L;
	public void setup(){
		size(500,500, OPENGL);
		background(230);
		
				
		
	
	}
	public void draw(){
		
		
		noStroke();
		lights();		
		sphere(10);
		translate(200,100); 
			
		
		
		
		
		
	}

}
