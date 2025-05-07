import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class GamePanel extends JPanel implements Runnable{

	private final int FPS = 60;
	
	private Image background;
	private Image alienLeft;
	private Image explosion;
	private Thread game_thread;
	private Ship ship;
	private ArrayList<Enemy> enemies;
	private KeyControls keyControls;
	private JProgressBar shipHPBar;
	private File file;
	private AudioInputStream audioStream;
	private Clip clip;
	private Timer timer;
	
	private int x;
	private int y;
	private long current_time;
	private long previous_time;
	private double interval;
	private double delta;
	
	protected int height;
	protected int width;
	private int hpVal;
	private int score;
	private boolean gameOver;
	private boolean gameComplete;
	
	private int aPositions[][] = {{0, 0}, {48, 0}, {96, 0},
								  {144, 0}, {192, 0}, {240 ,0}, 
								  {288, 0}, {336, 0}, {384 ,0}, 
								  {0, 48}, {48, 48}, {96, 48}, {144, 48},
								  {192, 48},{240, 48}, {288, 48}, 
								  {336 ,48}, {384, 48}};
	
	GamePanel(){
		bgMusic();
		initPanel();
		initAliens();
		startLoop();
		
	}
	
	private void initPanel() {
		gameOver = false;
		gameComplete = false;
		hpVal = 100;
		
		keyControls = new KeyControls();
		setDimensions();
		ship = new Ship(x, y, this, keyControls);
		timer = new Timer();
		
		alienLeft = new ImageIcon("images/ufo-indicator.png").getImage();
		explosion = new ImageIcon("images/output.gif").getImage();
		
		//===== ship HP bar ====
		shipHPBar = new JProgressBar();
		
		setPreferredSize(new Dimension(width - 2, height - 2));
		addKeyListener(keyControls);
		addMouseListener(new MouseControl());
		setFocusable(true);
	}
	
	private void initAliens() {
		enemies = new ArrayList<>();
		
		for(int []p : aPositions) {
			enemies.add(new Enemy(p[0], p[1]));
		}
	}
	
	private void setDimensions() {
		
		/*==== sets the panel height and width ====
		 * the panel's dimensions depends on the size 
		 * of the bg image
		 */
		{
			background = new ImageIcon("images/bg-space(resized).png").getImage();
			height = background.getHeight(null);
			width = background.getWidth(null);
		}
		/*===== ship's starting position ====
		 * x coordinates = (panel width / 2) - ship's width;
		 * y coordinates = panel height;  
		 */
		{
			x = (width/2) - 48;
			y = height;
		}
	}
	
	private void bgMusic() {
		file = new File("sfx/space-120280.wav");
		try {
			audioStream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void bgMusicStop() {
		clip.stop();
	}
	
	public int getPanelHeight() {
		return height;
	}
	
	public int getPanelWidth() {
		return width;
	}
	
	private void startLoop() {
		game_thread = new Thread(this);
		game_thread.start();
	}
	
	public void updateShip() {
		
		ship.move();
		
		//===== Movements of Ship Bullets ====
		ArrayList<ShipRockets> srockets = ship.getRockets();
		for(int i = 0; i < srockets.size(); i++) {
			ShipRockets rocket = srockets.get(i);
			if(rocket.isAlive()) {
				rocket.move();
			}
			else {
				srockets.remove(i);
			
			}
		}
		
		//===== Movements of Enemy Bullets ====
		for(int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			ArrayList<EnemyRockets> enemyRockets = enemy.getRockets();
			for(int j = 0; j < enemyRockets.size(); j++) {
				EnemyRockets erocket = enemyRockets.get(j);
				if(erocket.isAlive()) {
					erocket.move();
				}
				else {
					enemyRockets.remove(j);
				}
			}
		}
		
		
	}
	
	public void updateEnemies() {
		
		//==== updates the enemy position ====
		for(int i = 0; i < enemies.size(); i++){
			Enemy enemy = enemies.get(i);
			if(enemy.isAlive()) {
				enemy.move();
			}
			else {
				enemies.remove(i);
			}
		}
		
	}
	
	private void checkCollision() {
		//==== Collision Detection for Ship's Bullet and Enemy Ship ====
		{
			ArrayList<ShipRockets> srockets = ship.getRockets();
			for(int i = 0; i < srockets.size(); i++) {
			
				ShipRockets srocket = srockets.get(i);
				Rectangle srocketBounds = srocket.getBounds();
			
				for(int j = 0; j < enemies.size(); j++) {
					Enemy enemy = enemies.get(j);
					Rectangle enemyBounds = enemy.getBounds();
				
					if(srocketBounds.intersects(enemyBounds)) {
										
						srocket.setStatus(false);
					
						enemy.minusHitPoints();
					
						if(enemy.getHitPoints() == 0) {
						
							score += 100;
							
							enemy.setStatus(false);
						
						}
					
					}
				
				}
		
			}
		}
		
		//==== Collision Detection for Ship and Enemy Ship ====
		{
			for(int i = 0; i < enemies.size(); i++) {
				Enemy enemy = enemies.get(i);
				Rectangle enemyBounds = enemy.getBounds();
				Rectangle shipBounds = ship.getBounds();
				
				if(shipBounds.intersects(enemyBounds)) {
					ship.minusHitPoints();
					if(ship.getHitPoints() == 0) {
						ship.setStatus(false);
						gameOver = true;
					}
					
				}
				
			
			}
		}
		
		//===== Collision Detection for Enemy Bullets and Ship ====
		{
			for(int i  = 0; i < enemies.size(); i++) {
				Enemy enemy = enemies.get(i);
				ArrayList<EnemyRockets> erockets = enemy.getRockets();
				Rectangle shipBounds = ship.getBounds();
				
				for(int j = 0; j < erockets.size(); j++) {
					EnemyRockets erocket = erockets.get(j);
					Rectangle erocketBounds = erocket.getBounds();
					
					if(erocketBounds.intersects(shipBounds)) {
						erocket.setStatus(false);
						
						hpVal-=20;
						shipHPBar.setValue(hpVal);
						int remHp = shipHPBar.getValue();
						if(remHp >= 30 & remHp <= 50) {
							shipHPBar.setForeground(Color.orange);
						}
						else if(remHp >= 0 & remHp <= 30) {
							shipHPBar.setForeground(Color.red);
						}
						
						
						ship.minusHitPoints();
						if(ship.getHitPoints() <= 0) {
							ship.setStatus(false);
							bgMusicStop();
							gameOver = true;
						}
					
						
					}
				}
				
			}
		}
		
		ArrayList<ShipRockets> srockets = ship.getRockets();
		for(int i = 0; i < srockets.size(); i++) {
		
			ShipRockets srocket = srockets.get(i);
			Rectangle srocketBounds = srocket.getBounds();
		
			for(int j = 0; j < enemies.size(); j++) {
				Enemy enemy = enemies.get(j);
				
				ArrayList<EnemyRockets> erockets = enemy.getRockets();
				
				for(int k = 0; k < erockets.size(); k++) {
					
					EnemyRockets erocket = erockets.get(k);
					
					Rectangle erocketsBounds = erocket.getBounds();
					
					if(srocketBounds.intersects(erocketsBounds)) {
						
						srocket.setStatus(false);
						erocket.setStatus(false);
						
						
					}
					
				}
			
			}
	
		if(enemies.isEmpty()) {
			gameComplete = true;
		}
		}
	}
	
	private void drawEntities(Graphics g) {
		
		Font font = new Font("Segoe UI Black", Font.BOLD, 14);

		
		//==== draws the bg, indicator, ship itself ====
		{
			g.drawImage(background, 0, 0, this);
		
			g.drawImage(alienLeft, 0, 0, 28, 28, this);
			
			g.setColor(Color.white);
			
			g.setFont(font);
			
			g.drawString("Alien Left: " + enemies.size(), 40, 15);
			
			g.drawString("Score: " + score, 190, 15);
			
			// ==== ship HP ====
			g.drawString("HP", 296, 15);
			shipHPBar.setValue(hpVal);	
			shipHPBar.setVisible(true);
			add(shipHPBar);
			shipHPBar.setBounds(320, 5, 130, 10);
			
			
			
			// ==== ship ====
			if(ship.isAlive()) {
				g.drawImage(ship.getImage(), ship.getX(), ship.getY(), this);
			}
			else {
				g.drawImage(explosion, ship.getX() - 25, ship.getY() - 15, this);
			}
		}
		
		//==== draws the enemy ships ====
		
		{
			for(Enemy enemy : enemies) {
				if(enemy.isAlive()) {
					g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
				}
				else {
					g.drawImage(explosion, enemy.getX(), enemy.getY(), this);
				}
			}
		}
		
		//==== draws the ship's rockets ====
		{
			ArrayList<ShipRockets> srockets = ship.getRockets();
			for(int i = 0; i < srockets.size(); i++) {
				ShipRockets srocket = srockets.get(i);
				g.drawImage(srocket.getImage(), srocket.getX(), srocket.getY(), 16, 25, this);
			}
		}
		
		//==== draws the enemy rockets ====

		{
			for(int i = 0; i < enemies.size(); i++) {
				Enemy enemy = enemies.get(i);
				ArrayList<EnemyRockets> enemyRockets = enemy.getRockets();
				for(int j = 0; j < enemyRockets.size(); j++) {
					EnemyRockets erocket = enemyRockets.get(j);
					g.drawImage(erocket.getImage(), erocket.getX(), erocket.getY(), this);
				}
			}
			
		}
		
		
	}
	
	public void drawGameOver(Graphics g) {
		Image gOver = new ImageIcon("images/gameOver.gif").getImage();
		shipHPBar.setVisible(false);
		g.drawImage(background, 0, 0, this);
        g.drawImage(gOver, -10, 50, this);
        
	}
	
	public void drawGameComplete(Graphics g) {
		Font font = new Font("Segoe UI Black", Font.BOLD, 30);
		g.drawImage(background, 0, 0, this);
		g.setColor(Color.white);
		g.setFont(font);
		g.drawString("Game Complete", (width/2) - 125, height/2);
		g.drawString("Your Total Score: " + score, (width/2) - 165, height/2 + 30);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(!gameOver) {
			drawEntities(g);
			if(gameComplete) {
				ship.gCompleteMove();
				if(ship.getY() <= 0) {
					shipHPBar.setVisible(false);
					drawGameComplete(g);
				}
				
			}
		}
		else {
			drawGameOver(g);
		}
	}
	
	@Override
	public void run() {
		
		/*==== Game Engine ====
		 *  all the processes that occurs in the game happens here
		 *  like, drawing, movements, etc.
		 */
		
		delta = 0;
		interval = 1000000000 / FPS;
		previous_time = System.nanoTime();
		
		while(game_thread != null) {
			
			current_time = System.nanoTime();
			delta += (current_time - previous_time) / interval;
			previous_time = current_time;
			
			if(delta >= 1) {
				System.out.println(ship.getHitPoints());
				updateShip();
				checkCollision();
				updateEnemies();
				repaint();
				delta--;
			}
		}
	}
	
	//===== Class for Ship's Weapon =====
	private class MouseControl extends MouseAdapter{
		@Override
		public void mouseReleased(MouseEvent e) {
			ship.mouseReleased(e);
		}
	}
	
}
