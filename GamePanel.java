package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, MouseMotionListener, MouseListener, KeyListener{
	
	private int FPS = 60;
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 420;
	
	private int score = 0;
	
	private int level = 1;
	private boolean levelStarted = true;
	
	public static Circle circle;
	private ArrayList<ProjectileCircle> pc;
	private ArrayList<Target> targets;
	
	private BufferedImage image;
	private Graphics2D g;
	
	private Thread thread;
	
	public GamePanel(){
		
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		
	}
	
	public void addNotify(){
		
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
		addKeyListener(this);
		
	}
	
	public void init(){
		
		addMouseMotionListener(this);
		addMouseListener(this);
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
		           RenderingHints.VALUE_ANTIALIAS_ON);
 
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
		           RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		circle = new Circle();
		targets = new ArrayList<Target>();
		pc = new ArrayList<ProjectileCircle>();
		
	}
	
	public void run(){
		
		boolean running = true;
		
		init();
		
		long startTime;
		long waitTime;
		long elapsed;
		long targetTime = 1000/FPS;
		
		while(running){
			
			startTime = System.nanoTime();
			
			update();
			render();
			draw();
			
			elapsed = startTime - System.nanoTime();
			
			waitTime = targetTime - elapsed/1000000;
			
			try{
				Thread.sleep(waitTime);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			
		}
		
	}
	
	private void update(){
		
		//update circle on mouse
		circle.update();
		
		//targets
		if(levelStarted)
		for(int i = 0; i < level; i++)
			targets.add(new Target());
		levelStarted = false;
		
		//targets update
		for(int i = 0; i < targets.size(); i++)
			if(targets.get(i).update()){
				targets.remove(i);
				score += 10;
			}
		
		//level begin
		if(targets.size()==0){
			level++;
			levelStarted = true;
		}
		
		//update project. circle class
		for(int i = 0; i < pc.size(); i++){
			boolean remove = pc.get(i).update();
			if(remove) score--;
			//collision check between project. circle and target
			double pcx = pc.get(i).getx();
			double pcy = pc.get(i).gety();
			int pcr = pc.get(i).getr();
			for(int j = 0; j < targets.size(); j++){
				
				//using getters
				int tx = targets.get(j).getx();
				int ty = targets.get(j).gety();
				int tr = targets.get(j).getr();
				//calculating distance using distance formula
				double dist = Math.sqrt((tx - pcx)*(tx - pcx) + (ty - pcy)*(ty - pcy));
				
				//condition check
				if(dist < tr){
					//collision may be there
					//radius error check
					if(((tr - pcr) < 4 && (tr - pcr) > 0 ) || ( (pcr - tr) < 4 && (pcr - tr) > 0)){
					//Collision Detected
					remove = true;
					//explosions.add(new Explosion(targets.get(j)));
					//targets.remove(j);
					targets.get(j).initiateDestruction(true);
				} // radius error if block end
			  } // distance check end
			} // inner for loop till targets.size() end
			if(remove) pc.remove(i);
		} // main for loop end
		
		
	}
	
	private void render(){
		
		//Background
		g.setColor(new Color(30, 120,150));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//Animated Circle on mouse
		circle.draw(g);
		
		//Bullet Circles
		for(int i = 0; i < pc.size(); i++) 
			pc.get(i).draw(g);
		
		//targets
		for(int i = 0; i < targets.size(); i++)
			targets.get(i).draw(g);
		
		//Border
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(10));
		g.drawRect(2, 2, WIDTH-5, HEIGHT-5);
		g.setStroke(new BasicStroke(1));
		
		//Level
		g.setFont(new Font("Century Gothic", Font.BOLD, 17));
	    g.setColor(Color.WHITE);
		g.drawString("LEVEL: "+level, 16, 26);
		
		//Score
		g.drawString("SCORE: "+score, WIDTH-100, 28);
		
		//Credits
		g.setFont(new Font("Century Gothic", Font.BOLD, 13));
		g.drawString("By Saksham Puri", WIDTH - 120, HEIGHT - 20);
		
		//test strings
		
	}
	
	private void draw(){
		
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		
	}
	

	@Override
	public void mouseMoved(MouseEvent me) {
		
		circle.setx(me.getX());
		if(me.getY() < 180)
		circle.sety(me.getY());
		
	}

	@Override
	public void mouseDragged(MouseEvent me) {

		
	}

	@Override
	public void mouseClicked(MouseEvent me) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		pc.add(new ProjectileCircle());
		
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_SHIFT)
			ProjectileCircle.inverse = true;
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_SHIFT)
			ProjectileCircle.inverse = true;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_SHIFT)
			ProjectileCircle.inverse = false;
		
	}


}
