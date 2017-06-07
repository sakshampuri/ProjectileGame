package Main;

public class Target {
	
	private int x;
	private int y;
	
	private int maxR = 30;
	private int minR = 10;
	private int r = (int) (Math.random() * (maxR - minR) + minR);
	private int rMax = 3 * r / 2;
	
	private int yStart = 180;
	private int yEnd = GamePanel.HEIGHT - 50;
	
	private boolean destruct = false;
	
	//getters
	public int getx(){return x;}
	public int gety(){return y;}
	public int getr(){return r;}
	
	public Target(){
	
		x = (int) (Math.random() * (GamePanel.WIDTH - 80) + 40);
		y = (int) (Math.random() * (yEnd - yStart) + yStart);
	}
	
	//setter
	public void initiateDestruction(boolean b){destruct = b;}
	
	public boolean update(){
		
		if(destruct)
			r++;
		
		    if(r > rMax)
		    	return true;
		
		return false;
		
	}
	
	public void draw(java.awt.Graphics2D g){
		
		g.setColor(java.awt.Color.BLACK);
		g.setStroke(new java.awt.BasicStroke(2));
		g.drawOval(x-r, y-r, 2*r, 2*r);
		g.setColor(java.awt.Color.YELLOW);
		g.drawOval(x - r - 4, y - r - 4, 2*r + 8, 2*r + 8);
		
	}
	
}
