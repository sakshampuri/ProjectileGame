package Main;

import javax.swing.JFrame;

public class Game {
	
	public static void main(String[] main){
		
		JFrame window = new JFrame("Game");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setContentPane(new GamePanel());
		window.pack();
		window.setResizable(false);
		window.setVisible(true);
		
	}

}