package BrickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements ActionListener, KeyListener{
	
	private static final long serialVersionUID = 1L;
	
	public static boolean play = false;
	private boolean gameOver, gameWon;
	private int playerX;
	private int ballPosX;
	private int ballPosY;
	private int ballDirX;
	private int ballDirY;
	private int score;
	private Timer timer;
	private MapGenerator map;
	
	public GamePlay() {
		
		startUpDefaults();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(10, this);
		timer.start();
		
	}
	
	public void startUpDefaults() {
		
		playerX = 310;
		ballPosX = 120;
		ballPosY = 350;
		ballDirX = -1;
		ballDirY = -2;
		score = 0;
		gameOver = false;
		gameWon = false;
		map = new MapGenerator(3,7);
	}
	
	public void paint(Graphics g) {
		
		// background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 700, 600);
		
		// bricks
		map.draw((Graphics2D)g);
		
		// paddle
		g.setColor(Color.GREEN);
		g.fillRect(playerX, 550, 100, 10);
		
		// ball
		g.setColor(Color.YELLOW);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		
		// Text
		g.setColor(Color.WHITE);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("Score : " + String.valueOf(score), 550, 40);
		
		if(gameOver) {
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over!", 280, 300);
			g.setColor(Color.RED.darker());
			g.drawString("Press ENTER to Restart", 200, 350);
		}
		
		if(gameWon) {
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Won!", 280, 300);
			g.setColor(Color.WHITE);
			g.drawString("Press ENTER to Restart", 200, 350);
		}
		
		g.dispose();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(play) {
			Rectangle paddle = new Rectangle(playerX, 550, 100, 10);
			Rectangle ball = new Rectangle(ballPosX, ballPosY, 20, 20);
			if(ball.intersects(paddle)) {
				ballDirY = -ballDirY;
			}
			A: for(int i = 0; i < map.row; i++) {
				for(int j = 0; j < map.col; j++) {
					if(map.map[i][j] > 0) {
						Rectangle brick = new Rectangle(j*map.brickWidth + 70, i*map.brickHeight + 70, map.brickWidth, map.brickHeight);
						if(ball.intersects(brick)) {
							map.brickHit(i,j);
							score++;
							if(ballPosX+20>=brick.x && ballPosX<brick.x+brick.width) {
								ballDirY = -ballDirY;
							}else {
								ballDirX = -ballDirX;
							}
							break A;
						}
					}
				}
			}
			int lastRowSum = 0;
			for(int j = 0; j < map.col; j++) {
				lastRowSum += map.map[map.row-1][j];
			}
			if(lastRowSum == 0) {
				map.deleteLastRow();
			}
			
			ballPosX += ballDirX;
			ballPosY += ballDirY;
			if(ballPosX < 0) { ballDirX = -ballDirX; }
			if(ballPosX > 680) { ballDirX = -ballDirX; }
			if(ballPosY < 0) { ballDirY = -ballDirY; }
			
			if(ballPosY > 550 ) {
				gameOver = true;
				play = false;
			}
			
			if(map.row > 8) {
				gameOver = true;
				play = false;
			}
			
			int totalSum = 0;
			for(int i = 0; i < map.row; i++) {
				for(int j = 0; j < map.col; j++) {
					totalSum += map.map[i][j];
				}
			}
			if(totalSum == 0) {
				gameWon = true;
				play = false;
			}
		}
		
		repaint();
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX > 600) {
				playerX = 600;
			} 
			else {
				moveRight();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX < 0) {
				playerX = 0;
			} 
			else {
				moveLeft();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				startUpDefaults();
			}
		}
		
	}
	
	public void moveRight() {
		play = true;
		playerX += 20;
	}
	
    public void moveLeft() {
    	play = true;
		playerX -= 20;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
