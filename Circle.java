package Main;

import java.awt.Color;
import java.awt.Graphics2D;

public class Circle {
	
	private int x;
	private int y;
	private int r;
	
	private Color color;
	private long startTime;
	private int alpha = 140;
	
	public Circle(){
		
		r = 1;
		startTime = System.nanoTime();
		
	}
	
	public void setx(int x){this.x = x;}
	public void sety(int y){this.y = y;}
	
	public int getx(){return x;}
	public int gety(){return y;}
	public int getr(){return r;}
	public Color getColor(){return color;}
	
	public void update(){
		
		long elapsed = (System.nanoTime() - startTime)/1000000;
		if(elapsed > 500){
			r += 1;
			alpha-=3;
		}
		
		if(r >= 40){
			r = 1;
			alpha = 120;
		}
		color = new Color(200, 120, 50, alpha);
		
	}
	
	public void draw(Graphics2D g){
		
		g.setColor(color);
		g.fillOval(x-r, y-r, 2*r, 2*r);
		
	}

}
