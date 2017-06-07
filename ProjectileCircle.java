package Main;

import java.awt.Color;

public class ProjectileCircle {
	
	private double x;
	private double y;
	private int r;
	
	private int angle = -55;
	private double speed = 60;
	private double ax = 0.25;
	private double ay = 15;
	private double vx;
	private double vy;
	private double c = 0.2;
	
	public static boolean inverse = false;
	
	public ProjectileCircle(){
		
		this.x = GamePanel.circle.getx();
		this.y = GamePanel.circle.gety();
		this.r = GamePanel.circle.getr();
		
		if(inverse) angle = -135;
		vx = speed * Math.cos(angle*(Math.PI/180));
		vy = speed * Math.sin(angle*(Math.PI/180));
		
	}
	
	//getters
	public double getx(){return x;}
	public double gety(){return y;}
	public int getr(){return r;}
	
	public boolean update(){
		
		if(y > GamePanel.HEIGHT + r || x > GamePanel.WIDTH + r || x < -r) return true;
		
		x += vx * c;
		y += vy * c;
		
		vx += ax * c;
		vy += ay * c;
		
		return false;
		
	}
	
	public void draw(java.awt.Graphics2D g){
		
		g.setColor(new Color(0,80,130,120));
		g.fillOval((int)x-r, (int)y-r, 2*r, 2*r);
		
	}

}
