package BrickBreaker;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame jframe = new JFrame();
		GamePlay gamePlay = new GamePlay();
		
		jframe.setTitle("Meghna's Brick Breaker");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(700, 600);
		jframe.setVisible(true);
		jframe.setResizable(false);
		jframe.setLocationRelativeTo(null);
		jframe.add(gamePlay);

	}

}
