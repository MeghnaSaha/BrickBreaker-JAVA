package BrickBreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class MapGenerator implements ActionListener {
	
	public int map[][];
	public int brickWidth;
	public int brickHeight;
	public int row, col;
	Timer timer;
	
	public MapGenerator(int row, int col) {
		
		this.row = row;
		this.col = col;
		map = new int[10][col];
		for(int i = 0; i< row; i++) {
			for(int j = 0; j< col; j++) {
				map[i][j] = 1; //Brick not hit yet
			}
		}
		brickWidth = 540/col;
		brickHeight = 150/row;
		
		timer = new Timer(25000,this);
		timer.start();
		
	}
	
	public void draw(Graphics2D g) {
		
		for(int i = 0; i< row; i++) {
			for(int j = 0; j< col; j++) {
				if(map[i][j] == 1) {
					g.setColor(Color.WHITE);
					g.fillRect(j*brickWidth + 70, i*brickHeight + 70, brickWidth, brickHeight);
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.BLACK);
					g.drawRect(j*brickWidth + 70, i*brickHeight + 70, brickWidth, brickHeight);
				}
			}
		}
	}
	
	public void brickHit(int r, int c) {
		map[r][c] = 0; //Brick hit
	}
	
	public void deleteLastRow() {
		
		row--;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(GamePlay.play) {
			row++;
			for(int i = row-1; i > 0; i--) {
				for(int j = 0; j< col; j++) {
					map[i][j] = map[i-1][j];
				}
			}
			for(int j = 0; j< col; j++) {
				map[0][j] = 1;
			}
		}
		
	}

}
